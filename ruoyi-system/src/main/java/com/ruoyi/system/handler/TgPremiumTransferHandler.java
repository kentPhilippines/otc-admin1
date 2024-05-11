package com.ruoyi.system.handler;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.common.core.domain.entity.TgPremiumOrderInfo;
import com.ruoyi.common.utils.ForwardCounter;
import com.ruoyi.common.utils.LogUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.api.IFragmentApi;
import com.ruoyi.system.api.entity.Params;
import com.ruoyi.system.api.entity.fragment.*;
import com.ruoyi.system.mapper.TgPremiumOrderInfoMapper;
import com.ruoyi.system.service.IErrorLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class TgPremiumTransferHandler {

    @Autowired
    private IFragmentApi fragmentApi;

    @Autowired
    private IErrorLogService errorLogService;
    @Autowired
    private TgPremiumOrderInfoMapper tgPremiumOrderInfoMapper;

    public void doRechargeAndUpdate(TgPremiumOrderInfo tgPremiumOrderInfo) {

         doRecharge(tgPremiumOrderInfo);
        tgPremiumOrderInfo.setLcu("syste");
        tgPremiumOrderInfoMapper.updateTgPremiumOrderInfo(tgPremiumOrderInfo);
    }

    private void doRecharge(TgPremiumOrderInfo tgPremiumOrderInfo) {
        String comment = null;
        String address = null;
        Long amount = null;

        Integer count = 0;
        do {
            try {
                SearchPremiumGiftRecipientResponse searchPremiumGiftRecipientResponse = fragmentApi.searchPremiumGiftRecipient(tgPremiumOrderInfo);

                String recipient = recipient(searchPremiumGiftRecipientResponse);
                //创建订单
                InitGiftPremiumResponse initGiftPremiumResponse = fragmentApi.initGiftPremium(recipient, tgPremiumOrderInfo.getMonths());

                String reqId = reqId(initGiftPremiumResponse);
                //确认订单
                GetGiftPremiumLinkResponse getGiftPremiumLinkResponse = fragmentApi.getGiftPremiumLink(reqId);

                //获取收款地址和payload参数
                RawResponse response = fragmentApi.rawRequest(reqId);

                Messages message = messages(response);

                address = message.getAddress();
                String payload = message.getPayload();

//                comment = extractRefFromPayload(payload, tgPremiumOrderInfo.getMonths());
//
                comment = constructComment(payload, tgPremiumOrderInfo.getMonths());

                amount = message.getAmount();
                BigDecimal actualAmount = BigDecimal.valueOf(amount).movePointLeft(9);
                tgPremiumOrderInfo.setActualAmount(actualAmount);
                tgPremiumOrderInfo.setIdTg(reqId);
            } catch (Exception e) {
                String exceptionString = LogUtils.doRecursiveReversePrintStackCause(e, 5, ForwardCounter.builder().count(0).build(), 5);
                log.error("fragment_error:{}", exceptionString);
                ErrorLog errorLog = ErrorLog.builder()
                        .address(tgPremiumOrderInfo.getRechargeTgUserName())
                        .otherId(String.valueOf(tgPremiumOrderInfo.getIdTgPremiumOrderInfo()))
                        .errorCode("fragment_error")
                        .errorMsg(exceptionString.length() > 2000 ? exceptionString.substring(0, 2000) : exceptionString)
                        .fcu("system")
                        .lcu("system").build();

                errorLogService.insertErrorLog(errorLog);

                if (count >= 5) {
                    break;
                }
                count++;
            }

        } while (comment == null);



        if (count < 5) {
            count = 0;
            do {
                try {
//                    BigDecimal actualAmount = BigDecimal.valueOf(amount).movePointLeft(9);
                    fragmentApi.sendTransactions(address, tgPremiumOrderInfo.getActualAmount(), comment);
                    tgPremiumOrderInfo.setTgPaymentStatus("P");
//                    tgPremiumOrderInfo.setActualAmount(actualAmount);
                    break;
                } catch (Exception e) {
                    String exceptionString = LogUtils.doRecursiveReversePrintStackCause(e, 5, ForwardCounter.builder().count(0).build(), 5);
                    log.error("go_sendTransactions_error:{}", exceptionString);
                    ErrorLog errorLog = ErrorLog.builder()
                            .address(tgPremiumOrderInfo.getRechargeTgUserName())
                            .otherId(String.valueOf(tgPremiumOrderInfo.getIdTgPremiumOrderInfo()))
                            .errorCode("go_sendTransactions_error")
                            .errorMsg(exceptionString.length() > 2000 ? exceptionString.substring(0, 2000) : exceptionString)
                            .fcu("system")
                            .lcu("system").build();

                    errorLogService.insertErrorLog(errorLog);
                    count++;
                }
            } while (count < 5);
        }

        tgPremiumOrderInfo.setTgPaymentStatus("E");

    }

    private static Messages messages(RawResponse response) {
        if (response == null) {
            throw new RuntimeException("response is null");
        }

        Body body = response.getBody();

        if (body == null) {
            throw new RuntimeException("body is null");
        }
        Params params = body.getParams();

        if (params == null) {
            throw new RuntimeException("params is null");
        }
        List<Messages> messages = params.getMessages();
        if (messages == null || messages.size() == 0) {
            throw new RuntimeException("params.getMessages() is null");
        }
        Messages message = messages.get(0);
        return message;
    }

    private static String reqId(InitGiftPremiumResponse initGiftPremiumResponse) {
        if (initGiftPremiumResponse == null) {
            throw new RuntimeException("initGiftPremiumResponse is null");
        }

        String reqId = initGiftPremiumResponse.getReq_id();
        if (StringUtils.isBlank(reqId)) {
            throw new RuntimeException("reqId is null");
        }
        return reqId;
    }

    private static String recipient(SearchPremiumGiftRecipientResponse searchPremiumGiftRecipientResponse) {
        if (searchPremiumGiftRecipientResponse == null) {
            throw new RuntimeException("searchPremiumGiftRecipientResponse is null");
        }

        if (!searchPremiumGiftRecipientResponse.isOk()) {
            throw new RuntimeException("searchPremiumGiftRecipientResponse is not ok,请检查用户信息");
        }

        Found found = searchPremiumGiftRecipientResponse.getFound();

        if (found == null) {
            throw new RuntimeException("found is null");
        }

        //获取用户信息
        String recipient = found.getRecipient();
        return recipient;
    }


    public static String constructComment(String payload, Long premium_package_month) {
        byte[] decodedBytes = Base64.getDecoder().decode(payload);

        String decodedString = new String(decodedBytes);
        log.info("decodedString:{}", decodedString);

        // 使用 "#" 分割字符串
        String[] parts = decodedString.split("#");
        String base32_1 = parts.length > 1 ? parts[1] : "";

        // 移除 base32_1 中的非字母数字字符
        base32_1 = base32_1.replaceAll("[^A-Za-z0-9]", "");

        // 如果 base32_1 长度不是 8，则重试
        if (base32_1.length() != 9) {
            // 在这里添加重试逻辑
            log.error("长度不是 9");
            return null;
        }

        // 构造最终订单数据
//        Integer premium_package_month = 3;
//        String orderData = "Telegram Premium for " + (premium_package_month == 12 ? "1 year" : premium_package_month + " months") + " Ref#" + base32_1;
        String orderData = "Telegram Premium for " + (premium_package_month == 12 ? "1 year" : premium_package_month + " months") + " \n" +
                "\n" +
                "Ref#" + base32_1;
        return orderData;
    }

/*    public static void main(String[] args) {
        String comment = constructComment("te6ccgEBAgEANgABTgAAAABUZWxlZ3JhbSBQcmVtaXVtIGZvciAzIG1vbnRocyAKClJlZgEAFCNMa2M1MWhvZkw");


        String s = extractRefFromPayload("te6ccgEBAgEANgABTgAAAABUZWxlZ3JhbSBQcmVtaXVtIGZvciAzIG1vbnRocyAKClJlZgEAFCNMa2M1MWhvZkw", tgPremiumOrderInfo.getMonths());
        System.out.println(s);
    }*/


    public static String extractRefFromPayload(String payload, Long months) {
        byte[] decodedBytes = Base64.getDecoder().decode(payload);

        String decodedPayloadStr = new String(decodedBytes);

        log.info("decodedPayloadStr:{}", decodedPayloadStr);
//        String decodedPayloadStr = payload;
        String refStr = null;
        int index = decodedPayloadStr.indexOf("#");

        if (index != -1) {
            StringBuilder sb = new StringBuilder();
            // 开始从 "#" 之后的位置提取字符，最多提取8个字符
            for (int i = index + 1; i < decodedPayloadStr.length() && sb.length() < 9; i++) {
                char c = decodedPayloadStr.charAt(i);
                if (Character.isLetterOrDigit(c)) {
                    sb.append(c);
                }
            }
            refStr = sb.toString();
//            String orderData = "Telegram Premium for " + (months == 12 ? "1 year" : months + " months") + " Ref#" + refStr;
            String orderData = "Telegram Premium for " + (months == 12 ? "1 year" : months + " months") + " \n" +
                    "\n" +
                    "Ref#" + refStr;
            return orderData;
        }

        return refStr;
    }

    public void checkFragmentPaymentStatus(TgPremiumOrderInfo tgPremiumOrderInfo) {
        try {
            CheckReqRespose checkReqRespose = fragmentApi.checkReq(tgPremiumOrderInfo.getIdTg());
            if (checkReqRespose == null){
                log.error("checkReqRespose is null");
                return;
            }
            Boolean confirmed = checkReqRespose.getConfirmed();
            if (confirmed != null && confirmed){
                log.error("confirmed is null");
                tgPremiumOrderInfo.setTgPaymentStatus("Y");
                tgPremiumOrderInfo.setLcu("system");
                tgPremiumOrderInfoMapper.updateTgPremiumOrderInfo(tgPremiumOrderInfo);
            }else {
                String error = checkReqRespose.getError();
                if (StringUtils.isNotBlank(error)){
                    log.error("checkFragmentPaymentStatus_error_return:{}", error);
                    tgPremiumOrderInfo.setTgPaymentStatus("E");
                    tgPremiumOrderInfoMapper.updateTgPremiumOrderInfo(tgPremiumOrderInfo);
                    ErrorLog errorLog = ErrorLog.builder()
                            .address(tgPremiumOrderInfo.getRechargeTgUserName())
                            .otherId(String.valueOf(tgPremiumOrderInfo.getIdTgPremiumOrderInfo()))
                            .errorCode("checkFragmentPaymentStatus_error_return")
                            .errorMsg(error)
                            .fcu("system")
                            .lcu("system").build();

                    errorLogService.insertErrorLog(errorLog);
                }else {
                    Date fcd = tgPremiumOrderInfo.getFcd();
                    long betweenMinutes = DateUtil.between(fcd, new Date(), DateUnit.MINUTE);
                    if (betweenMinutes >= 10){
                        log.error("checkFragmentPaymentStatus_error_timeout:");
                        tgPremiumOrderInfo.setTgPaymentStatus("E");
                        tgPremiumOrderInfoMapper.updateTgPremiumOrderInfo(tgPremiumOrderInfo);
                        ErrorLog errorLog = ErrorLog.builder()
                                .address(tgPremiumOrderInfo.getRechargeTgUserName())
                                .otherId(String.valueOf(tgPremiumOrderInfo.getIdTgPremiumOrderInfo()))
                                .errorCode("checkFragmentPaymentStatus_error_timeout")
                                .errorMsg("systemScanTimeOut")
                                .fcu("system")
                                .lcu("system").build();

                        errorLogService.insertErrorLog(errorLog);
                    }
                }




            }

        } catch (IOException e) {
            String exceptionString = LogUtils.doRecursiveReversePrintStackCause(e, 5, ForwardCounter.builder().count(0).build(), 5);
            log.error("checkFragmentPaymentStatus_error:{}", exceptionString);
            ErrorLog errorLog = ErrorLog.builder()
                    .address(tgPremiumOrderInfo.getRechargeTgUserName())
                    .otherId(String.valueOf(tgPremiumOrderInfo.getIdTgPremiumOrderInfo()))
                    .errorCode("checkFragmentPaymentStatus_error")
                    .errorMsg(exceptionString.length() > 2000 ? exceptionString.substring(0, 2000) : exceptionString)
                    .fcu("system")
                    .lcu("system").build();

            errorLogService.insertErrorLog(errorLog);
        }
    }
}


