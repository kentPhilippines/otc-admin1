package com.ruoyi.quartz.task;


import com.ruoyi.common.core.domain.entity.TenantInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.system.handler.EnergyTenantTransferHandler;
import com.ruoyi.system.service.ITenantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("energyTenantTransfer")
public class EnergyTenantTransferTask {
    @Autowired
    private ITenantInfoService tenantInfoService;

    @Autowired
    private EnergyTenantTransferHandler energyTenantTransferHandler;

    public void doEnergyTenantTransfer(){
        TenantInfo tenantInfoExample = new TenantInfo();
        tenantInfoExample.setIsPaid("Y");
        tenantInfoExample.setStatus(DictUtils.getDictValue("sys_tenant_status", "生效中"));
        List<TenantInfo> tenantInfoList = tenantInfoService.selectTenantInfoList(tenantInfoExample);

        for (TenantInfo tenantInfo : tenantInfoList) {
            energyTenantTransferHandler.doEnergyTenantTransfer(tenantInfo);
        }
    }
}
