package com.ruoyi.system.api.entity.tronscan;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class AccountResponse implements Serializable {


    private static final long serialVersionUID = 1L;

    private long totalFrozenV2;
    private int transactions_out;
    private long frozenForEnergyV2;
    private int rewardNum;
    private int delegatedFrozenV2BalanceForBandwidth;
    private OwnerPermission ownerPermission;
    private String redTag;
    private int delegateFrozenForEnergy;
    private long balance;
    private long frozenForBandWidthV2;
    private int canWithdrawAmountV2;

    private int transactions_in;
    private int totalTransactionCount;
    private Representative representative;
    private String announcement;
    private List<String> allowExchange;
    private int accountType;
    private List<String> exchanges;
    private Frozen frozen;
    private int transactions;
    private long delegatedFrozenV2BalanceForEnergy;
    private String name;
    private int frozenForEnergy;
    private double energyCost;
    private List<ActivePermissions> activePermissions;
    private int acquiredDelegatedFrozenV2BalanceForBandwidth;
    private double netCost;
    private int acquiredDelegateFrozenForBandWidth;
    private String greyTag;
    private String publicTag;
    private List<WithPriceTokens> withPriceTokens;
    private int unfreezeV2;
    private boolean feedbackRisk;
    private int voteTotal;
    private int totalFrozen;
    private long latest_operation_time;
    private int frozenForBandWidth;
    private int reward;
    private String addressTagLogo;
    private String address;
    private List<String> frozen_supply;
    private Bandwidth bandwidth;
    private long date_created;
    private long acquiredDelegatedFrozenV2BalanceForEnergy;
    private String blueTag;
    private int witness;
    private long freezing;
    private int delegateFrozenForBandWidth;
    private boolean activated;
    private int acquiredDelegateFrozenForEnergy;
}
