package com.ruoyi.common.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.SmsTaskTbl;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * WS短信任务配置对象 sms_task_tbl
 *
 * @author dorion
 * @date 2024-07-04
 */
@Data
public class SmsTaskTblVO implements Serializable {


    private static final long serialVersionUID = 1L;


    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 占位符
     */
    private String placeHolder;

    /**
     * 物料切割数量
     */
    private Integer splitNumber;


    /**
     * 任务文本内容
     */
    private String context;

    /**
     * 占位填充符
     */
    private String placeHolderFill;

    /**
     * 任务开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskBeginTime;


    /**
     * 内容类型
     */
    private String smsContentType;

    /**
     * 任务状态
     */
    private String taskStatus;


    /**
     * 首行手机号,用来判断国家
     */
    private String phoneNumber;

    /**
     * 物料名称
     */
    private String fileName;


    private List<SmsTaskTbl> smsTaskTblList;


    private long lineCount ;

}
