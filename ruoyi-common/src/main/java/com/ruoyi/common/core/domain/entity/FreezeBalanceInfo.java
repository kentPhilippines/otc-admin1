package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 抵押流水记录对象 freeze_balance_info
 * 
 * @author dorion
 * @date 2024-04-13
 */
@Data
public class FreezeBalanceInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idFreezeBalanceInfo;

    /** 账户地址 */
    @Excel(name = "账户地址")
    private String address;

    /** 抵押金额trx */
    @Excel(name = "抵押金额trx")
    private Long freezeTrxAmount;

    /** 充值类型类型id */
    @Excel(name = "充值类型")
    private String exchangeResourceType;

    /** 交易id */
    @Excel(name = "交易id")
    private String txId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date fcd;

    /** 创建用户 */
    @Excel(name = "创建用户")
    private String fcu;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lcd;

    /** 更新用户 */
    @Excel(name = "更新用户")
    private String lcu;

}
