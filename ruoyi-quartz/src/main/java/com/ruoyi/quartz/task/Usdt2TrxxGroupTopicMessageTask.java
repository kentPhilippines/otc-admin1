package com.ruoyi.quartz.task;

import com.ruoyi.system.handler.Usdt2TrxTransferHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("usdt2TrxxGroupTopicMessageTask")
public class Usdt2TrxxGroupTopicMessageTask {
    @Autowired
    private Usdt2TrxTransferHandler usdt2TrxTransferHandler;

    public void doUsdt2TrxxGroupTopicMessageTask(){


        usdt2TrxTransferHandler.topicGroupMessage();
    }
}
