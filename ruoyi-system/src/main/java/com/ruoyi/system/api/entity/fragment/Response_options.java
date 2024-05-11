package com.ruoyi.system.api.entity.fragment;

import lombok.Data;

import java.io.Serializable;
@Data
public class Response_options implements Serializable {

    private static final long serialVersionUID = 1L;
    private String callback_url;
    private boolean broadcast;
}
