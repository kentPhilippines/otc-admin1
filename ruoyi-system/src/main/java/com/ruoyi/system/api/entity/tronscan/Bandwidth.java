package com.ruoyi.system.api.entity.tronscan;

import lombok.Data;

import java.io.Serializable;
@Data
public class Bandwidth implements Serializable {


    private static final long serialVersionUID = 1L;
    private long energyRemaining;
    private long totalEnergyLimit;
    private long totalEnergyWeight;
    private int netUsed;
    private int storageLimit;
    private int storagePercentage;

    private double netPercentage;
    private int storageUsed;
    private int storageRemaining;
    private int freeNetLimit;
    private long energyUsed;
    private int freeNetRemaining;
    private int netLimit;
    private int netRemaining;
    private long energyLimit;
    private int freeNetUsed;
    private long totalNetWeight;
    private int freeNetPercentage;
    private double energyPercentage;
    private long totalNetLimit;
}
