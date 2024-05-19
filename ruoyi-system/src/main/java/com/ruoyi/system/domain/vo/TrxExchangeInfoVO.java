package com.ruoyi.system.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TrxExchangeInfoVO {
    /**
     *
     */
    private String idTrxExchangeInfo;

    /**
     *
     */
    private String fromAddress;

    /**
     *
     */
    private String toAddress;

    /**
     *
     */
    private String monitorAddressName;

    /**
     *
     */
    private String accountAddress;

    /**
     *
     */
    private Integer price;

    /**
     *
     */
    private String trxTxId;

    /**
     *
     */
    private String tranferCount;

    /**
     *
     */
    private String energyBusiType;

    /**
     *
     */
    private String trxAmount;

    /**
     *
     */
    private String trxAmountUnit;

    /**
     *
     */
    private String delegateAmountTrx;

    /**
     *
     */
    private String resourceCode;

    /**
     *
     */
    private String delegateTxId;

    /**
     *
     */
    private String lockPeriod;

    /**
     *
     */
    private String delegateStatus;

    /**
     *
     */
    private String unDelegateTxId;

    /**
     *
     */
    private Date fcd;

    /**
     *
     */
    private String fcu;

    /**
     *
     */
    private Date lcd;

    /**
     *
     */
    private String lcu;
    private String calcRule;


}