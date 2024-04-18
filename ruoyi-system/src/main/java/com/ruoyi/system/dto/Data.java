package com.ruoyi.system.dto;

import java.util.List;
@lombok.Data
public class Data {
    private List<Ret> ret;
    private List<String> signature;
    private String txID;
    private int net_usage;
    private String raw_data_hex;
    private int net_fee;
    private int energy_usage;
    private long blockNumber;
    private long block_timestamp;
    private int energy_fee;
    private int energy_usage_total;
    private Raw_data raw_data;
    private List<String> internal_transactions;



    private String transaction_id;
    private Token_info  token_info;

    private String from;
    private String to;
    private String type;
    private String value;
}
