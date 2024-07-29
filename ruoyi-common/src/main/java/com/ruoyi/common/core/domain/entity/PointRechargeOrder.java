package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 群发充值管理对象 point_recharge_order
 * 
 * @author dorion
 * @date 2024-07-21
 */
public class PointRechargeOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idPointRechargeOrder;

    /** 用户 */
    @Excel(name = "用户")
    private Long userId;

    /** 充值订单 */
    @Excel(name = "充值订单")
    private String orderNumber;

    /** 充值金额 */
    @Excel(name = "充值金额")
    private BigDecimal amount;

    /** 兑换汇率 */
    @Excel(name = "兑换汇率")
    private BigDecimal exchangeRate;

    /** 兑换积分 */
    @Excel(name = "兑换积分")
    private BigDecimal points;

    /** 支付状态 */
    @Excel(name = "支付状态")
    private String status;

    public void setIdPointRechargeOrder(Long idPointRechargeOrder) 
    {
        this.idPointRechargeOrder = idPointRechargeOrder;
    }

    public Long getIdPointRechargeOrder() 
    {
        return idPointRechargeOrder;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setOrderNumber(String orderNumber) 
    {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() 
    {
        return orderNumber;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setExchangeRate(BigDecimal exchangeRate) 
    {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getExchangeRate() 
    {
        return exchangeRate;
    }
    public void setPoints(BigDecimal points) 
    {
        this.points = points;
    }

    public BigDecimal getPoints() 
    {
        return points;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("idPointRechargeOrder", getIdPointRechargeOrder())
            .append("userId", getUserId())
            .append("orderNumber", getOrderNumber())
            .append("amount", getAmount())
            .append("exchangeRate", getExchangeRate())
            .append("points", getPoints())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
