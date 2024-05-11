package com.ruoyi.system.api.entity.fragment;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class Transaction implements Serializable {
    private final static long serialVersionUID = 1L;
    private long validUntil;
    private List<Messages> messages;
}
