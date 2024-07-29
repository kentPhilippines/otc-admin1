package com.ruoyi.system.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.common.core.domain.entity.TenantInfo;
import com.ruoyi.common.core.domain.entity.TrxExchangeInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.ForwardCounter;
import com.ruoyi.common.utils.LogUtils;
import com.ruoyi.common.utils.encrpt.Dt;
import com.ruoyi.system.domain.TrxExchangeMonitorAccountInfo;
import com.ruoyi.system.mapper.TenantInfoMapper;
import com.ruoyi.system.mapper.TrxExchangeInfoMapper;
import com.ruoyi.system.service.IErrorLogService;
import com.ruoyi.system.service.ITenantInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tron.trident.core.ApiWrapper;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class EnergyTenantTransferHandler {

    @Autowired
    private TrxExchangeInfoMapper trxExchangeInfoMapper;

    @Autowired
    private UndelegateEnergyHandler undelegateEnergyHandler;
    @Autowired
    private ITenantInfoService tenantInfoService;

    @Autowired
    private TenantInfoMapper tenantInfoMapper;

    @Autowired
    private IErrorLogService errorLogService;

    /**
     * 按照定制客户一个一个处理
     *
     * @param tenantInfo
     */
    public void doEnergyTenantTransfer(TenantInfo tenantInfo) {
        try {

            String eneryBusiTypeByDay = DictUtils.getDictValue("sys_energy_busi_type", "天数套餐");

            if (tenantInfo.getEnergyBusiType().equals(eneryBusiTypeByDay)) {

                Long period = tenantInfo.getPeriod();
                Long delegatedDays = tenantInfo.getDelegatedDays();
//                long between = DateUtil.between(tenantInfo.getFcd(), new Date(), DateUnit.DAY);

                TrxExchangeInfo trxExchangeInfo = buildTrxExchangeInfo(tenantInfo, eneryBusiTypeByDay);

                List<TrxExchangeMonitorAccountInfo> trxExchangeMonitorAccountInfoList = trxExchangeInfoMapper.selectTrxExchangeMonitorAccountInfo(trxExchangeInfo);

                if (delegatedDays + 1 > period) {

                    String apiKey = DictUtils.getRandomDictValue("sys_tron_api_key");

                    for (TrxExchangeMonitorAccountInfo trxExchangeMonitorAccountInfo : trxExchangeMonitorAccountInfoList) {
                        String encryptPrivateKey = trxExchangeMonitorAccountInfo.getEncryptPrivateKey();
                        String encryptKey = trxExchangeMonitorAccountInfo.getEncryptKey();
                        String decryptPrivateKey = Dt.decrypt(encryptPrivateKey, encryptKey);
                        ApiWrapper apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey, apiKey);
                        undelegateEnergyHandler.doUndelegateEnergy(trxExchangeMonitorAccountInfo,apiWrapper);
                    }

                    //满期不继续处理
//                    tenantInfo.setDelegatedDays(delegatedDays);
                    String delegateStatus = DictUtils.getDictValue("sys_delegate_status", "已满期");
                    tenantInfo.setStatus(delegateStatus);
                    tenantInfo.setLcu("system");
                    tenantInfoMapper.updateTenantInfo(tenantInfo);


                    return;
                }

           /*
                TrxExchangeInfo trxExchangeInfo = buildTrxExchangeInfo(tenantInfo, eneryBusiTypeByDay);

                List<TrxExchangeMonitorAccountInfo> trxExchangeMonitorAccountInfoList = trxExchangeInfoMapper.selectTrxExchangeMonitorAccountInfo(trxExchangeInfo);
                       for (TrxExchangeMonitorAccountInfo trxExchangeMonitorAccountInfo : trxExchangeMonitorAccountInfoList) {
                    undelegateEnergyHandler.unDelegateResource(trxExchangeMonitorAccountInfo);
                }*/

                if (CollectionUtil.isNotEmpty(trxExchangeMonitorAccountInfoList)) {
                    tenantInfo.setTotalCountUsed(0L);
                    tenantInfo.setDelegatedDays(tenantInfo.getDelegatedDays() + 1);
                    tenantInfo.setLcu("system");
                    tenantInfoMapper.updateTenantInfo(tenantInfo);
                } else {
                    //赠送每天的能量
                    tenantInfoService.doDelegateEnergy(tenantInfo, "system");
                }
            } else {
                String delegateStatus = DictUtils.getDictValue("sys_delegate_status", "已回收");
                //查看有没有没回收的能量,有的话先回收
                TrxExchangeInfo trxExchangeInfo = TrxExchangeInfo.builder()
                        .fromAddress(tenantInfo.getReceiverAddress())
                        .delegateStatus(delegateStatus)
                        .energyBusiType(eneryBusiTypeByDay)
                        .fcd(DateUtil.offsetDay(new Date(), -2))
                        .build();

                List<TrxExchangeMonitorAccountInfo> trxExchangeMonitorAccountInfoList = trxExchangeInfoMapper.selectTrxExchangeMonitorAccountInfo(trxExchangeInfo);
                if (CollectionUtil.isEmpty(trxExchangeMonitorAccountInfoList)) {
                    //一笔都没有使用,每天自动扣除一笔
                    tenantInfo.setTotalCountUsed(tenantInfo.getTotalCountUsed() + 1);
                    tenantInfo.setLcu("system");
                    tenantInfoMapper.updateTenantInfo(tenantInfo);
                }

                tenantInfoMapper.updateTenantInfo(tenantInfo);
            }

        } catch (Exception e) {
            String exceptionString = LogUtils.doRecursiveReversePrintStackCause(e, 5, ForwardCounter.builder().count(0).build(), 5);
            log.error("获取trx20交易列表异常:{}", exceptionString);
            ErrorLog errorLog = ErrorLog.builder()
                    .address(tenantInfo.getReceiverAddress())
                    .errorCode("doEnergyTenantTransfer")
                    .errorMsg(exceptionString.length() > 2000 ? exceptionString.substring(0, 2000) : exceptionString)
                    .fcu("system")
                    .lcu("system").build();
            errorLogService.insertErrorLog(errorLog);
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) {
        DateTime parse = DateUtil.parse("2024-06-20 01:59:31", "yyyy-MM-dd HH:mm:ss");
        long between = DateUtil.between(parse, new Date(), DateUnit.DAY);
        System.out.println(between);
    }



    private static TrxExchangeInfo buildTrxExchangeInfo(TenantInfo tenantInfo, String eneryBusiTypeByDay) {
        String delegateStatus = DictUtils.getDictValue("sys_delegate_status", "已委托");
        //查看有没有没回收的能量,有的话先回收

        TrxExchangeInfo trxExchangeInfo = TrxExchangeInfo.builder()
                .fromAddress(tenantInfo.getReceiverAddress())
                .delegateStatus(delegateStatus)
                .energyBusiType(eneryBusiTypeByDay)
                .fcd(DateUtil.offsetDay(new Date(), -30))
                .build();
        return trxExchangeInfo;
    }


}
