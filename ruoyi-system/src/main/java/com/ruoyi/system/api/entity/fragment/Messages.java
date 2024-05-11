package com.ruoyi.system.api.entity.fragment;

import lombok.Data;

import java.io.Serializable;

@Data
public class Messages implements Serializable {
    private final static long serialVersionUID = 1L;
    private String address;
    private long amount;
    private String payload;
}
