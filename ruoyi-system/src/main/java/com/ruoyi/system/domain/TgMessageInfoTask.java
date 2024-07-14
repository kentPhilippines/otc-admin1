package com.ruoyi.system.domain;

import java.io.Serializable;
import java.util.Date;

public class TgMessageInfoTask implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long idTgMessageTask;

    /**
     *
     */
    private Long idTgMessageInfo;

    /**
     *
     */
    private String chatIds;

    /**
     *
     */
    private String intervalTime;

    /**
     *
     */
    private Date beginTime;

    /**
     *
     */
    private Date nextRunTime;

    /**
     *
     */
    private Integer executionStragy;

    /**
     *
     */
    private Integer status;

    /**
     *
     */
    private String messageInfo;
    private Integer messageType;


    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Long getIdTgMessageTask() {
        return idTgMessageTask;
    }

    public void setIdTgMessageTask(Long idTgMessageTask) {
        this.idTgMessageTask = idTgMessageTask;
    }

    public Long getIdTgMessageInfo() {
        return idTgMessageInfo;
    }

    public void setIdTgMessageInfo(Long idTgMessageInfo) {
        this.idTgMessageInfo = idTgMessageInfo;
    }

    public String getChatIds() {
        return chatIds;
    }

    public void setChatIds(String chatIds) {
        this.chatIds = chatIds;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getNextRunTime() {
        return nextRunTime;
    }

    public void setNextRunTime(Date nextRunTime) {
        this.nextRunTime = nextRunTime;
    }

    public Integer getExecutionStragy() {
        return executionStragy;
    }

    public void setExecutionStragy(Integer executionStragy) {
        this.executionStragy = executionStragy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }
}