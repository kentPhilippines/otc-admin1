package com.ruoyi.system.api.entity.tronscan;

import lombok.Data;

import java.io.Serializable;

@Data
public class Representative implements Serializable {


    private static final long serialVersionUID = 1L;
    private long lastWithDrawTime;
    private int allowance;
    private boolean enabled;
    private String url;

}
