package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 租户对象 tenant_info
 *
 * @author dorion
 * @date 2024-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idTenantInfo;

    /** 接收能量地址 */
    @Excel(name = "接收能量地址")
    private String receiverAddress;

    /** 监控入账地址 */
    @Excel(name = "监控入账地址")
    private String monitorAddress;


    /** 套餐类型 */
    @Excel(name = "套餐类型")
    private String energyBusiType;

    /** 单价 */
    @Excel(name = "单价")
    private Long price;

    /** 笔数 */
    @Excel(name = "笔数")
    private Long transferCount;

    /** 交易单位 */
    @Excel(name = "交易单位")
    private String exchangeUnit;

    /** 订单号 */
    @Excel(name = "订单号")
    private String txId;

    /** 交易总额 */
    @Excel(name = "交易总额")
    private Long exchangeAmount;

    /** 是否到账 */
    @Excel(name = "是否到账")
    private String isPaid;

    /** 租期 */
    @Excel(name = "租期")
    private Long period;

    /** 用户id */
    private String userId;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 已经委托天数 */
    @Excel(name = "已经委托天数")
    private Long delegatedDays;
    
    /** 已使用笔数 */
    @Excel(name = "已使用笔数")
    private Long totalCountUsed;

    /** 计算规则 */
    @Excel(name = "计算规则")
    private String calcRule;

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
