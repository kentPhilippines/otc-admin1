package com.ruoyi.quartz.task;

import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.system.handler.UsdtTransferHandler;
import com.ruoyi.system.service.IMonitorAddressInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("monitorUsdtTransferTask")
@Slf4j
public class MonitorUsdtTransferTask {

    @Autowired
    private IMonitorAddressInfoService iMonitorAddressInfoService;

    @Autowired
    private UsdtTransferHandler usdtTransferHandler;

    public void doMonitorUsdtTransferTask() throws Exception {
        String usdt2Trx = DictUtils.getDictValue("sys_busi_type", "USDT兑TRX");
        List<MonitorAddressInfo> monitorAddressInfoList =
                iMonitorAddressInfoService.selectAllValidMonitorAddressAccount(usdt2Trx);

        for (MonitorAddressInfo monitorAddressInfo : monitorAddressInfoList) {

            usdtTransferHandler.doMonitorUsdtTransfer(monitorAddressInfo);
        }


    }
}
