package com.ruoyi.system.api.entity.fragment;

import lombok.Data;

import java.io.Serializable;
@Data
public class RawResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String version;
    private Body body;
}
