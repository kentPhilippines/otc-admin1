package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * TG消息管理对象 tg_message_info
 *
 * @author dorion
 * @date 2024-07-12
 */
public class TgMessageInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idTgMessageInfo;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String messageInfo;

    /** 消息类型 */
    @Excel(name = "消息类型")
    private Long messageType;

    /** tg会话id */
    @Excel(name = "tg会话id")
    private String chatId;

    /** 执行周期 */
    @Excel(name = "执行周期")
    private String intervalTime;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginTime;

    /** 下次执行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下次执行时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nextRunTime;

    /** 执行策略 */
    @Excel(name = "执行策略")
    private Long executionStragy;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private Long status;

    public void setIdTgMessageInfo(Long idTgMessageInfo)
    {
        this.idTgMessageInfo = idTgMessageInfo;
    }

    public Long getIdTgMessageInfo()
    {
        return idTgMessageInfo;
    }
    public void setMessageInfo(String messageInfo)
    {
        this.messageInfo = messageInfo;
    }

    public String getMessageInfo()
    {
        return messageInfo;
    }
    public void setMessageType(Long messageType)
    {
        this.messageType = messageType;
    }

    public Long getMessageType()
    {
        return messageType;
    }
    public void setChatId(String chatId)
    {
        this.chatId = chatId;
    }

    public String getChatId()
    {
        return chatId;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
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
                .append("idTgMessageInfo", getIdTgMessageInfo())
                .append("messageInfo", getMessageInfo())
                .append("messageType", getMessageType())
                .append("chatId", getChatId())
                .append("interval", getIntervalTime())
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
