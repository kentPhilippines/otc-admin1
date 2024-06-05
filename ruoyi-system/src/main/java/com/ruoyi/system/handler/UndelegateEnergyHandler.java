package com.ruoyi.system.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.common.core.domain.entity.TenantInfo;
import com.ruoyi.common.core.domain.entity.TrxExchangeInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.encrpt.Dt;
import com.ruoyi.system.api.ITronApi;
import com.ruoyi.system.domain.TrxExchangeMonitorAccountInfo;
import com.ruoyi.system.dto.AccountResourceResponse;
import com.ruoyi.system.dto.Data;
import com.ruoyi.system.dto.TronGridResponse;
import com.ruoyi.system.mapper.TenantInfoMapper;
import com.ruoyi.system.mapper.TrxExchangeInfoMapper;
import com.ruoyi.system.service.IErrorLogService;
import com.ruoyi.system.service.ISysConfigService;
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
    private TrxExchangeInfoMapper trxExchangeInfoMapper;
    @Autowired
    private ITronApi tronApi;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private IErrorLogService errorLogService;


    public void doUndelegateEnergyByTrxExchangeInfo(TrxExchangeMonitorAccountInfo trxExchangeMonitorAccountInfo) {
        Long lockPeriod = trxExchangeMonitorAccountInfo.getLockPeriod();
        Date fcd = trxExchangeMonitorAccountInfo.getFcd();
        Date now = new Date();
        long betweenHours = DateUtil.between(fcd, now, DateUnit.HOUR);

        String fromAddress = trxExchangeMonitorAccountInfo.getFromAddress();


        if (betweenHours < lockPeriod) {
            String sysEnergyUndelegateWhitelistString = configService.selectConfigByKey("sys.energy.undelegate.whitelist");
           if (StringUtils.isNotEmpty(sysEnergyUndelegateWhitelistString) && sysEnergyUndelegateWhitelistString.contains(trxExchangeMonitorAccountInfo.getFromAddress())){
               String sysEnergyUndelegatePeriod = configService.selectConfigByKey("sys.energy.undelegate.period");
               if (StringUtils.isNotEmpty(sysEnergyUndelegatePeriod)){
                   long betweenMinutes = DateUtil.between(fcd, now, DateUnit.MINUTE);
                   Long betweenMinutesLong = new Long(sysEnergyUndelegatePeriod);
                   if (betweenMinutes < betweenMinutesLong){
                       return;
                   }
               }
           }
            //定时任务判断对方转账笔数
//            String apiKey = trxExchangeMonitorAccountInfo.getApiKey();
//
//            apiKey = StringUtils.isEmpty(apiKey) ? DictUtils.getDictValue("sys_tron_api_key", "synp@outlook") : apiKey;

            String apiKey = DictUtils.getRandomDictValue("sys_tron_api_key");

            String calcRule = trxExchangeMonitorAccountInfo.getCalcRule();
            String dictValue = DictUtils.getDictValue("sys_transaction_count_calc_rule", "实际交易法");
            if (StringUtils.isNotNull(calcRule) && dictValue.equals(calcRule)) {
                TronGridResponse tronGridResponse = tronApi.getTronGridTrc20Response(fromAddress, false, true, apiKey, fcd.getTime());

                List<Data> dataList = tronGridResponse.getData();
                if (CollectionUtil.isEmpty(dataList)) {
                    return;
                }
                //只要有交易,就进行回收
              /*  int transActionCount = dataList.size();
                if (transActionCount < trxExchangeMonitorAccountInfo.getTranferCount()) {
                    return;
                }*/

                trxExchangeMonitorAccountInfo.setCountUsed(dataList.size());

            } else {

                AccountResourceResponse accountResource = tronApi.getAccountResource(fromAddress, apiKey);
                if (accountResource == null) {
                    return;
                }

                String energyBusiType = trxExchangeMonitorAccountInfo.getEnergyBusiType();
                String sysEnergyBusiType = DictUtils.getDictValue("sys_energy_busi_type", "闪兑套餐");
                String sysEnergyBusiType1 = DictUtils.getDictValue("sys_energy_busi_type", "笔数限时套餐");
                if (sysEnergyBusiType.equals(energyBusiType) || sysEnergyBusiType1.equals(energyBusiType)) {

                    String resourceCode = trxExchangeMonitorAccountInfo.getResourceCode();
                    if (resourceCode.equals(Common.ResourceCode.ENERGY.name())) {
                        Integer energyUsed = accountResource.getEnergyUsed();
                        if (energyUsed == null) {
                            return;
                        }

                        long energyUsedCount = energyUsed / 30000;
                        if (energyUsedCount < trxExchangeMonitorAccountInfo.getTranferCount()) {
                            return;
                        }
                    } else {
                        Integer netUsed = accountResource.getNetUsed();
                        if (netUsed == null) {
                            return;
                        }
                        int bandWidthUsedCount = netUsed / 250;
                        if (bandWidthUsedCount < trxExchangeMonitorAccountInfo.getTranferCount()) {
                            return;
                        }
                    }
                } else {
                    String resourceCode = trxExchangeMonitorAccountInfo.getResourceCode();
                    if (resourceCode.equals(Common.ResourceCode.ENERGY.name())) {
                        Integer energyUsed = accountResource.getEnergyUsed();
                        if (energyUsed == null) {
                            return;
                        }

                        int energyUsedCount = energyUsed / 30000;
                        if (energyUsedCount == 0) {
                            return;
                        }
                        trxExchangeMonitorAccountInfo.setCountUsed(energyUsedCount);
                    }
                }

            }
        }
        //超过时间强制回收
        unDelegateResource(trxExchangeMonitorAccountInfo);

    }

    public void unDelegateResource(TrxExchangeMonitorAccountInfo trxExchangeMonitorAccountInfo) {
        String accountAddress = trxExchangeMonitorAccountInfo.getAccountAddress();
        RLock lock = redissonClient.getLock("lock_undelegate_" + trxExchangeMonitorAccountInfo.getDelegateTxId());
        try {
//            String decryptPrivateKey = accountAddressInfoService.getDecryptPrivateKey(accountAddress);
            String encryptPrivateKey = trxExchangeMonitorAccountInfo.getEncryptPrivateKey();
            String encryptKey = trxExchangeMonitorAccountInfo.getEncryptKey();

            String decryptPrivateKey = Dt.decrypt(encryptPrivateKey, encryptKey);
            boolean res = lock.tryLock(1, 50, TimeUnit.SECONDS);
            if (!res) {
                //有其他程序正在处理则不需要再处理
                return;
            }

            //再次确认记录是否已经被回收
            TrxExchangeInfo trxExchangeInfo = trxExchangeInfoMapper.selectTrxExchangeInfoByIdTrxExchangeInfo(trxExchangeMonitorAccountInfo.getIdTrxExchangeInfo());
            String deleagteStatus = DictUtils.getDictValue("sys_delegate_status", "已回收");
            if (deleagteStatus.equals(trxExchangeInfo.getDelegateStatus())) {
                //已经处理过不再处理
                return;
            }


//            String tronApiKey = DictUtils.getDictValue("sys_tron_api_key", "synp@outlook");
            String apiKey = DictUtils.getRandomDictValue("sys_tron_api_key");
            ApiWrapper apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey, apiKey);

            //回收能量
            doUndelegateEnergy(trxExchangeMonitorAccountInfo, apiWrapper);


            Integer countUsed = trxExchangeMonitorAccountInfo.getCountUsed();

            if (countUsed == null || countUsed == 0){
                //代表不是按照回收监控或者套餐类型的订单
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

              /*  if (UserConstants.NO.equals(tenantInfo.getIsPaid())) {
                    return;
                }*/



                Long totalCountUsed = tenantInfo.getTotalCountUsed();
                long newTotalCountUsed  = totalCountUsed + countUsed;
                tenantInfo.setTotalCountUsed(newTotalCountUsed);
                Long transferCount = tenantInfo.getTransferCount();
                if (newTotalCountUsed < transferCount) {
                    //回收完再次赠送

                    trx2EneryTransferHandler.calcBalanceAndDelegate(null,
                            apiWrapper,
                            accountAddress,
                            2,
                            tenantInfo.getReceiverAddress(),
                            24,
                            null,
                            tenantInfo.getPrice(),
                            tenantInfo.getEnergyBusiType(),
                            null,
                            null,
                            "system",
                            Common.ResourceCode.ENERGY.name(), tenantInfo.getCalcRule());

                } else {
                    Long period = tenantInfo.getPeriod();
                    Long delegatedDays = tenantInfo.getDelegatedDays();
                    String sysEnergyBusiType = DictUtils.getDictValue("sys_energy_busi_type", "天数套餐");
                    String trxExchangeInfoBusiType = trxExchangeMonitorAccountInfo.getEnergyBusiType();
                    if (trxExchangeInfoBusiType.equals(sysEnergyBusiType) ) {
                       if ( delegatedDays == period){
                           //委托天数已用完不再处理,更改状态为已满期
                           String expire = DictUtils.getDictValue("sys_tenant_status", "已满期");
                           tenantInfo.setStatus(expire);
                        }
                    }else{
                        //笔数套餐超过次数直接设置为耗尽
                        String expire = DictUtils.getDictValue("sys_tenant_status", "笔数用完");
                        tenantInfo.setStatus(expire);
                    }
                    tenantInfo.setTotalCountUsed(transferCount);
                }


                tenantInfo.setLcu("system");
                tenantInfoMapper.updateTenantInfo(tenantInfo);
            }

            //查询是否是按天支付的租户,是的话需要回收完再次赠送
//
//            Integer totalCountUsed = trxExchangeMonitorAccountInfo.getTotalCountUsed();
//
//            if (totalCountUsed == null) {
//                //不是天数或者套餐不需要再继续处理
//                return;
//            }
//            if (UserConstants.NO.equals(trxExchangeMonitorAccountInfo.getIsPaid())) {
//                return;
//            }
//
//
////            if (delegatedDays == period) {
////                //委托天数已用完不再处理,更改状态为已满期
////                String expire = DictUtils.getDictValue("sys_tenant_status", "已满期");
////                tenantInfo.setStatus(expire);
////                tenantInfoMapper.updateTenantInfo(tenantInfo);
////            }
//            Integer maxTransferCount = trxExchangeMonitorAccountInfo.getMaxTransferCount();
//            TenantInfo tenantInfo = new TenantInfo();
//            tenantInfo.setIdTenantInfo(trxExchangeMonitorAccountInfo.getIdTenantInfo());
//            tenantInfo.setTotalCountUsed(trxExchangeMonitorAccountInfo.getTotalCountUsed().longValue());
//            if (totalCountUsed < maxTransferCount) {
//                //回收完再次赠送
//
//            } else {
//                Long period = trxExchangeMonitorAccountInfo.getPeriod();
//                Long delegatedDays = trxExchangeMonitorAccountInfo.getDelegatedDays();
//                String sysEnergyBusiType = DictUtils.getDictValue("sys_energy_busi_type", "天数套餐");
//                String trxExchangeInfoBusiType = trxExchangeMonitorAccountInfo.getEnergyBusiType();
//                if (trxExchangeInfoBusiType.equals(sysEnergyBusiType) ) {
//                   if ( delegatedDays == period){
//                       //委托天数已用完不再处理,更改状态为已满期
//                       String expire = DictUtils.getDictValue("sys_tenant_status", "已满期");
//                       tenantInfo.setStatus(expire);
//                    }
//                }else{
//                    //笔数套餐超过次数直接设置为耗尽
//                    String expire = DictUtils.getDictValue("sys_tenant_status", "已满期");
//                    tenantInfo.setStatus(expire);
//                }
//            }
//
//            tenantInfoMapper.updateTenantInfo(tenantInfo);

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
            errorLogService.insertErrorLog(errorLog);
//            throw new RuntimeException("回收能量业务处理异常", e);
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
                balance, Common.ResourceCode.valueOf(trxExchangeMonitorAccountInfo.getResourceCode()).getNumber(), fromAddress);

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
