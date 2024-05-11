package com.ruoyi.system.api.entity.fragment;

import lombok.Data;

import java.io.Serializable;

@Data
public class Found implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean myself;
    private String recipient;
    private String photo;
    private String name;
}
