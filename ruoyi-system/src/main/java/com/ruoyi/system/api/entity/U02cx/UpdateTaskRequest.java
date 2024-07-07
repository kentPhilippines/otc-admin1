package com.ruoyi.system.api.entity.U02cx;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class UpdateTaskRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long taskId;


    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 任务开始时间 格式 yyyy-MM-dd HH:mm:ss
     */
    private String taskBeginTime;



}
