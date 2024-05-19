package com.ruoyi.quartz.task;


import com.ruoyi.common.core.domain.entity.TenantInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.system.handler.EnergyTenantTransferHandler;
import com.ruoyi.system.mapper.TenantInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("energyTenantTransferTask")
public class EnergyTenantTransferTask {
    @Autowired
    private TenantInfoMapper tenantInfoMapper;

    @Autowired
    private EnergyTenantTransferHandler energyTenantTransferHandler;

    public void doEnergyTenantTransfer(){
        TenantInfo tenantInfoExample = new TenantInfo();
        tenantInfoExample.setStatus(DictUtils.getDictValue("sys_tenant_status", "生效中"));
    /*    String energyBusiType = DictUtils.getDictValue("sys_energy_busi_type", "天数套餐");
        tenantInfoExample.setEnergyBusiType(energyBusiType);*/
        List<TenantInfo> tenantInfoList = tenantInfoMapper.selectTenantInfoList(tenantInfoExample);

        for (TenantInfo tenantInfo : tenantInfoList) {
            String isPaid = tenantInfo.getIsPaid();
            if ("N".equals(isPaid)){
                continue;
            }
            energyTenantTransferHandler.doEnergyTenantTransfer(tenantInfo);
        }
    }
}
