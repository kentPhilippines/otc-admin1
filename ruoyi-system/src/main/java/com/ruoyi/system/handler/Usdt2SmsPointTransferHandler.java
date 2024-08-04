package com.ruoyi.system.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.common.core.domain.entity.PointRechargeOrder;
import com.ruoyi.common.core.domain.entity.UserPoint;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.ForwardCounter;
import com.ruoyi.common.utils.LogUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.api.IOkxApi;
import com.ruoyi.system.api.ITronApi;
import com.ruoyi.system.api.enums.ContractType;
import com.ruoyi.system.api.enums.Symbol;
import com.ruoyi.system.bot.TgLongPollingBot;
import com.ruoyi.system.bot.utils.SendContent;
import com.ruoyi.system.domain.MonitorAddressAccount;
import com.ruoyi.system.dto.Data;
import com.ruoyi.system.dto.Token_info;
import com.ruoyi.system.dto.TronGridResponse;
import com.ruoyi.system.mapper.PointRechargeOrderMapper;
import com.ruoyi.system.mapper.UsdtExchangeInfoMapper;
import com.ruoyi.system.mapper.UserPointMapper;
import com.ruoyi.system.service.IErrorLogService;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class Usdt2SmsPointTransferHandler {

    @Autowired
    private ITronApi tronApi;
    @Autowired
    private IOkxApi okxApi;

    @Autowired
    private PointRechargeOrderMapper pointRechargeOrderMapper;

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
    @Autowired
    private UserPointMapper userPointMapper;

    public void doMonitorUsdt2SmsPointTransfer(MonitorAddressAccount monitorAddressAccount) {

        try {
            String monitorAddress = monitorAddressAccount.getMonitorAddress();
            String apiKey = DictUtils.getRandomDictValue("sys_tron_api_key");
            String sysTransferBetween = configService.selectConfigByKey("sys.transfer.between");
//
            DateTime min_timestamp = DateUtil.offset(new Date(), DateField.MINUTE, Integer.valueOf(sysTransferBetween));

            TronGridResponse tronGridResponse = tronApi.getTronGridTrc20Response(monitorAddress, true, false, apiKey, min_timestamp.getTime());

            List<Data> dataList = tronGridResponse.getData();
            if (CollectionUtil.isEmpty(dataList)) {
                return;
            }


            for (Data data : dataList) {
                doMonitorUsdt2SmsPointTransferByData(monitorAddressAccount, data);
            }
        } catch (Exception e) {
            String exceptionString = LogUtils.doRecursiveReversePrintStackCause(e, 5, ForwardCounter.builder().count(0).build(), 5);
            log.error("短信模块获取trx20交易列表异常:{}", exceptionString);
            ErrorLog errorLog = ErrorLog.builder()
                    .address(monitorAddressAccount.getMonitorAddress())
                    .errorCode("smsGetTransAction")
                    .errorMsg(exceptionString.length() > 2000 ? exceptionString.substring(0, 2000) : exceptionString)
                    .fcu("system")
                    .lcu("system").build();
            errorLogService.insertErrorLog(errorLog);

            throw new RuntimeException("短信模块获取trx20交易列表异常", e);
        }

    }

    private void doMonitorUsdt2SmsPointTransferByData(MonitorAddressAccount monitorAddressAccount, Data data) throws Exception {
        BigDecimal transferValue = getTransferValue(data);
        if (transferValue == null) return;

        //转账地址
        String from = data.getFrom();
        //接收地址
        String dataTo = data.getTo();
        String transactionId = data.getTransaction_id();

        Boolean hasKey = redisTemplate.hasKey("transfer_USDT_SMS_" + transactionId);
        if (hasKey) {
            //已经处理过,不处理了
            return;
        }

        RLock lock = redissonClient.getLock("lock_" + transactionId);
        try {
            boolean res = lock.tryLock(2, 5, TimeUnit.SECONDS);
            if (!res) {
                //有其他程序正在处理则不需要再处理
                return;
            }

            Boolean hasSmsKey = redisTemplate.hasKey("sms_recharge_amount_" + transferValue);
            if (!hasSmsKey) {
                //没有这笔交易代表要么过期,已经被处理,或者转账金额不对 回头加张表记录下
                doSendTgNotice(from, transferValue, transactionId);
                redisTemplate.opsForValue().set("transfer_USDT_" + transactionId, transactionId, 1, TimeUnit.DAYS);
                return;
            }
            PointRechargeOrder pointRechargeOrder = (PointRechargeOrder) redisTemplate.opsForValue().get("sms_recharge_amount_" + transferValue);
            pointRechargeOrder.setStatus("Y");
            pointRechargeOrder.setFromAddress(from);
            pointRechargeOrder.setToAddress(dataTo);
            pointRechargeOrder.setTxId(transactionId);
            pointRechargeOrder.setUpdateTime(new Date());
            pointRechargeOrder.setUpdateBy("system");
            pointRechargeOrderMapper.updatePointRechargeOrder(pointRechargeOrder);

            createOrUpdateUserPoints(pointRechargeOrder,null);

            redisTemplate.delete("sms_recharge_amount_" + transferValue);

            redisTemplate.opsForValue().set("transfer_USDT_SMS_" + transactionId, transactionId, 1, TimeUnit.DAYS);
        } finally {
            if (lock.isLocked()) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }





    }

    private void doSendTgNotice( String from, BigDecimal transferValue, String txId) throws TelegramApiException {

        if (longPollingBot != null ) {
            CompletableFuture.runAsync(()->{
                String sysUsdtTranferNotice = configService.selectConfigByKey("sys.sms.warn.tg");
                String sysTgGroupChatId = configService.selectConfigByKey("sys.tg.group.chat.id");
                /*短信模块金额不存在告警
                转账金额：{amount} U
                兑换地址：{FromAddress}
                交易哈希：{txId}
                订单时间：{txTime}
                请注意检查客户是否重复转账,订单已过期或者转账金额小数点不对*/
                Map<String, Object> arguments = new HashMap<>();
                arguments.put("amount", transferValue);
                arguments.put("FromAddress", from.replaceAll("(.{6})(.*)(.{8})", "$1\\\\*\\\\*\\\\*\\\\*$3"));
                arguments.put("txId", txId.replaceAll("(.{6})(.*)(.{8})", "$1\\\\*\\\\*\\\\*\\\\*\\\\*\\\\*\\\\*\\\\*$3"));
                arguments.put("txTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                StrSubstitutor substitutor = new StrSubstitutor(arguments, "{", "}");
                String message = substitutor.replace(sysUsdtTranferNotice);
                SendMessage sendMessage = sendContent.messageText(sysTgGroupChatId, message, ParseMode.MARKDOWN);
                try {
                    longPollingBot.execute(sendMessage);
                } catch (TelegramApiException e) {
                    log.error("longPollingBot execute exception",e);
                }
            });
        } else {
            log.warn("longPollingBot  is null");
        }
    }

    public void createOrUpdateUserPoints(PointRechargeOrder pointRechargeOrder,String loginName) {
        Long userId = pointRechargeOrder.getUserId();
        RLock lock = redissonClient.getLock("lock_" + userId);
        try {
            boolean res = lock.tryLock(1, 2, TimeUnit.SECONDS);
            if (!res) {
                //有其他程序正在处理则不需要再处理
                return;
            }

            UserPoint userPoint = new UserPoint();
            userPoint.setUserId(userId);
            List<UserPoint> userPoints = userPointMapper.selectUserPointList(userPoint);
            String name = StringUtils.isNotBlank(loginName) ? loginName : "system";
            if (CollectionUtil.isNotEmpty(userPoints)) {
                UserPoint userPointResult = userPoints.get(0);
                BigDecimal pointBalance = userPointResult.getPointBalance();
                if (pointBalance == null){
                    pointBalance=BigDecimal.ZERO;
                }
                BigDecimal add = pointBalance.add(pointRechargeOrder.getPoints());
                userPointResult.setPointBalance(add);
                userPointResult.setUpdateTime(new Date());

                userPointResult.setUpdateBy(name);
                userPointMapper.updateUserPoint(userPointResult);
            } else {
                userPoint.setCreateTime(new Date());
                userPoint.setCreateBy(name);
                userPoint.setUpdateBy(name);
                userPoint.setPointBalance(pointRechargeOrder.getPoints() == null ? BigDecimal.ZERO : pointRechargeOrder.getPoints());
                userPointMapper.insertUserPoint(userPoint);
            }
        } catch (InterruptedException e) {
            log.error("更新用户积分异常用户积分异常:{}",userId, e);
            throw new RuntimeException(e);
        } finally {
            if (lock.isLocked()) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
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
        return transferValue.setScale(4);
    }


}
