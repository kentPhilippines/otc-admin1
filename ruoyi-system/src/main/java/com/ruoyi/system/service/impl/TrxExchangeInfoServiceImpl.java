package com.ruoyi.system.service.impl;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.common.core.domain.entity.TenantInfo;
import com.ruoyi.common.core.domain.entity.TrxExchangeInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.encrpt.Dt;
import com.ruoyi.common.utils.http.RestTemplateUtils;
import com.ruoyi.system.domain.MonitorAddressAccount;
import com.ruoyi.system.domain.TrxExchange;
import com.ruoyi.system.dto.Contract;
import com.ruoyi.system.dto.Data;
import com.ruoyi.system.dto.TronGridResponse;
import com.ruoyi.system.dto.Value;
import com.ruoyi.system.mapper.ErrorLogMapper;
import com.ruoyi.system.mapper.TenantInfoMapper;
import com.ruoyi.system.mapper.TrxExchangeInfoMapper;
import com.ruoyi.system.service.IAccountAddressInfoService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ITrxExchangeInfoService;
import com.ruoyi.system.util.AddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Common;
import org.tron.trident.proto.Response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * trx兑能量记录Service业务层处理
 *
 * @author dorion
 * @date 2024-04-13
 */
@Service
@Slf4j
public class TrxExchangeInfoServiceImpl implements ITrxExchangeInfoService {
    @Autowired
    private TrxExchangeInfoMapper trxExchangeInfoMapper;

    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IAccountAddressInfoService accountAddressInfoService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ErrorLogMapper errorLogMapper;

    @Autowired
    private TenantInfoMapper tenantInfoMapper;


    /**
     * 查询trx兑能量记录
     *
     * @param idTrxExchangeInfo trx兑能量记录主键
     * @return trx兑能量记录
     */
    @Override
    public TrxExchangeInfo selectTrxExchangeInfoByIdTrxExchangeInfo(Long idTrxExchangeInfo) {
        return trxExchangeInfoMapper.selectTrxExchangeInfoByIdTrxExchangeInfo(idTrxExchangeInfo);
    }

    /**
     * 查询trx兑能量记录列表
     *
     * @param trxExchangeInfo trx兑能量记录
     * @return trx兑能量记录
     */
    @Override
    public List<TrxExchangeInfo> selectTrxExchangeInfoList(TrxExchangeInfo trxExchangeInfo) {
        return trxExchangeInfoMapper.selectTrxExchangeInfoList(trxExchangeInfo);
    }

    /**
     * 新增trx兑能量记录
     *
     * @param trxExchangeInfo trx兑能量记录
     * @return 结果
     */
    @Override
    public int insertTrxExchangeInfo(TrxExchangeInfo trxExchangeInfo) {
        String userName = ShiroUtils.getSysUser().getUserName();
        trxExchangeInfo.setFcu(userName);
        trxExchangeInfo.setLcu(userName);
        return trxExchangeInfoMapper.insertTrxExchangeInfo(trxExchangeInfo);
    }

    /**
     * 修改trx兑能量记录
     *
     * @param trxExchangeInfo trx兑能量记录
     * @return 结果
     */
    @Override
    public int updateTrxExchangeInfo(TrxExchangeInfo trxExchangeInfo) {
        String userName = ShiroUtils.getSysUser().getUserName();
        trxExchangeInfo.setLcu(userName);
        return trxExchangeInfoMapper.updateTrxExchangeInfo(trxExchangeInfo);
    }

    /**
     * 批量删除trx兑能量记录
     *
     * @param idTrxExchangeInfos 需要删除的trx兑能量记录主键
     * @return 结果
     */
    @Override
    public int deleteTrxExchangeInfoByIdTrxExchangeInfos(String idTrxExchangeInfos) {
        return trxExchangeInfoMapper.deleteTrxExchangeInfoByIdTrxExchangeInfos(Convert.toStrArray(idTrxExchangeInfos));
    }

    /**
     * 删除trx兑能量记录信息
     *
     * @param idTrxExchangeInfo trx兑能量记录主键
     * @return 结果
     */
    @Override
    public int deleteTrxExchangeInfoByIdTrxExchangeInfo(Long idTrxExchangeInfo) {
        return trxExchangeInfoMapper.deleteTrxExchangeInfoByIdTrxExchangeInfo(idTrxExchangeInfo);
    }

    @Override
    public int delegate(TrxExchange trxExchange, Boolean isTenant) throws Exception {
        //转账笔数
        Long transferNumber = trxExchange.getTransferNumber();
        //实际锁定周期
        long lockPeriod = trxExchange.getLockNum() * 1200;
        String systronApiSwitch = configService.selectConfigByKey("sys.tron.api");

        String fromAddress = trxExchange.getFromAddress();

        String dictValue = DictUtils.getDictValue("sys_delegate_status", "已委托");

        TrxExchangeInfo trxExchangeInfo = null;
        String userName = ShiroUtils.getLoginName();
        String busiType = DictUtils.getDictValue("sys_busi_type", "闪兑套餐");
        if (isTenant) {
            busiType = DictUtils.getDictValue("sys_busi_type", "天数套餐");
        } else if (lockPeriod > 1200 && lockPeriod <= 24 * 1200) {
            busiType = DictUtils.getDictValue("sys_busi_type", "笔数套餐");
        }
        if (UserConstants.YES.equals(systronApiSwitch)) {
            String accountAddress = trxExchange.getAccountAddress();
            String decryptPrivateKey = accountAddressInfoService.getDecryptPrivateKey(accountAddress);

            String tronApiKey = DictUtils.getDictValue("sys_tron_api_key", "synp@outlook");

            ApiWrapper apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey, tronApiKey);

            calcBalanceAndDelegate(null,
                    apiWrapper,
                    accountAddress,
                    transferNumber,
                    fromAddress,
                    lockPeriod,
                    null,
                    null,
                    busiType,
                    null,
                    userName);
          /*  Response.AccountResourceMessage accountResource = apiWrapper.getAccountResource(accountAddress);

            //总用于质押换取能量的trx上限
            long balance = getBalance(accountResource, transferNumber);

            String delegateResourceTxid = getDelegateResourceTxid(apiWrapper, accountAddress, balance, fromAddress, lockPeriod);


            trxExchangeInfo = TrxExchangeInfo.builder()
                    .delegateAmountTrx(balance)
                    .tranferCount(transferNumber)
                    .busiType(busiType)
                    .lockPeriod(lockPeriod)
                    .accountAddress(trxExchange.getAccountAddress())
                    .fromAddress(fromAddress)
                    .delegateTxId(delegateResourceTxid)
                    .delegateStatus(dictValue)
                    .fcu(userName)
                    .lcu(userName)
                    .build();*/

        } else {
//            String busiType = DictUtils.getDictValue("sys_busi_type", "闪兑套餐");
//            if (isTenant) {
//                busiType = DictUtils.getDictValue("sys_busi_type", "天数套餐");
//            } else if (lockPeriod > 1200 && lockPeriod <= 24 * 1200) {
//                busiType = DictUtils.getDictValue("sys_busi_type", "笔数套餐");
//            }
            trxExchangeInfo = TrxExchangeInfo.builder()
                    .delegateAmountTrx(0L)
                    .tranferCount(transferNumber)
                    .lockPeriod(lockPeriod)
                    .busiType(busiType)
                    .lockPeriod(lockPeriod)
                    .accountAddress(trxExchange.getAccountAddress())
                    .fromAddress(fromAddress)
                    .delegateStatus(dictValue)
                    .fcu(userName)
                    .lcu(userName)
                    .build();
        }

        return trxExchangeInfoMapper.insertTrxExchangeInfo(trxExchangeInfo);
    }


    /**
     * 按照单个地址监听方法
     *
     * @param monitorAddressAccount
     */
    @Override
    public void doMonitorTrxTransferByMonitorAddressInfo(MonitorAddressAccount monitorAddressAccount) {

        String monitorAddress = monitorAddressAccount.getMonitorAddress();
        String apiKey = monitorAddressAccount.getApiKey();

        ResponseEntity responseEntity = null;
        try {
            responseEntity = getTransActionList(monitorAddress, apiKey);
        } catch (Exception e) {
            log.error("获取交易列表异常", e);
            ErrorLog errorLog = ErrorLog.builder()
                    .address(monitorAddressAccount.getMonitorAddress())
                    .errorCode("getTransAction")
                    .errorMsg(e.getMessage())
                    .fcu("system")
                    .lcu("system").build();

            errorLogMapper.insertErrorLog(errorLog);

            throw new RuntimeException("获取交易列表异常", e);
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
                log.error("业务处理异常{},txid:{}", monitorAddress, txID, e);
                ErrorLog errorLog = ErrorLog.builder()
                        .address(monitorAddressAccount.getMonitorAddress())
                        .trxId(txID)
                        .errorCode("doDelegateResource")
                        .errorMsg(e.getMessage())
                        .fcu("system")
                        .lcu("system").build();

                errorLogMapper.insertErrorLog(errorLog);
                throw new RuntimeException("doDelegateResource业务处理异常", e);
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
     * 进行资源代理操作
     *
     * @param contract
     * @param txID
     * @param monitorAddressAccount
     * @throws IllegalException
     */
    private void doDelegateResource(Contract contract, String txID, MonitorAddressAccount monitorAddressAccount) throws Exception {


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
        Integer price = null;
        String busiType = DictUtils.getDictValue("sys_busi_type", "闪兑套餐");

        //查询是否是按天支付的租户
        TenantInfo tenantInfoExample = new TenantInfo();
        tenantInfoExample.setReceiverAddress(ownerAddress);
//        tenantInfoExample.setMonitorAddress(monitorAddressAccount.getMonitorAddress());
        tenantInfoExample.setFinishTransferTime(new Date());
        tenantInfoExample.setIsPaid(UserConstants.NO);
        List<TenantInfo> tenantInfoList = tenantInfoMapper.selectTenantInfoList(tenantInfoExample);

        if (tenantInfoList.size() > 0) {
            TenantInfo tenantInfo = tenantInfoList.get(0);
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
                errorLogMapper.insertErrorLog(errorLog);
                return;
            }
            tenantInfo.setIsPaid("Y");
            tenantInfo.setLcu("system");
            tenantInfo.setTxId(txID);
            tenantInfoMapper.updateTenantInfo(tenantInfo);
            //取包天的套餐锁定时间和交易笔数
            lockPeriod = 1200L * 24;
            busiType = DictUtils.getDictValue("sys_busi_type", "天数套餐");
            transferCount = tenantInfo.getTransferCount();
            price = tenantInfo.getPrice().intValue();

        } else {
            price = monitorAddressAccount.getPrice();
            transferCount = amount / price;

            if (transferCount < 1) {
                log.info("txID:{}金额{}不是单价的倍数不做处理", txID, amountSun);
                return;
            }

        }

        String accountAddress = monitorAddressAccount.getAccountAddress();
        String encryptPrivateKey = monitorAddressAccount.getEncryptPrivateKey();
        String encryptKey = monitorAddressAccount.getEncryptKey();
        String apiKey = monitorAddressAccount.getApiKey();

        String decryptPrivateKey = Dt.decrypt(encryptPrivateKey, encryptKey);
        ApiWrapper apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey, apiKey);

        calcBalanceAndDelegate(txID, apiWrapper, accountAddress, transferCount, ownerAddress, lockPeriod, toAddress, price, busiType, amount, "system");
        //持久化之后放redis
        redisTemplate.opsForValue().set("transfer_trx_" + txID, txID, 1, TimeUnit.DAYS);
    }

    /**
     * @param txID           转账交易订单号
     * @param apiWrapper     apiWrapper
     * @param accountAddress 出账地址
     * @param transferCount  转账笔数
     * @param ownerAddress   转入地址
     * @param lockPeriod     锁定周期
     * @param toAddress      监听地址
     * @param price          单价
     * @param busiType       业务类型
     * @param amount         转入金额
     * @param currentUser    当前处理人
     * @throws Exception 异常
     */
    private void calcBalanceAndDelegate(String txID,
                                        ApiWrapper apiWrapper,
                                        String accountAddress,
                                        long transferCount,
                                        String ownerAddress,
                                        long lockPeriod,
                                        String toAddress,
                                        Integer price,
                                        String busiType,
                                        Long amount,
                                        String currentUser) throws Exception {


        Response.AccountResourceMessage accountResource = apiWrapper.getAccountResource(accountAddress);

        //总用于质押换取能量的trx上限
        long balance = getBalance(accountResource, transferCount);

        /*  lock_period: 锁定周期，以区块时间（3s）为单位，表示锁定多少个区块的时间，当lock为true时，该字段有效。如果代理锁定期为1天，则lock_period为：28800*/

        String delegateResourceTxid = getDelegateResourceTxid(apiWrapper, accountAddress, balance, ownerAddress, lockPeriod);

        TrxExchangeInfo trxExchangeInfo = TrxExchangeInfo.builder()
                .fromAddress(AddressUtil.hexToBase58(ownerAddress))
                .toAddress(AddressUtil.hexToBase58(toAddress))
                .accountAddress(accountAddress)
                .price(Long.valueOf(price))
                .trxTxId(txID)
                .tranferCount(transferCount)
                .busiType(busiType)
                .trxAmount(amount)
                .delegateAmountTrx(balance)
                .delegateTxId(delegateResourceTxid)
                .lockPeriod(lockPeriod)
                .delegateStatus("0")
                .fcd(new Date())
                .fcu(currentUser)
                .lcd(new Date())
                .fcu(currentUser)
                .build();
        trxExchangeInfoMapper.insertTrxExchangeInfo(trxExchangeInfo);
    }

    private static long getBalance(Response.AccountResourceMessage accountResource, long energyNum) {
        long totalEnergyLimit = accountResource.getTotalEnergyLimit();
        //已经用于质押换取能量的trx
        long totalEnergyWeight = accountResource.getTotalEnergyWeight();

        //系数 保留一位或者保留2位,算出trx往上进一位
        BigDecimal trxToFreezeEnergyTimes = BigDecimal.valueOf(totalEnergyLimit).divide(BigDecimal.valueOf(totalEnergyWeight), 2, BigDecimal.ROUND_DOWN);
        //计算代理的数量
//        long balance = energyNum * 32000 / 12;
        long balance = BigDecimal.valueOf(energyNum * 32000).divide(trxToFreezeEnergyTimes, 0, BigDecimal.ROUND_UP).longValue();
        return balance;
    }

    private static String getDelegateResourceTxid(ApiWrapper apiWrapper, String accountAddress, long balance, String ownerAddress, long lockPeriod) throws IllegalException {
        Response.TransactionExtention transactionExtention = apiWrapper.delegateResourceV2(accountAddress, balance * 1000000, Common.ResourceCode.ENERGY_VALUE, ownerAddress, true, lockPeriod);

        Chain.Transaction transaction = apiWrapper.signTransaction(transactionExtention);

        String delegateResourceTxid = apiWrapper.broadcastTransaction(transaction);
        return delegateResourceTxid;
    }


    @Override
    public void doUndelegateEnergyByTrxExchangeInfo(TrxExchangeInfo trxExchangeInfo) {
        Long lockPeriod = trxExchangeInfo.getLockPeriod();
        Date fcd = trxExchangeInfo.getFcd();
        Date now = new Date();
        long betweenSeconds = DateUtil.between(fcd, now, DateUnit.SECOND);

        if (betweenSeconds <= lockPeriod * 3) {
            //不解锁时间不处理
            return;
        }


        String accountAddress = trxExchangeInfo.getAccountAddress();
        RLock lock = redissonClient.getLock("lock_undelegate_" + trxExchangeInfo.getDelegateTxId());
        try {
            String decryptPrivateKey = accountAddressInfoService.getDecryptPrivateKey(accountAddress);
            boolean res = lock.tryLock(3, 50, TimeUnit.SECONDS);
            if (!res) {
                //有其他程序正在处理则不需要再处理
                return;
            }
            String tronApiKey = DictUtils.getDictValue("sys_tron_api_key", "synp@outlook");
            ApiWrapper apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey, tronApiKey);

            //回收能量
            doUndelegateEnergy(trxExchangeInfo, apiWrapper);

            String busiType = DictUtils.getDictValue("sys_busi_type", "天数套餐");
            String trxExchangeInfoBusiType = trxExchangeInfo.getBusiType();
            if (!busiType.equals(trxExchangeInfoBusiType)) {
                return;
            }

            //查询是否是按天支付的租户,是的话需要回收完再次赠送
            TenantInfo tenantInfoExample = new TenantInfo();
            tenantInfoExample.setReceiverAddress(trxExchangeInfo.getFromAddress());
//            tenantInfoExample.setMonitorAddress(trxExchangeInfo.getToAddress());
            tenantInfoExample.setFinishTransferTime(new Date());
            List<TenantInfo> tenantInfoList = tenantInfoMapper.selectTenantInfoList(tenantInfoExample);

            if (tenantInfoList.size() > 0) {
                Response.AccountResourceMessage accountResource = apiWrapper.getAccountResource(accountAddress);
                TenantInfo tenantInfo = tenantInfoList.get(0);

                if (UserConstants.NO.equals(tenantInfo.getIsPaid())) {
                    return;
                }

                Long period = tenantInfo.getPeriod();
                Date tenantInfoFcd = tenantInfo.getFcd();
                long between = DateUtil.between(tenantInfoFcd, new Date(), DateUnit.DAY);
                if (between > period) {
                    //已到期不处理
                    return;
                }

                String monitorAddress = tenantInfo.getMonitorAddress();
                String toAddress = trxExchangeInfo.getToAddress();
                if (StringUtils.isNotEmpty(toAddress) && !toAddress.equals(monitorAddress)) {
                    return;
                }

                String receiverAddress = tenantInfo.getReceiverAddress();
                Long transferCount = tenantInfo.getTransferCount();
                long newLockPeriod = 24 * 1200L;

                calcBalanceAndDelegate(null,
                        apiWrapper,
                        accountAddress,
                        transferCount,
                        receiverAddress,
                        newLockPeriod,
                        monitorAddress,
                        tenantInfo.getPrice() == null ? null : tenantInfo.getPrice().intValue(),
                        busiType,
                        null,
                        "system");
                /*    long balance = getBalance(accountResource, transferCount);

                 *//*  lock_period: 锁定周期，以区块时间（3s）为单位，表示锁定多少个区块的时间，当lock为true时，该字段有效。如果代理锁定期为1天，则lock_period为：28800*//*


                String delegateResourceTxid = getDelegateResourceTxid(apiWrapper, accountAddress, balance, receiverAddress, newLockPeriod);

                TrxExchangeInfo trxExchangeInfoNew = TrxExchangeInfo.builder()
                        .fromAddress(receiverAddress)
                        .toAddress(accountAddress)
                        .accountAddress(accountAddress)
                        .price(tenantInfo.getPrice())
                        .trxTxId(tenantInfo.getTxId())
                        .tranferCount(transferCount)
                        .busiType(DictUtils.getDictValue("sys_busi_type", "天数套餐"))
                        .trxAmount(null)
                        .delegateAmountTrx(balance)
                        .delegateTxId(delegateResourceTxid)
                        .lockPeriod(newLockPeriod)
                        .delegateStatus("0")
                        .fcd(new Date())
                        .fcu("system")
                        .lcd(new Date())
                        .fcu("system")
                        .build();

                trxExchangeInfoMapper.insertTrxExchangeInfo(trxExchangeInfoNew);*/
            }

        } catch (Exception e) {
            log.error("回收能量业务处理异常{}", trxExchangeInfo.getIdTrxExchangeInfo(), e);
            ErrorLog errorLog = ErrorLog.builder()
                    .address(accountAddress)
                    .errorCode("UndelegateEnergy")
                    .errorMsg(e.getMessage())
                    .trxId(trxExchangeInfo.getDelegateTxId())
                    .otherId(trxExchangeInfo.getIdTrxExchangeInfo().toString())
                    .fcu("system")
                    .lcu("system").build();
            errorLogMapper.insertErrorLog(errorLog);
            throw new RuntimeException("回收能量业务处理异常", e);
        } finally {
            if (lock.isLocked()) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }


    }


    /**
     * 回收业务逻辑
     *
     * @param trxExchangeInfo
     * @param apiWrapper
     * @throws IllegalException
     */
    private void doUndelegateEnergy(TrxExchangeInfo trxExchangeInfo, ApiWrapper apiWrapper) throws IllegalException {
        String accountAddress = trxExchangeInfo.getAccountAddress();
        String fromAddress = trxExchangeInfo.getFromAddress();
        long balance = trxExchangeInfo.getDelegateAmountTrx() * 1000000;

        Response.TransactionExtention transactionExtention = apiWrapper.undelegateResource(accountAddress,
                balance, Common.ResourceCode.ENERGY_VALUE, fromAddress);

        Chain.Transaction transaction = apiWrapper.signTransaction(transactionExtention);

        String unDelegateTxId = apiWrapper.broadcastTransaction(transaction);

        String dictValue = DictUtils.getDictValue("sys_delegate_status", "已回收");

        trxExchangeInfo.setDelegateStatus(dictValue);
        trxExchangeInfo.setUnDelegateTxId(unDelegateTxId);
        trxExchangeInfo.setLcd(new Date());
        trxExchangeInfo.setLcu("system");
        trxExchangeInfoMapper.updateTrxExchangeInfo(trxExchangeInfo);
    }

}
