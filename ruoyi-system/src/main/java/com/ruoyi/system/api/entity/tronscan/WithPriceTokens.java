package com.ruoyi.system.api.entity.tronscan;

import lombok.Data;

import java.io.Serializable;

@Data
public class WithPriceTokens implements Serializable {


    private static final long serialVersionUID = 1L;
    private String amount;
    private int tokenPriceInTrx;
    private String tokenId;
    private String balance;
    private String tokenName;
    private int tokenDecimal;
    private String tokenAbbr;
    private int tokenCanShow;
    private String tokenType;
    private boolean vip;
    private String tokenLogo;
}
