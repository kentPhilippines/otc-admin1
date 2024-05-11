package com.ruoyi.system.api.entity.fragment;

import lombok.Data;

import java.io.Serializable;
@Data
public class CheckReqRespose implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean  confirmed;

    private String error;
}
