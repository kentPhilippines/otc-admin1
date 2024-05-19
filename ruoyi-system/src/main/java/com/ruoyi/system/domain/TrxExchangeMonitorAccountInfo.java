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

    private String  resourceCode;

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
    private Integer countUsed;


    private String calcRule;

   /* private String status;


    private String isPaid;


    private Long delegatedDays;
    private Long period;


    private Integer maxTransferCount;


    private Long idTenantInfo;*/


}
