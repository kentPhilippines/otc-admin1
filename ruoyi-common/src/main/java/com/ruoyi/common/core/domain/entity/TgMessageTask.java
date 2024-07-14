package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * TG消息任务管理对象 tg_message_task
 * 
 * @author dorion
 * @date 2024-07-14
 */
public class TgMessageTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idTgMessageTask;

    /** 消息信息 */
    @Excel(name = "消息信息")
    private Long idTgMessageInfo;

    /** tg会话id */
    @Excel(name = "tg会话id")
    private String chatIds;

    /** 执行周期 */
    @Excel(name = "执行周期")
    private String intervalTime;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /** 下次执行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下次执行时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date nextRunTime;

    /** 执行策略 */
    @Excel(name = "执行策略")
    private Long executionStragy;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private Long status;

    public void setIdTgMessageTask(Long idTgMessageTask) 
    {
        this.idTgMessageTask = idTgMessageTask;
    }

    public Long getIdTgMessageTask() 
    {
        return idTgMessageTask;
    }
    public void setIdTgMessageInfo(Long idTgMessageInfo) 
    {
        this.idTgMessageInfo = idTgMessageInfo;
    }

    public Long getIdTgMessageInfo() 
    {
        return idTgMessageInfo;
    }
    public void setChatIds(String chatIds) 
    {
        this.chatIds = chatIds;
    }

    public String getChatIds() 
    {
        return chatIds;
    }
    public void setIntervalTime(String intervalTime) 
    {
        this.intervalTime = intervalTime;
    }

    public String getIntervalTime() 
    {
        return intervalTime;
    }
    public void setBeginTime(Date beginTime) 
    {
        this.beginTime = beginTime;
    }

    public Date getBeginTime() 
    {
        return beginTime;
    }
    public void setNextRunTime(Date nextRunTime) 
    {
        this.nextRunTime = nextRunTime;
    }

    public Date getNextRunTime() 
    {
        return nextRunTime;
    }
    public void setExecutionStragy(Long executionStragy) 
    {
        this.executionStragy = executionStragy;
    }

    public Long getExecutionStragy() 
    {
        return executionStragy;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("idTgMessageTask", getIdTgMessageTask())
            .append("idTgMessageInfo", getIdTgMessageInfo())
            .append("chatIds", getChatIds())
            .append("intervalTime", getIntervalTime())
            .append("beginTime", getBeginTime())
            .append("nextRunTime", getNextRunTime())
            .append("executionStragy", getExecutionStragy())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
