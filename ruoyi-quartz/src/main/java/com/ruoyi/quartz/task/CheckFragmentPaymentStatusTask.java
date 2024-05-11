package com.ruoyi.quartz.task;

import com.ruoyi.common.core.domain.entity.TgPremiumOrderInfo;
import com.ruoyi.system.handler.TgPremiumTransferHandler;
import com.ruoyi.system.mapper.TgPremiumOrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("checkFragmentPaymentStatusTask")
public class CheckFragmentPaymentStatusTask {

    @Autowired
    private TgPremiumOrderInfoMapper tgPremiumOrderInfoMapper;

    @Autowired
    private TgPremiumTransferHandler tgPremiumTransferHandler;

    public void doCheckFragmentPaymentStatus() {

        TgPremiumOrderInfo tgPremiumOrderInfoExample = new TgPremiumOrderInfo();
        tgPremiumOrderInfoExample.setTgPaymentStatus("P");
        List<TgPremiumOrderInfo> tgPremiumOrderInfos = tgPremiumOrderInfoMapper.selectTgPremiumOrderInfoList(tgPremiumOrderInfoExample);

        for (TgPremiumOrderInfo tgPremiumOrderInfo : tgPremiumOrderInfos) {

            tgPremiumTransferHandler.checkFragmentPaymentStatus(tgPremiumOrderInfo);

        }
    }

}
