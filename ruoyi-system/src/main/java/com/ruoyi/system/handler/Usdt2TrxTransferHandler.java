package com.ruoyi.system.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Preconditions;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.common.core.domain.entity.UsdtExchangeInfo;
import com.ruoyi.common.utils.ForwardCounter;
import com.ruoyi.common.utils.LogUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.encrpt.Dt;
import com.ruoyi.system.api.IOkxApi;
import com.ruoyi.system.api.ITronApi;
import com.ruoyi.system.api.entity.okx.OkxResponse;
import com.ruoyi.system.api.enums.ContractType;
import com.ruoyi.system.api.enums.Symbol;
import com.ruoyi.system.bot.TgLongPollingBot;
import com.ruoyi.system.bot.utils.SendContent;
import com.ruoyi.system.domain.MonitorAddressAccount;
import com.ruoyi.system.dto.Data;
import com.ruoyi.system.dto.Token_info;
import com.ruoyi.system.dto.TronGridResponse;
import com.ruoyi.system.mapper.UsdtExchangeInfoMapper;
import com.ruoyi.system.service.IAccountAddressInfoService;
import com.ruoyi.system.service.IErrorLogService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.util.AddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class Usdt2TrxTransferHandler {

    @Autowired
    private ITronApi tronApi;
    @Autowired
    private IOkxApi okxApi;

    @Autowired
    private IAccountAddressInfoService accountAddressInfoService;

    @Autowired
    private UsdtExchangeInfoMapper usdtExchangeInfoMapper;
    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired(required = false)
    private TgLongPollingBot longPollingBot;
    @Autowired
    private SendContent sendContent;

    @Autowired
    private IErrorLogService errorLogService;

    public void doMonitorUsdtTransfer(MonitorAddressAccount monitorAddressAccount) {

        try {
            String monitorAddress = monitorAddressAccount.getMonitorAddress();
            String apiKey = monitorAddressAccount.getApiKey();
            String sysTransferBetween = configService.selectConfigByKey("sys.transfer.between");
//
            DateTime min_timestamp = DateUtil.offset(new Date(), DateField.MINUTE, Integer.valueOf(sysTransferBetween));

            TronGridResponse tronGridResponse = tronApi.getTronGridTrc20Response(monitorAddress, true, false, apiKey, min_timestamp.getTime());

            List<Data> dataList = tronGridResponse.getData();
            if (CollectionUtil.isEmpty(dataList)) {
                return;
            }


            //获取欧易费率
//            BigDecimal oneUsdtToTrx = getOneUsdtToTrx();

            Pair<BigDecimal, BigDecimal> oneUsdtToTrxPair = getOneUsdtToTrx();


            for (Data data : dataList) {
                doMonitorUsdtTransferByData(monitorAddressAccount, data, oneUsdtToTrxPair, apiKey);
            }
        } catch (Exception e) {
            String exceptionString = LogUtils.doRecursiveReversePrintStackCause(e, 5, ForwardCounter.builder().count(0).build(), 5);
            log.error("获取trx20交易列表异常:{}", exceptionString);
            ErrorLog errorLog = ErrorLog.builder()
                    .address(monitorAddressAccount.getMonitorAddress())
                    .errorCode("getTransAction")
                    .errorMsg(exceptionString.length() > 2000 ? exceptionString.substring(0, 2000) : exceptionString)
                    .fcu("system")
                    .lcu("system").build();
            errorLogService.insertErrorLog(errorLog);

            throw new RuntimeException("获取trx20交易列表异常", e);
        }

    }

    private void doMonitorUsdtTransferByData(MonitorAddressAccount monitorAddressAccount, Data data, Pair<BigDecimal, BigDecimal> oneUsdtToTrxPair, String apiKey) throws Exception {
        BigDecimal transferValue = getTransferValue(data);
        if (transferValue == null) return;

        BigDecimal trxValue = transferValue.multiply(oneUsdtToTrxPair.getFirst()).setScale(6, BigDecimal.ROUND_HALF_DOWN);

        String accountAddress = monitorAddressAccount.getAccountAddress();
        String encryptPrivateKey = monitorAddressAccount.getEncryptPrivateKey();
        String encryptKey = monitorAddressAccount.getEncryptKey();
        String decryptPrivateKey = Dt.decrypt(encryptPrivateKey, encryptKey);

        //转账地址
        String from = data.getFrom();
        //接收地址
        String dataTo = data.getTo();
        String transactionId = data.getTransaction_id();

        Boolean hasKey = redisTemplate.hasKey("transfer_USDT_" + transactionId);
        if (hasKey) {
            return;
        }

        RLock lock = redissonClient.getLock("lock_" + transactionId);
        try {
            boolean res = lock.tryLock(3, 50, TimeUnit.SECONDS);
            if (!res) {
                //有其他程序正在处理则不需要再处理
                return;
            }

            doTransferUsdtAndStore(oneUsdtToTrxPair, apiKey, decryptPrivateKey, accountAddress, from, trxValue, dataTo, transactionId, transferValue);

            redisTemplate.opsForValue().set("transfer_USDT_" + transactionId, transactionId, 1, TimeUnit.DAYS);
        } finally {
            if (lock.isLocked()) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }

    public void doTransferUsdtAndStore(Pair<BigDecimal, BigDecimal> oneUsdtToTrxPair, String apiKey, String decryptPrivateKey, String accountAddress, String from, BigDecimal trxValue, String dataTo, String transactionId, BigDecimal transferValue) throws IllegalException, TelegramApiException {

        String systronApiSwitch = configService.selectConfigByKey("sys.tron.api");

        String txId = null;
        if (UserConstants.YES.equals(systronApiSwitch)) {

            ApiWrapper apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey, apiKey);

            //转账
            Response.TransactionExtention transfer = apiWrapper.transfer(accountAddress, from, trxValue.movePointRight(6).longValue());
            //签名
            Chain.Transaction transaction = apiWrapper.signTransaction(transfer);
            //广播
            txId = apiWrapper.broadcastTransaction(transaction);
        }


        UsdtExchangeInfo usdtExchangeInfo = new UsdtExchangeInfo();
        usdtExchangeInfo.setFromAddress(AddressUtil.hexToBase58(from))
                .setToAddress(AddressUtil.hexToBase58(dataTo))
                .setAccountAddress(accountAddress)
                .setUsdtTxId(transactionId)
                .setUsdtAmount(transferValue)
                .setTrxAmount(trxValue)
                .setExchangeRate(oneUsdtToTrxPair == null ? null : oneUsdtToTrxPair.getFirst())
                .setOrginalExchangeRate(oneUsdtToTrxPair == null ? null :oneUsdtToTrxPair.getSecond())
                .setTrxTxId(txId)
                .setFcu("system")
                .setLcu("system");


        usdtExchangeInfoMapper.insertUsdtExchangeInfo(usdtExchangeInfo);

        if (oneUsdtToTrxPair == null){
            return;
        }
        doSendTgNotice(oneUsdtToTrxPair.getFirst(), from, trxValue, transferValue, txId);
    }

    private void doSendTgNotice(BigDecimal oneUsdtToTrx, String from, BigDecimal trxValue, BigDecimal transferValue, String txId) throws TelegramApiException {
        String sysUsdtTranferNotice = configService.selectConfigByKey("sys.usdt.tranfer.notice");
        String sysTgGroupChatId = configService.selectConfigByKey("sys.tg.group.chat.id");
        if (longPollingBot != null && StringUtils.isNotEmpty(sysUsdtTranferNotice) && StringUtils.isNotEmpty(sysTgGroupChatId)) {
            Map<String, Object> arguments = new HashMap<>();
            arguments.put("usdtAmount", transferValue.setScale(2, BigDecimal.ROUND_HALF_DOWN));
            arguments.put("exchangeRate", oneUsdtToTrx);
            arguments.put("trxAmount", trxValue);
            arguments.put("FromAddress", from.replaceAll("(.{6})(.*)(.{8})", "$1********$3"));
            arguments.put("txId", txId.replaceAll("(.{6})(.*)(.{8})", "$1*******************$3"));
            arguments.put("txTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//            String message = MessageFormat.format(sysUsdtTranferNotice, arguments);
            StrSubstitutor substitutor = new StrSubstitutor(arguments, "{", "}");
            String message = substitutor.replace(sysUsdtTranferNotice);
            SendMessage sendMessage = sendContent.messageText(sysTgGroupChatId, message, ParseMode.HTML);
            longPollingBot.execute(sendMessage);
        } else {
            log.warn("longPollingBot OR sysUsdtTranferNotice OR sysTgGroupChatId  is null");
        }
    }

    /**
     * 获取对方转账的U数量
     *
     * @param data
     * @return
     */
    private BigDecimal getTransferValue(Data data) {
        String transFerName = ContractType.Transfer.name();
        if (!transFerName.equals(data.getType())) {
            log.warn("未知交易类型:{},交易数据:{}", data.getType(), JSONUtil.toJsonStr(data));
            return null;
        }


        Token_info tokenInfo = data.getToken_info();

        if (!Symbol.USDT.name().equals(tokenInfo.getSymbol())) {
            log.warn("未知交易币种:{},交易数据:{}", tokenInfo.getSymbol(), JSONUtil.toJsonStr(data));
            return null;
        }
        int decimals = tokenInfo.getDecimals();


        //转账金额
        BigDecimal transferValue = new BigDecimal(data.getValue()).movePointLeft(decimals);

        if (transferValue.compareTo(BigDecimal.ONE) == -1) {
            log.warn("交易金额小于1,交易数据:{}", JSONUtil.toJsonStr(data));
            return null;
        }
        return transferValue;
    }

    /**
     * 获取欧易费率 1U
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public Pair<BigDecimal, BigDecimal> getOneUsdtToTrx() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        OkxResponse oksResponse = okxApi.getSingleTickerOkxResponse2();
        Preconditions.checkNotNull(oksResponse, "查询费率失败无法兑换");
        String code = oksResponse.getCode();

        Preconditions.checkState("0".equals(code), "okx响应码异常:" + JSONUtil.toJsonStr(oksResponse));

        List<com.ruoyi.system.api.entity.okx.Data> oksResponseDataList = oksResponse.getData();
        Preconditions.checkState(CollectionUtil.isNotEmpty(oksResponseDataList), "okx费率响应为空:" + JSONUtil.toJsonStr(oksResponse));

        log.info("oksResponse:{}", oksResponse);
        String last = oksResponseDataList.get(0).getLast();

//        BigDecimal oneUsdtToTrx = BigDecimal.ONE.divide(new BigDecimal(last)).setScale(6, BigDecimal.ROUND_HALF_DOWN);

        double oneUsdtToTrxDouble = 1 / new Double(last);
        BigDecimal oneUsdtToTrx = BigDecimal.valueOf(oneUsdtToTrxDouble).setScale(6, BigDecimal.ROUND_HALF_DOWN);

        log.info("实时汇率:{}", oneUsdtToTrx);
        String sysUsdtProportionRate = configService.selectConfigByKey("sys.usdt.proportion.rate");
        BigDecimal discountOneUsdtToTrx = oneUsdtToTrx;
        if (StringUtils.isNotEmpty(sysUsdtProportionRate)) {
            discountOneUsdtToTrx = oneUsdtToTrx.multiply(BigDecimal.ONE.subtract(new BigDecimal(sysUsdtProportionRate))).setScale(6, BigDecimal.ROUND_HALF_DOWN);
        }
        return Pair.of(discountOneUsdtToTrx, oneUsdtToTrx);
    }


    public void topicGroupMessage() {


        String sysUsdtGroupTopic = configService.selectConfigByKey("sys.usdt.group.topic");
        String sysTgGroupChatId = configService.selectConfigByKey("sys.tg.group.chat.id");

        log.info("longPollingBot:{},sysUsdtGroupTopic:{},sysTgGroupChatId:{}", longPollingBot, sysUsdtGroupTopic, sysTgGroupChatId);

        if (longPollingBot != null && StringUtils.isNotEmpty(sysUsdtGroupTopic) && StringUtils.isNotEmpty(sysTgGroupChatId)) {
            log.info("进入这里1");
            try {
                BigDecimal oneUsdtToTrx = getOneUsdtToTrx().getFirst();
                BigDecimal tenUsdtToTrx = oneUsdtToTrx.multiply(BigDecimal.TEN);
                Map<String, Object> arguments = new HashMap<>();
                arguments.put("tenUsdtToTrx", tenUsdtToTrx);
                StrSubstitutor substitutor = new StrSubstitutor(arguments, "{", "}");
                String message = substitutor.replace(sysUsdtGroupTopic);
                SendMessage sendMessage = sendContent.messageText(sysTgGroupChatId, message, ParseMode.MARKDOWN);

                longPollingBot.execute(sendMessage);
            } catch (Exception e) {

                log.error("广播消息异常", e);
            }
        } else {
            log.info("进入这里2");
            log.warn("sysUsdtTranferNotice OR sysTgGroupChatId  is null");
        }
    }


}
