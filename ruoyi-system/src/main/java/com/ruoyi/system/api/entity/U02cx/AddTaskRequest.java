package com.ruoyi.system.api.entity.U02cx;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class AddTaskRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 任务开始时间 格式 yyyy-MM-dd HH:mm:ss
     */
    private String taskBeginTime;

    /**
     * 物料名称
     */
    private String fileName;

    /**
     * 物料文件下载地址，文件为utf-8编码的txt文件
     */
    private String filePath;

    /**
     * 物料文件MD5码
     */
    private String fileMd5;

    /**
     * 任务文本内容 base64加密字串
     */
    private String context;

    /**
     * 1-文本任务 2-图文任务 （当前默认文本任务）
     */
    private Integer type;



}
