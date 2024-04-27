package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TrxExchangeMonitorAccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long idTrxExchangeInfo;

    /**
     *
     */
    private String fromAddress;

    private String toAddress;

    /**
     *
     */
    private String accountAddress;

    /**
     *
     */
    private Integer tranferCount;

    /**
     *
     */
    private String energyBusiType;

    /**
     *
     */
    private Long delegateAmountTrx;

    /**
     *
     */
    private Long lockPeriod;

    /**
     *
     */
    private String delegateStatus;

    private String delegateTxId;

    /**
     *
     */
    private Date fcd;

    /**
     *
     */
    private String apiKey;

    /**
     *
     */
    private String encryptKey;

    /**
     *
     */
    private String encryptPrivateKey;


    private String unDelegateTxId;
}
