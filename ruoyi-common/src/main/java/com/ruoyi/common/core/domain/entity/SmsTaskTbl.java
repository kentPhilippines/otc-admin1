package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * WS短信任务配置对象 sms_task_tbl
 *
 * @author dorion
 * @date 2024-06-27
 */
public class SmsTaskTbl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idSmsTask;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 短信类型 */
    @Excel(name = "短信类型")
    private String smsBusiType;

    /** 接口返回的任务id */
    @Excel(name = "接口返回的任务id")
    private Long taskId;

    /** 渠道id */
    @Excel(name = "渠道id")
    private String channelId;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 任务开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskBeginTime;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String fileName;

    /** 物料文件下载地址 */
    @Excel(name = "物料文件下载地址")
    private String filePath;

    /** 物料文件MD5值 */
    @Excel(name = "物料文件MD5值")
    private String fileMd5;

    /** 任务文本内容base64加密字符串 */
    @Excel(name = "任务文本内容base64加密字符串")
    private String context;

    /** 内容类型 */
    @Excel(name = "内容类型")
    private String smsContentType;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private String taskStatus;

    /** 成功数 */
    @Excel(name = "成功数")
    private String successRate;

    /** 物料总数 */
    @Excel(name = "物料总数")
    private Long issueCount;

    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    public void setIdSmsTask(Long idSmsTask)
    {
        this.idSmsTask = idSmsTask;
    }

    public Long getIdSmsTask()
    {
        return idSmsTask;
    }
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getTaskName()
    {
        return taskName;
    }
    public void setSmsBusiType(String smsBusiType)
    {
        this.smsBusiType = smsBusiType;
    }

    public String getSmsBusiType()
    {
        return smsBusiType;
    }
    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public Long getTaskId()
    {
        return taskId;
    }
    public void setChannelId(String channelId)
    {
        this.channelId = channelId;
    }

    public String getChannelId()
    {
        return channelId;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setTaskBeginTime(Date taskBeginTime)
    {
        this.taskBeginTime = taskBeginTime;
    }

    public Date getTaskBeginTime()
    {
        return taskBeginTime;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return fileName;
    }
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFilePath()
    {
        return filePath;
    }
    public void setFileMd5(String fileMd5)
    {
        this.fileMd5 = fileMd5;
    }

    public String getFileMd5()
    {
        return fileMd5;
    }
    public void setContext(String context)
    {
        this.context = context;
    }

    public String getContext()
    {
        return context;
    }
    public void setSmsContentType(String smsContentType)
    {
        this.smsContentType = smsContentType;
    }

    public String getSmsContentType()
    {
        return smsContentType;
    }
    public void setTaskStatus(String taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus()
    {
        return taskStatus;
    }
    public void setSuccessRate(String successRate)
    {
        this.successRate = successRate;
    }

    public String getSuccessRate()
    {
        return successRate;
    }
    public void setIssueCount(Long issueCount)
    {
        this.issueCount = issueCount;
    }

    public Long getIssueCount()
    {
        return issueCount;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("idSmsTask", getIdSmsTask())
                .append("taskName", getTaskName())
                .append("smsBusiType", getSmsBusiType())
                .append("taskId", getTaskId())
                .append("channelId", getChannelId())
                .append("price", getPrice())
                .append("taskBeginTime", getTaskBeginTime())
                .append("fileName", getFileName())
                .append("filePath", getFilePath())
                .append("fileMd5", getFileMd5())
                .append("context", getContext())
                .append("smsContentType", getSmsContentType())
                .append("taskStatus", getTaskStatus())
                .append("successRate", getSuccessRate())
                .append("issueCount", getIssueCount())
                .append("userId", getUserId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
