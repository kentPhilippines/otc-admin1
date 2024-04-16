package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出账账户对象 account_address_info
 * 
 * @author dorion
 * @date 2024-04-13
 */
@Data
public class AccountAddressInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idAccoutAddressInfo;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 加密之后的账户秘钥 */
    @Excel(name = "加密之后的账户秘钥")
    private String encryptPrivateKey;

    /** 用于解密加密之后的账户秘钥的秘钥 */
    @Excel(name = "用于解密加密之后的账户秘钥的秘钥")
    private String encryptKey;

    /** 是否有效,用于逻辑删除 */
    @Excel(name = "是否有效,用于逻辑删除")
    private String isValid;

    /**
     * 带宽资源
     */
    private String netResource;

    /**
     * 能量资源
     */
    private String energyResource;

    /**
     * 冻结资产数
     */
    private BigDecimal totalFrozen;

    /**
     * trx账户余额
     */
    private BigDecimal trxBalance;

    //** 创建时间 *//*
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date fcd;

    //** 创建用户 *//*
    @Excel(name = "创建用户")
    private String fcu;

    //** 更新时间 *//*
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lcd;

    //** 更新用户 *//*
    @Excel(name = "更新用户")
    private String lcu;



}
