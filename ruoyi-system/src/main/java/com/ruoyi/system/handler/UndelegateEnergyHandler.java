package com.ruoyi.system.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.common.core.domain.entity.TenantInfo;
import com.ruoyi.common.core.domain.entity.TrxExchangeInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.encrpt.Dt;
import com.ruoyi.system.api.ITronApi;
import com.ruoyi.system.domain.TrxExchangeMonitorAccountInfo;
import com.ruoyi.system.dto.Data;
import com.ruoyi.system.dto.TronGridResponse;
import com.ruoyi.system.mapper.ErrorLogMapper;
import com.ruoyi.system.mapper.TenantInfoMapper;
import com.ruoyi.system.mapper.TrxExchangeInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Common;
import org.tron.trident.proto.Response;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class UndelegateEnergyHandler {


    @Autowired
    private Redisson redissonClient;
    @Autowired
    private TenantInfoMapper tenantInfoMapper;
    @Autowired
    private TRX2EneryTransferHandler trx2EneryTransferHandler;
    @Autowired
    private ErrorLogMapper errorLogMapper;
    @Autowired
    private TrxExchangeInfoMapper trxExchangeInfoMapper;
    @Autowired
    private ITronApi tronApi;


    public void doUndelegateEnergyByTrxExchangeInfo(TrxExchangeMonitorAccountInfo trxExchangeMonitorAccountInfo) {
        Long lockPeriod = trxExchangeMonitorAccountInfo.getLockPeriod();
        Date fcd = trxExchangeMonitorAccountInfo.getFcd();
        Date now = new Date();
//        long betweenSeconds = DateUtil.between(fcd, now, DateUnit.SECOND);
        long betweenHours = DateUtil.between(fcd, now, DateUnit.HOUR);

        String fromAddress = trxExchangeMonitorAccountInfo.getFromAddress();


        if (betweenHours < lockPeriod){
            //超过时间强制回收

            //定时任务判断对方转账笔数
            String apiKey = trxExchangeMonitorAccountInfo.getApiKey();

            apiKey =  StringUtils.isEmpty(apiKey) ? DictUtils.getDictValue("sys_tron_api_key", "synp@outlook") : apiKey;

            TronGridResponse tronGridResponse = tronApi.getTronGridTrc20Response(fromAddress,false,true, apiKey,fcd.getTime());

            List<Data> dataList = tronGridResponse.getData();
            if (CollectionUtil.isEmpty(dataList)) {
                return;
            }

            int transActionCount =  dataList.size();
            if (transActionCount < trxExchangeMonitorAccountInfo.getTranferCount()){
                return;
            }

        }



        unDelegateResource(trxExchangeMonitorAccountInfo);

    }

    private void unDelegateResource(TrxExchangeMonitorAccountInfo trxExchangeMonitorAccountInfo) {
        String accountAddress = trxExchangeMonitorAccountInfo.getAccountAddress();
        RLock lock = redissonClient.getLock("lock_undelegate_" + trxExchangeMonitorAccountInfo.getDelegateTxId());
        try {
//            String decryptPrivateKey = accountAddressInfoService.getDecryptPrivateKey(accountAddress);
            String encryptPrivateKey = trxExchangeMonitorAccountInfo.getEncryptPrivateKey();
            String encryptKey = trxExchangeMonitorAccountInfo.getEncryptKey();

            String decryptPrivateKey = Dt.decrypt(encryptPrivateKey, encryptKey);
            boolean res = lock.tryLock(3, 50, TimeUnit.SECONDS);
            if (!res) {
                //有其他程序正在处理则不需要再处理
                return;
            }
            String tronApiKey = DictUtils.getDictValue("sys_tron_api_key", "synp@outlook");
            ApiWrapper apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey, tronApiKey);

            //回收能量
            doUndelegateEnergy(trxExchangeMonitorAccountInfo, apiWrapper);

            String sysEnergyBusiType = DictUtils.getDictValue("sys_energy_busi_type", "天数套餐");
            String trxExchangeInfoBusiType = trxExchangeMonitorAccountInfo.getEnergyBusiType();
            if (!sysEnergyBusiType.equals(trxExchangeInfoBusiType)) {
                return;
            }

            //查询是否是按天支付的租户,是的话需要回收完再次赠送
            TenantInfo tenantInfoExample = new TenantInfo();
            tenantInfoExample.setReceiverAddress(trxExchangeMonitorAccountInfo.getFromAddress());
            tenantInfoExample.setStatus(DictUtils.getDictValue("sys_tenant_status", "生效中"));
//            tenantInfoExample.setMonitorAddress(trxExchangeMonitorAccountInfo.getToAddress());
            List<TenantInfo> tenantInfoList = tenantInfoMapper.selectTenantInfoList(tenantInfoExample);
            TenantInfo tenantInfo = null;
            if (tenantInfoList.size() > 0) {

                tenantInfo = tenantInfoList.get(0);

                if (UserConstants.NO.equals(tenantInfo.getIsPaid())) {
                    return;
                }

                Long period = tenantInfo.getPeriod();
//                long between = DateUtil.between(tenantInfoFcd, new Date(), DateUnit.DAY);
                Long delegatedDays = tenantInfo.getDelegatedDays();

                if (delegatedDays == period) {
                    //委托天数已用完不再处理,更改状态为已满期
                    String expire = DictUtils.getDictValue("sys_tenant_status", "已满期");
                    tenantInfo.setStatus(expire);
                    tenantInfoMapper.updateTenantInfo(tenantInfo);
                }
/*
                String monitorAddress = tenantInfo.getMonitorAddress();
                String toAddress = trxExchangeMonitorAccountInfo.getToAddress();
                if (StringUtils.isNotEmpty(toAddress) && !toAddress.equals(monitorAddress)) {
                    return;
                }

                tenantInfo.setDelegatedDays(delegatedDays + 1);

                String receiverAddress = tenantInfo.getReceiverAddress();
                Long transferCount = tenantInfo.getTransferCount();
//                long newLockPeriod = 24 * 1200L;
                long between = DateUtil.between(DateUtil.date(), DateUtil.endOfDay(DateUtil.date()), DateUnit.HOUR);
                long newLockPeriod = between + 1;
                trx2EneryTransferHandler.calcBalanceAndDelegate(null,
                        apiWrapper,
                        accountAddress,
                        transferCount,
                        receiverAddress,
                        newLockPeriod,
                        monitorAddress,
                        tenantInfo.getPrice(),
                        sysEnergyBusiType,
                        null,
                        "system");*/

            }

   /*         if (tenantInfo != null) {

                tenantInfoMapper.updateTenantInfo(tenantInfo);
            }*/

        } catch (Exception e) {
            log.error("回收能量业务处理异常{}", trxExchangeMonitorAccountInfo.getIdTrxExchangeInfo(), e);
            ErrorLog errorLog = ErrorLog.builder()
                    .address(accountAddress)
                    .errorCode("UndelegateEnergy")
                    .errorMsg(e.getMessage())
                    .trxId(trxExchangeMonitorAccountInfo.getDelegateTxId())
                    .otherId(trxExchangeMonitorAccountInfo.getIdTrxExchangeInfo().toString())
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
     * @param trxExchangeMonitorAccountInfo
     * @param apiWrapper
     * @param
     * @throws IllegalException
     */
    private void doUndelegateEnergy(TrxExchangeMonitorAccountInfo trxExchangeMonitorAccountInfo, ApiWrapper apiWrapper) throws IllegalException {
        String accountAddress = trxExchangeMonitorAccountInfo.getAccountAddress();
        String fromAddress = trxExchangeMonitorAccountInfo.getFromAddress();

        long balance = trxExchangeMonitorAccountInfo.getDelegateAmountTrx() * 1000000;

        Response.TransactionExtention transactionExtention = apiWrapper.undelegateResource(accountAddress,
                balance, Common.ResourceCode.ENERGY_VALUE, fromAddress);

        Chain.Transaction transaction = apiWrapper.signTransaction(transactionExtention);

        String unDelegateTxId = apiWrapper.broadcastTransaction(transaction);


        String deleagteStatus = DictUtils.getDictValue("sys_delegate_status", "已回收");
        //更新回收记录
        TrxExchangeInfo trxExchangeInfo = TrxExchangeInfo.builder()
                .idTrxExchangeInfo(trxExchangeMonitorAccountInfo.getIdTrxExchangeInfo())
                .unDelegateTxId(unDelegateTxId)
                .delegateStatus(deleagteStatus)
                .lcd(new Date())
                .lcu("system")
                .build();
        trxExchangeInfoMapper.updateTrxExchangeInfo(trxExchangeInfo);
    }
}
