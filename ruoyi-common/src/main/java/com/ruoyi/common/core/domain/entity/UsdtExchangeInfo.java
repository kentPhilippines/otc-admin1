package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * USDT交易明细对象 usdt_exchange_info
 * 
 * @author dorion
 * @date 2024-04-19
 */
@Accessors(chain = true)
@Data
public class UsdtExchangeInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idUsdtExchangeInfo;

    /** 转入地址 */
    @Excel(name = "交易地址")
    private String fromAddress;

    /** 转出地址 */
    @Excel(name = "转入地址")
    private String toAddress;


    /** 出账地址 */
    @Excel(name = "出账地址")
    private String accountAddress;

    /** 交易订单 */
    @Excel(name = "交易订单")
    private String usdtTxId;

    /** 转入金额 */
    @Excel(name = "转入金额")
    private BigDecimal usdtAmount;

    /** 转出金额 */
    @Excel(name = "转出金额")
    private BigDecimal trxAmount;

    /** 兑换费率 */
    @Excel(name = "兑换费率")
    private BigDecimal exchangeRate;

    /** TRX订单 */
    @Excel(name = "TRX订单")
    private String trxTxId;

    /** 原始费率 */
    @Excel(name = "原始费率")
    private BigDecimal orginalExchangeRate;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fcd;

    /** 创建用户 */
    @Excel(name = "创建用户")
    private String fcu;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lcd;

    /** 更新用户 */
    @Excel(name = "更新用户")
    private String lcu;


}
