package com.ruoyi.system.api.entity.U02cx;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TaskDetailResponse implements Serializable {

    private String userName;
    private String createTime;
    private long taskId;
    private String taskName;
    private double price;
    private BigDecimal preSummary;
    private int issueCount;
    private String taskStatus;
    private String successRate;
    private String taskBeginTime;
    private String completeTime;
    private String screenFlag;


}
