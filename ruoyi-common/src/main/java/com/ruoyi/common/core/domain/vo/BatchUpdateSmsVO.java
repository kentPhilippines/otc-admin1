package com.ruoyi.common.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BatchUpdateSmsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String editSmsType;

    /** 任务开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date taskBeginTime;

    /** 任务状态 */
    private String taskStatus;

    private String ids;
}
