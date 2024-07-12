package com.ruoyi.system.handler;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.common.core.domain.entity.TenantInfo;
import com.ruoyi.common.core.domain.entity.TrxExchangeFail;
import com.ruoyi.common.core.domain.entity.TrxExchangeInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.ForwardCounter;
import com.ruoyi.common.utils.LogUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.encrpt.Dt;
import com.ruoyi.common.utils.http.RestTemplateUtils;
import com.ruoyi.system.bot.TgLongPollingBot;
import com.ruoyi.system.bot.utils.SendContent;
import com.ruoyi.system.domain.MonitorAddressAccount;
import com.ruoyi.system.dto.Contract;
import com.ruoyi.system.dto.Data;
import com.ruoyi.system.dto.TronGridResponse;
import com.ruoyi.system.dto.Value;
import com.ruoyi.system.mapper.TenantInfoMapper;
import com.ruoyi.system.mapper.TrxExchangeFailMapper;
import com.ruoyi.system.mapper.TrxExchangeInfoMapper;
import com.ruoyi.system.service.IErrorLogService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.util.AddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Common;
import org.tron.trident.proto.Response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TRX2EneryTransferHandler {

    @Autowired
    private ISysConfigService configService;
    @Autowired(required = false)
    private TgLongPollingBot longPollingBot;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private TenantInfoMapper tenantInfoMapper;
    @Autowired
    private TrxExchangeInfoMapper trxExchangeInfoMapper;
    @Autowired
    private SendContent sendContent;
    @Autowired
    private IErrorLogService errorLogService;

    @Autowired
    private TrxExchangeFailMapper trxExchangeFailMapper;

    public void doMonitorTrxTransferByMonitorAddressInfo(MonitorAddressAccount monitorAddressAccount) {

        String monitorAddress = monitorAddressAccount.getMonitorAddress();
        String apiKey = monitorAddressAccount.getApiKey();

        ResponseEntity responseEntity = null;
        try {
            responseEntity = getTransActionList(monitorAddress, apiKey);
        } catch (Exception e) {

            String exceptionString = LogUtils.doRecursiveReversePrintStackCause(e, 5, ForwardCounter.builder().count(0).build(), 5);

            log.error("获取交易列表异常:{}", exceptionString);
            ErrorLog errorLog = ErrorLog.builder()
                    .address(monitorAddressAccount.getMonitorAddress())
                    .errorCode("getTransAction")
                    .errorMsg(exceptionString.length() > 2000 ? exceptionString.substring(0, 2000) : exceptionString)
                    .fcu("system")
                    .lcu("system").build();
            errorLogService.insertErrorLog(errorLog);
        }

        Object responseEntityBody = getResponseEntityBody(responseEntity, monitorAddress);

        if (responseEntityBody == null) return;
        TronGridResponse tronGridResponse = JSONUtil.toBean((String) responseEntityBody, TronGridResponse.class);
        if (log.isInfoEnabled()) {
            log.info("{}responseEntityBody:{}", monitorAddress, JSONUtil.toJsonStr(tronGridResponse));
        }
        List<Data> dataList = tronGridResponse.getData();
        for (Data data : dataList) {
            //交易hash判断有没有处理过
            String txID = data.getTxID();
            Boolean hasKey = redisTemplate.hasKey("transfer_trx_" + txID);
            if (hasKey) {
                continue;
            }


            RLock lock = redissonClient.getLock("lock_" + txID);
            try {
                boolean res = lock.tryLock(3, 50, TimeUnit.SECONDS);
                if (!res) {
                    //有其他程序正在处理则不需要再处理
                    continue;
                }

                Contract contract = data.getRaw_data().getContract().get(0);

                String type = contract.getType();
                //判断是否转账业务
                if (Chain.Transaction.Contract.ContractType.TransferContract.name().equals(type)) {
                    //判断是否是租户
                    doDelegateResource(contract, txID, monitorAddressAccount);
                } else {
                    log.info("txID:{}其他业务类型不处理:{}", txID, JSONUtil.toJsonStr(data));
                }
            } catch (Exception e) {

                String exceptionString = LogUtils.doRecursiveReversePrintStackCause(e, 5, ForwardCounter.builder().count(0).build(), 5);

//                log.error("获取交易列表异常:{}", exceptionString);
                log.error("doDelegateResource业务处理异常{},txid:{},exception:{}", monitorAddress, txID, e);
                ErrorLog errorLog = ErrorLog.builder()
                        .address(monitorAddressAccount.getMonitorAddress())
                        .trxId(txID)
                        .errorCode("doDelegateResource")
                        .errorMsg(exceptionString.length() > 2000 ? exceptionString.substring(0, 2000) : exceptionString)
                        .fcu("system")
                        .lcu("system").build();
                errorLogService.insertErrorLog(errorLog);

            } finally {
                if (lock.isLocked()) {
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
        }

    }

    /**
     * 获取交易列表
     *
     * @param address 监听地址
     * @param apiKey
     * @return
     */
    private ResponseEntity getTransActionList(String address, String apiKey) {
        ResponseEntity responseEntity;
        HttpHeaders headers = new HttpHeaders();
        headers.add("TRON-PRO-API-KEY", apiKey);
        //监听
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://api.trongrid.io/v1/accounts/" + address + "/transactions");
//        builder.queryParam("only_confirmed", true);
        builder.queryParam("only_to", true);
        builder.queryParam("limit", 200);
        String sysTransferBetween = configService.selectConfigByKey("sys.transfer.between");

        DateTime min_timestamp = DateUtil.offset(new Date(), DateField.MINUTE, Integer.valueOf(sysTransferBetween));
        builder.queryParam("min_timestamp", min_timestamp.getTime());

        String uriString = builder.toUriString();
        responseEntity = RestTemplateUtils.get(uriString, headers, String.class);
        return responseEntity;
    }


    /**
     * 获取响应体
     *
     * @param responseEntity
     * @param address
     * @return
     */
    private static Object getResponseEntityBody(ResponseEntity responseEntity, String address) {
        if (responseEntity == null) {
            log.warn("{}:responseEntity is null", address);
            return null;
        }
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            log.error("获取trx交易信息失败:{}", address);
            return null;
        }
        Object responseEntityBody = responseEntity.getBody();
        if (responseEntityBody == null) {
            log.warn("{}:responseEntityBody is null", address);
            return null;
        }
        return responseEntityBody;
    }

    /**
     * 进行资源代理操作
     *
     * @param contract
     * @param txID
     * @param monitorAddressAccount
     * @throws IllegalException
     */
    @Transactional
    public void doDelegateResource(Contract contract, String txID, MonitorAddressAccount monitorAddressAccount) throws Exception {

        Value value = contract.getParameter().getValue();
        //转账金额需除以10的6次方精度
        long amountSun = value.getAmount();
        long amount = amountSun / 1000000L;

        if (amount < 1) {
            log.info("txID:{}金额{}小于1TRX不做处理", txID, amountSun);
            return;
        }
        String ownerAddress = value.getOwner_address();
        String toAddress = value.getTo_address();

        long lockPeriod = 1200L;
        long transferCount = 0L;
        Long price = null;
        String sysEnergyBusiType = DictUtils.getDictValue("sys_energy_busi_type", "闪兑套餐");

        //查询是否是按天支付的租户
        TenantInfo tenantInfoExample = new TenantInfo();
        tenantInfoExample.setReceiverAddress(ownerAddress);
//        tenantInfoExample.setMonitorAddress(monitorAddressAccount.getMonitorAddress());
        tenantInfoExample.setIsPaid(UserConstants.NO);
        tenantInfoExample.setStatus(DictUtils.getDictValue("sys_tenant_status", "生效中"));
        List<TenantInfo> tenantInfoList = tenantInfoMapper.selectTenantInfoList(tenantInfoExample);
        TenantInfo tenantInfo = null;
        if (tenantInfoList.size() > 0) {
            tenantInfo = tenantInfoList.get(0);
            Long exchangeAmount = tenantInfo.getExchangeAmount();
            //判断入账金额是否与转入金额相等,如果相等则设置为已支付
            if (amount != exchangeAmount) {
                log.info("txID:{}金额{}不是入账金额{}不做处理", txID, amountSun, exchangeAmount);
                ErrorLog errorLog = ErrorLog.builder()
                        .address(ownerAddress)
                        .errorCode("tenantTansferAmount")
                        .errorMsg("金额" + amountSun + "不是入账金额" + exchangeAmount + "不做处理")
                        .trxId(txID)
                        .fcu("system")
                        .lcu("system").build();
                errorLogService.insertErrorLog(errorLog);
                return;
            }
            tenantInfo.setIsPaid("Y");
            tenantInfo.setLcu("system");
            tenantInfo.setTxId(txID);
            tenantInfo.setDelegatedDays(tenantInfo.getDelegatedDays() + 1);

            //取包天的套餐锁定时间和交易笔数
            lockPeriod = 24;
            sysEnergyBusiType = DictUtils.getDictValue("sys_energy_busi_type", "天数套餐");
            transferCount = tenantInfo.getTransferCount();
            price = tenantInfo.getPrice();

        } else {
            price = monitorAddressAccount.getTrxPrice().longValue();
            transferCount = amount / price;

            if (transferCount < 1) {
                log.info("txID:{}金额{}不是单价的倍数不做处理", txID, amountSun);
                return;
            }

            lockPeriod = new Long(monitorAddressAccount.getBindPeriod());
        }

        String accountAddress = monitorAddressAccount.getAccountAddress();
        String encryptPrivateKey = monitorAddressAccount.getEncryptPrivateKey();
        String encryptKey = monitorAddressAccount.getEncryptKey();
        String apiKey = monitorAddressAccount.getApiKey();

        String decryptPrivateKey = Dt.decrypt(encryptPrivateKey, encryptKey);

        ApiWrapper apiWrapper = null;
        try {
            apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey, apiKey);
        } catch (Exception e) {
            String exceptionString = LogUtils.doRecursiveReversePrintStackCause(e, 5, ForwardCounter.builder().count(0).build(), 5);

//                log.error("获取交易列表异常:{}", exceptionString);
            log.error("ofMainnet业务处理异常{},txid:{},exception:{}", monitorAddressAccount.getMonitorAddress(), txID, e);
            ErrorLog errorLog = ErrorLog.builder()
                    .address(monitorAddressAccount.getMonitorAddress())
                    .trxId(txID)
                    .errorCode("ofMainnet")
                    .errorMsg(exceptionString.length() > 2000 ? exceptionString.substring(0, 2000) : exceptionString)
                    .fcu("system")
                    .lcu("system").build();

            errorLogService.insertErrorLog(errorLog);

            throw new RuntimeException(e);
        }


        calcBalanceAndDelegate(txID, apiWrapper, accountAddress, transferCount, ownerAddress, lockPeriod, toAddress, price, sysEnergyBusiType, amount,"TRX","system",Common.ResourceCode.ENERGY.name(),"energy_used");
        //持久化之后放redis
        redisTemplate.opsForValue().set("transfer_trx_" + txID, txID, 1, TimeUnit.DAYS);
        if (tenantInfo != null) {
            tenantInfoMapper.updateTenantInfo(tenantInfo);
        }

    }


    /**
     * @param txID              转账交易订单号
     * @param apiWrapper        apiWrapper
     * @param accountAddress    出账地址
     * @param transferCount     转账笔数
     * @param ownerAddress      转入地址
     * @param lockPeriod        锁定周期
     * @param toAddress         监听地址
     * @param price             单价
     * @param sysEnergyBusiType 业务类型
     * @param amount            转入金额
     * @param currentUser       当前处理人
     * @param calcRule
     * @throws Exception 异常
     */
    public void calcBalanceAndDelegate(String txID,
                                       ApiWrapper apiWrapper,
                                       String accountAddress,
                                       long transferCount,
                                       String ownerAddress,
                                       long lockPeriod,
                                       String toAddress,
                                       Long price,
                                       String sysEnergyBusiType,
                                       Long amount,
                                       String trxAmountUnit,
                                       String currentUser,
                                       String resourceCode, String calcRule) throws Exception {

        String systronApiSwitch = configService.selectConfigByKey("sys.tron.api");
        Long balance = null;
        String delegateResourceTxid = null;
        if (UserConstants.YES.equals(systronApiSwitch)) {
            Response.AccountResourceMessage accountResource = apiWrapper.getAccountResource(accountAddress);

            //总用于质押换取能量的trx上限
            balance = getBalance(accountResource, transferCount, resourceCode);

            /*  lock_period: 锁定周期，以区块时间（3s）为单位，表示锁定多少个区块的时间，当lock为true时，该字段有效。如果代理锁定期为1天，则lock_period为：28800*/

            try {
                delegateResourceTxid = getDelegateResourceTxid(apiWrapper, accountAddress, balance, ownerAddress, resourceCode);
            } catch (Exception e) {
                Object cacheidTrxExchangeFail = redisTemplate.opsForValue().get("transfer_trx_fail_" + txID);
                if (cacheidTrxExchangeFail == null){
                    TrxExchangeFail trxExchangeFail = new TrxExchangeFail();
                    trxExchangeFail.setFromAddress(ownerAddress);
                    trxExchangeFail.setToAddress(toAddress);
                    trxExchangeFail.setAccountAddress(accountAddress);
                    trxExchangeFail.setPrice(price);
                    trxExchangeFail.setTrxTxId(txID);
                    trxExchangeFail.setTranferCount(transferCount);
                    trxExchangeFail.setEnergyBusiType(sysEnergyBusiType);
                    trxExchangeFail.setTrxAmount(amount);
                    trxExchangeFail.setTrxAmountUnit(trxAmountUnit);
                    trxExchangeFail.setResourceCode(resourceCode);
                    trxExchangeFail.setLockPeriod(lockPeriod);
                    trxExchangeFail.setDelegateStatus("2");
                    trxExchangeFail.setCalcRule(calcRule);
                   trxExchangeFailMapper.insertTrxExchangeFail(trxExchangeFail);
                    redisTemplate.opsForValue().set("transfer_trx_fail_" + txID, trxExchangeFail.getIdTrxExchangeFail(), 1, TimeUnit.HOURS);
                }
                throw new RuntimeException(e);
            }

        }
        String fromAddress = AddressUtil.hexToBase58(ownerAddress);
        TrxExchangeInfo trxExchangeInfo = TrxExchangeInfo.builder()
                .fromAddress(fromAddress)
                .toAddress(AddressUtil.hexToBase58(toAddress))
                .accountAddress(accountAddress)
                .price(price)
                .trxTxId(txID)
                .tranferCount(transferCount)
                .energyBusiType(sysEnergyBusiType)
                .trxAmount(amount)
                .trxAmountUnit(trxAmountUnit)
                .delegateAmountTrx(balance)
                .resourceCode(resourceCode)
                .delegateTxId(delegateResourceTxid)
                .lockPeriod(lockPeriod)
                .calcRule(calcRule)
                .delegateStatus("0")
                .fcd(new Date())
                .fcu(currentUser)
                .lcd(new Date())
                .lcu(currentUser)
                .build();
        trxExchangeInfoMapper.insertTrxExchangeInfo(trxExchangeInfo);

        Object cacheidTrxExchangeFail = redisTemplate.opsForValue().get("transfer_trx_fail_" + txID);
        if (cacheidTrxExchangeFail != null){

            TrxExchangeFail trxExchangeFail = new TrxExchangeFail();
            trxExchangeFail.setIdTrxExchangeFail(Long.valueOf(cacheidTrxExchangeFail.toString()));
            trxExchangeFail.setDelegateStatus("1");
            trxExchangeFailMapper.updateTrxExchangeFail(trxExchangeFail);
            redisTemplate.delete("transfer_trx_fail_" + txID);
        }

        String sysenTrxTransferNotice = configService.selectConfigByKey("sys.energy.transaction.notice");
        String sysTgGroupChatId = configService.selectConfigByKey("sys.tg.group.chat.id");
        if (longPollingBot != null && StringUtils.isNotEmpty(sysenTrxTransferNotice) && StringUtils.isNotEmpty(sysTgGroupChatId)) {
           /*
           * 转账TRX：{trxAmount}
            单价:     {price}
            兑换笔数:  {transferCount}
            兑换地址：{FromAddress}
            交易哈希：{txId}
            订单时间：{txTime}
               * */
            Map<String, Object> arguments = new HashMap<>();
            arguments.put("trxAmount", amount == null ? "" : amount.toString());
            arguments.put("price", price == null ? "" : price.toString());
            arguments.put("transferCount", transferCount);
            arguments.put("FromAddress", fromAddress.replaceAll("(.{6})(.*)(.{8})", "$1********$3"));
            arguments.put("txId", StringUtils.isEmpty(txID) ? "" : txID.replaceAll("(.{6})(.*)(.{8})", "$1*******************$3"));
            arguments.put("txTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//            String message = MessageFormat.format(sysUsdtTranferNotice, arguments);
            StrSubstitutor substitutor = new StrSubstitutor(arguments, "{", "}");
            String message = substitutor.replace(sysenTrxTransferNotice);
            SendMessage sendMessage = sendContent.messageText(sysTgGroupChatId, message, ParseMode.HTML);
            longPollingBot.execute(sendMessage);
        } else {
            log.warn("longPollingBot OR sysUsdtTranferNotice OR sysTgGroupChatId  is null");
        }

    }


    private long getBalance(Response.AccountResourceMessage accountResource, long transferCount, String resourceCode) {
        Long balance = null;
        if (resourceCode.equals(Common.ResourceCode.ENERGY.name())) {
            long totalEnergyLimit = accountResource.getTotalEnergyLimit();
            //已经用于质押换取能量的trx
            long totalEnergyWeight = accountResource.getTotalEnergyWeight();

            //系数 保留一位或者保留2位,算出trx往上进一位
            BigDecimal trxToFreezeEnergyTimes = BigDecimal.valueOf(totalEnergyLimit).divide(BigDecimal.valueOf(totalEnergyWeight), 2, BigDecimal.ROUND_DOWN);
            //计算代理的数量
//        long balance = energyNum * 32000 / 12;
            balance = BigDecimal.valueOf(transferCount * 32000).divide(trxToFreezeEnergyTimes, 0, BigDecimal.ROUND_UP).longValue();
        }else {

            balance = transferCount * 350;
        }
        return balance;
    }

    private String getDelegateResourceTxid(ApiWrapper apiWrapper, String accountAddress, long balance, String ownerAddress, String resourceCode) throws IllegalException {

        Response.TransactionExtention transactionExtention = apiWrapper.delegateResourceV2(accountAddress, balance * 1000000, Common.ResourceCode.valueOf(resourceCode).getNumber(), ownerAddress, false, 0);

        Chain.Transaction transaction = apiWrapper.signTransaction(transactionExtention);

        String delegateResourceTxid = apiWrapper.broadcastTransaction(transaction);

        return delegateResourceTxid;
    }

}
