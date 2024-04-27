package com.ruoyi.quartz.task;


import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.system.domain.MonitorAddressAccount;
import com.ruoyi.system.handler.TRX2EneryTransferHandler;
import com.ruoyi.system.service.IMonitorAddressInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("monitorTrxTransferTask")
@Slf4j
public class MonitorTrxTransferTask {

     @Autowired
    private IMonitorAddressInfoService iMonitorAddressInfoService;

    @Autowired
    private TRX2EneryTransferHandler trx2EneryTransferHandler;


    public void doMonitorTrxTransfer() {

        MonitorAddressInfo monitorAddressInfoExample = new MonitorAddressInfo();
        monitorAddressInfoExample
                .setIsValid("Y")
                .setBusiType(DictUtils.getDictValue("sys_busi_type", "TRX兑能量"));
        List<MonitorAddressAccount> monitorAddressAccountList = iMonitorAddressInfoService.selectMonitorAddressAccount(monitorAddressInfoExample);
        log.debug("monitorAddressAccounts:{}",monitorAddressAccountList);

        for (MonitorAddressAccount monitorAddressAccount : monitorAddressAccountList) {
            Integer trxPrice = monitorAddressAccount.getTrxPrice();
            if (trxPrice == null){
                //没有设置trx价格不参与监控
                continue;
            }
            trx2EneryTransferHandler.doMonitorTrxTransferByMonitorAddressInfo(monitorAddressAccount);
        }

    }


}
