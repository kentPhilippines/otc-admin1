package com.ruoyi.system.api.entity.tronscan;

import lombok.Data;

import java.io.Serializable;

@Data
public class Keys implements Serializable {


    private static final long serialVersionUID = 1L;

    private String address;
    private int weight;
}
