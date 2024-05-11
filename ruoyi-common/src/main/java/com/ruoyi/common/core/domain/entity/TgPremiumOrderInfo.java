package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TG会员充值对象 tg_premium_order_info
 *
 * @author dorion
 * @date 2024-05-04
 */
@Data
@Accessors(chain = true)
public class TgPremiumOrderInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idTgPremiumOrderInfo;

    /** tg用户名 */
    @Excel(name = "tg用户名")
    private String rechargeTgUserName;

    /** 开通时长 */
    @Excel(name = "开通时长")
    private Long months;

    /** 支付金额(TON) */
    @Excel(name = "支付金额(TON)")
    private BigDecimal actualAmount;

    /** tg订单id */
    @Excel(name = "tg订单id")
    private String idTg;

    /** tg支付状态 */
    @Excel(name = "tg支付状态")
    private String tgPaymentStatus;

    /** 售出金额(USDT) */
    @Excel(name = "售出金额(USDT)")
    private String orderAmount;

    /** 是否到账 */
    @Excel(name = "是否到账")
    private String isPaid;

    /** 交易账户 */
    @Excel(name = "交易账户")
    private String fromAddress;

    /** 转入账户 */
    @Excel(name = "转入账户")
    private String toAddress;

    /** 交易id */
    @Excel(name = "交易id")
    private Long txId;

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

