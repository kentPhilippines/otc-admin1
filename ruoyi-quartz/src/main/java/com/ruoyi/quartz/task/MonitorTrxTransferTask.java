package com.ruoyi.quartz.task;


import com.ruoyi.common.core.domain.entity.AccountAddressInfo;
import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.system.domain.MonitorAddressAccount;
import com.ruoyi.system.service.IAccountAddressInfoService;
import com.ruoyi.system.service.IMonitorAddressInfoService;
import com.ruoyi.system.service.ITrxExchangeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("monitorTrxTransferTask")
@Slf4j
public class MonitorTrxTransferTask {

     @Autowired
    private IMonitorAddressInfoService iMonitorAddressInfoService;
    @Autowired
    private ITrxExchangeInfoService trxExchangeInfoService;
    @Autowired
    private IAccountAddressInfoService accountAddressInfoService;

    public void doMonitorTrxTransfer() {
        //获取需要监听的地址列表
       /* MonitorAddressAccount monitorAddressAccountExample = MonitorAddressAccount.builder()
                .isValid("Y")
                .monitorType("TRX")
                .build();
        List<MonitorAddressAccount> monitorAddressAccountList = iMonitorAddressInfoService.selectAllMonitorAddressAccount(monitorAddressAccountExample);*/
        String trx2Energy = DictUtils.getDictValue("sys_busi_type", "TRX兑能量");
        List<MonitorAddressInfo> monitorAddressInfoList = iMonitorAddressInfoService.selectAllValidMonitorAddressAccount(trx2Energy);
        List<MonitorAddressAccount> monitorAddressAccountList = new ArrayList<>();

        for (MonitorAddressInfo monitorAddressInfo : monitorAddressInfoList) {
            AccountAddressInfo accountAddressInfoExample = new AccountAddressInfo();
            String accountAddress = monitorAddressInfo.getAccountAddress();
            accountAddressInfoExample.setAddress(accountAddress);
            List<AccountAddressInfo> accountAddressInfoList = accountAddressInfoService.selectAccountAddressInfoList(accountAddressInfoExample);
            AccountAddressInfo accountAddressInfo = accountAddressInfoList.get(0);

            MonitorAddressAccount monitorAddressAccount = MonitorAddressAccount.builder()
                    .monitorAddress(monitorAddressInfo.getMonitorAddress())
                    .accountAddress(accountAddress)
                    .price(monitorAddressInfo.getPrice().intValue())
                    .monitorType(monitorAddressInfo.getMonitorType())
                    .apiKey(monitorAddressInfo.getApiKey())
                    .encryptPrivateKey(accountAddressInfo.getEncryptPrivateKey())
                    .encryptKey(accountAddressInfo.getEncryptKey())
                    .build();
            monitorAddressAccountList.add(monitorAddressAccount);
        }


        for (MonitorAddressAccount monitorAddressAccount : monitorAddressAccountList) {
            trxExchangeInfoService.doMonitorTrxTransferByMonitorAddressInfo(monitorAddressAccount);
        }

    }


}
