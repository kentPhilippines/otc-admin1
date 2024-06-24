package com.ruoyi.system.api.entity.U02cx;

import lombok.Data;

import java.io.Serializable;
@Data
public class U02cxCommonResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功 : 0,失败：其他
     */
    private int status;

    /**
     * 	成功 : success,失败：具体原因
     */
    private String msg;
}
