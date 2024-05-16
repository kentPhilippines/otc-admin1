package com.ruoyi.quartz.task;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.quartz.domain.SysJob;
import com.ruoyi.quartz.domain.SysJobLog;
import com.ruoyi.quartz.service.ISysJobLogService;
import com.ruoyi.quartz.service.ISysJobService;
import com.ruoyi.system.service.IErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component(value = "monitorTimerTaskStatusTask")
public class MonitorTimerTaskStatusTask {

    @Autowired
    private ISysJobService sysJobService;
    @Autowired
    private ISysJobLogService sysJobLogService;
    @Autowired
    private IErrorLogService errorLogService;

    public void doMonitorTimerTaskStatus() {


        SysJob sysJobExample = new SysJob();
        List<SysJob> sysJobs = sysJobService.selectJobList(sysJobExample);

        for (SysJob sysJob : sysJobs) {
            //0=正常,1=暂停
            if ("1".equals(sysJob.getStatus())) {
                continue;
            }
            if (104 == sysJob.getJobId() || 103 == sysJob.getJobId() || 106 == sysJob.getJobId()) {
                continue;
            }


            SysJobLog sysJobLogExample = new SysJobLog();
            sysJobLogExample.setJobName(sysJob.getJobName());
            sysJobLogExample.setCreateTime(DateUtil.offsetMinute(new Date(), -5));
            Integer count = sysJobLogService.countJobLog(sysJobLogExample);

            if (count == 0) {
                ErrorLog errorLog = ErrorLog.builder()
                        .address(sysJob.getJobName())
                        .errorCode("定时任务调度异常")
                        .errorMsg("定时任务调度异常")
                        .fcu("system")
                        .lcu("system").build();

                errorLogService.insertErrorLog(errorLog);
            }

        }

    }

}
