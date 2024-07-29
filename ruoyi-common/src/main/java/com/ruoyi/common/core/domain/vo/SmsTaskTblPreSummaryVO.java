package com.ruoyi.common.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * WS短信任务配置对象 sms_task_tbl
 *
 * @author dorion
 * @date 2024-07-04
 */
@Data
public class SmsTaskTblPreSummaryVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idSmsTask;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;



    /** 所属国家 */
    @Excel(name = "所属国家")
    private String country;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 预估金额 */
    @Excel(name = "预估金额")
    private BigDecimal preSummary;

    /** 任务开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date taskBeginTime;

    /** 任务完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date completeTime;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String fileName;

       /** 任务状态 */
    @Excel(name = "任务状态",dictType = "sys_sms_task_status")
    private String taskStatus;


    /** 总数 */
    @Excel(name = "总量")
    private Long totalCount;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
