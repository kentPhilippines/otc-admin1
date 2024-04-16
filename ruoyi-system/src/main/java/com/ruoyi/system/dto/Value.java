package com.ruoyi.system.dto;

import lombok.Data;

@Data
public class Value {
    private String resource;
    private long frozen_balance;
    private String owner_address;

    private long amount;

    private String to_address;

}
