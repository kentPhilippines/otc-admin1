package com.ruoyi.quartz.task;

import com.ruoyi.common.core.domain.entity.SmsTaskTbl;
import com.ruoyi.system.handler.GetSmsDetailTaskHandler;
import com.ruoyi.system.mapper.SmsTaskTblMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("getSmsDetailTask")
public class GetSmsDetailTask {
    @Autowired
    private SmsTaskTblMapper smsTaskTblMapper;

    @Autowired
    private GetSmsDetailTaskHandler getSmsDetailTaskHandler;

    public void doGetSmsDetailTask() {
        List<SmsTaskTbl> smsTaskTblList = smsTaskTblMapper.selectSmsTaskTblListNotComplete();

        getSmsDetailTaskHandler.doGetSmsDetailTask(smsTaskTblList);

    }
}
