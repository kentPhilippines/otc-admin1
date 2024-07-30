package com.ruoyi.quartz.task;


import com.ruoyi.common.core.domain.entity.TrxExchangeInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.system.domain.TrxExchangeMonitorAccountInfo;
import com.ruoyi.system.handler.UndelegateEnergyHandler;
import com.ruoyi.system.service.ITenantInfoService;
import com.ruoyi.system.service.ITrxExchangeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("undelegateEnergyTask")
@Slf4j
public class UndelegateEnergyTask {

    @Autowired
    private ITrxExchangeInfoService trxExchangeInfoService;

    @Autowired
    private UndelegateEnergyHandler undelegateEnergyHandler;

    @Autowired
    private ITenantInfoService tenantInfoService;

    public void doUndelegateEnergy() {



        String dictValue = DictUtils.getDictValue("sys_delegate_status", "已委托");

        TrxExchangeInfo trxExchangeInfoExample = TrxExchangeInfo.builder()
                                                        .delegateStatus(dictValue)
//                                                        .fcd(DateUtil.offsetDay(new Date(),-2))
                                                        .build();

        List<TrxExchangeMonitorAccountInfo> trxExchangeMonitorAccountInfoList = trxExchangeInfoService.selectTrxExchangeMonitorAccountInfo(trxExchangeInfoExample);

        for (TrxExchangeMonitorAccountInfo trxExchangeMonitorAccountInfo : trxExchangeMonitorAccountInfoList) {
            undelegateEnergyHandler.doUndelegateEnergyByTrxExchangeInfo(trxExchangeMonitorAccountInfo);
        }


    }
}