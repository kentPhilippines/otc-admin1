package com.ruoyi.system.dto;

import lombok.Data;

import java.util.List;
@Data
public class Raw_data {
    private List<Contract> contract;
    private String ref_block_bytes;
    private String ref_block_hash;
    private long expiration;
    private long timestamp;
}
