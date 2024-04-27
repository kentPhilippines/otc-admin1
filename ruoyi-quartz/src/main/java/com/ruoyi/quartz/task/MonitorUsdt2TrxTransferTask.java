package com.ruoyi.quartz.task;

import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.system.domain.MonitorAddressAccount;
import com.ruoyi.system.handler.Usdt2TrxTransferHandler;
import com.ruoyi.system.service.IMonitorAddressInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("monitorUsdt2TrxTransferTask")
@Slf4j
public class MonitorUsdt2TrxTransferTask {

    @Autowired
    private IMonitorAddressInfoService iMonitorAddressInfoService;

    @Autowired
    private Usdt2TrxTransferHandler usdt2TrxTransferHandler;

    public void doMonitorUsdtTransferTask() throws Exception {

        MonitorAddressInfo monitorAddressInfoExample = new MonitorAddressInfo();
        monitorAddressInfoExample
                .setIsValid("Y")
                .setBusiType(DictUtils.getDictValue("sys_busi_type", "USDT兑TRX"));

        List<MonitorAddressAccount> monitorAddressAccounts = iMonitorAddressInfoService.selectMonitorAddressAccount(monitorAddressInfoExample);

        for (MonitorAddressAccount monitorAddressAccount : monitorAddressAccounts) {

            usdt2TrxTransferHandler.doMonitorUsdtTransfer(monitorAddressAccount);
        }
    }
}
