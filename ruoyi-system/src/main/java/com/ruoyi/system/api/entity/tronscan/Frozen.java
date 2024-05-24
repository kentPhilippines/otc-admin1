package com.ruoyi.system.api.entity.tronscan;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class Frozen implements Serializable {


    private static final long serialVersionUID = 1L;
    private int total;
    private List<String> balances;
}
