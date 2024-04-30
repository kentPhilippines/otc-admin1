package com.ruoyi.system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AccountResourceResponse implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer freeNetUsed;
    private Integer freeNetLimit;
    private List<AssetNetUsed> assetNetUsed;
    private List<AssetNetLimit> assetNetLimit;
    private Long TotalNetLimit;
    private Long TotalNetWeight;
    private Integer EnergyUsed;
    private Long EnergyLimit;
    private Long TotalEnergyLimit;
    private Long TotalEnergyWeight;



}
