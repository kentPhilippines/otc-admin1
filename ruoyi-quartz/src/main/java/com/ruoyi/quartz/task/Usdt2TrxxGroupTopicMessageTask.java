package com.ruoyi.quartz.task;

import com.ruoyi.system.handler.UsdtTransferHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("usdt2TrxxGroupTopicMessageTask")
public class Usdt2TrxxGroupTopicMessageTask {
    @Autowired
    private UsdtTransferHandler usdtTransferHandler;

    public void doUsdt2TrxxGroupTopicMessageTask(){


        usdtTransferHandler.topicGroupMessage();
    }
}
