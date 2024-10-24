package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户交易订单对象 med_orders
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
public class MedOrders extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单唯一ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户账号 */
    @Excel(name = "用户账号")
    private String account;

    /** 用户真实姓名 */
    @Excel(name = "用户真实姓名")
    private String realName;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 交易方向 (买入/卖出) */
    @Excel(name = "交易方向 (买入/卖出)")
    private String direction;

    /** 交易类型 (例如: USDT/CNY) */
    @Excel(name = "交易类型 (例如: USDT/CNY)")
    private String tradeType;

    /** 购买金额 */
    @Excel(name = "购买金额")
    private BigDecimal purchaseAmount;

    /** 买入后的余额 */
    @Excel(name = "买入后的余额")
    private BigDecimal balanceAfterPurchase;

    /** 建仓价格 */
    @Excel(name = "建仓价格")
    private BigDecimal openingPrice;

    /** 平仓价格 */
    @Excel(name = "平仓价格")
    private BigDecimal closingPrice;

    /** 建仓时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "建仓时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date openingTime;

    /** 平仓时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "平仓时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date closingTime;

    /** 类型 (例如: 长线, 短线 具体玩法) */
    @Excel(name = "类型 (例如: 长线, 短线 具体玩法)")
    private String orderType;

    /** 交易结果 (盈利/亏损) */
    @Excel(name = "交易结果 (盈利/亏损)")
    private String dealResult;

    /** 单控 (0: 否, 1: 是) */
    @Excel(name = "单控 (0: 否, 1: 是)")
    private Integer individualControl;

    /** 用户IP地址 */
    @Excel(name = "用户IP地址")
    private String ipAddress;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setAccount(String account) 
    {
        this.account = account;
    }

    public String getAccount() 
    {
        return account;
    }

    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }

    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }

    public void setDirection(String direction) 
    {
        this.direction = direction;
    }

    public String getDirection() 
    {
        return direction;
    }

    public void setTradeType(String tradeType) 
    {
        this.tradeType = tradeType;
    }

    public String getTradeType() 
    {
        return tradeType;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) 
    {
        this.purchaseAmount = purchaseAmount;
    }

    public BigDecimal getPurchaseAmount() 
    {
        return purchaseAmount;
    }

    public void setBalanceAfterPurchase(BigDecimal balanceAfterPurchase) 
    {
        this.balanceAfterPurchase = balanceAfterPurchase;
    }

    public BigDecimal getBalanceAfterPurchase() 
    {
        return balanceAfterPurchase;
    }

    public void setOpeningPrice(BigDecimal openingPrice) 
    {
        this.openingPrice = openingPrice;
    }

    public BigDecimal getOpeningPrice() 
    {
        return openingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) 
    {
        this.closingPrice = closingPrice;
    }

    public BigDecimal getClosingPrice() 
    {
        return closingPrice;
    }

    public void setOpeningTime(Date openingTime) 
    {
        this.openingTime = openingTime;
    }

    public Date getOpeningTime() 
    {
        return openingTime;
    }

    public void setClosingTime(Date closingTime) 
    {
        this.closingTime = closingTime;
    }

    public Date getClosingTime() 
    {
        return closingTime;
    }

    public void setOrderType(String orderType) 
    {
        this.orderType = orderType;
    }

    public String getOrderType() 
    {
        return orderType;
    }

    public void setDealResult(String dealResult) 
    {
        this.dealResult = dealResult;
    }

    public String getDealResult() 
    {
        return dealResult;
    }

    public void setIndividualControl(Integer individualControl) 
    {
        this.individualControl = individualControl;
    }

    public Integer getIndividualControl() 
    {
        return individualControl;
    }

    public void setIpAddress(String ipAddress) 
    {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() 
    {
        return ipAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("account", getAccount())
            .append("realName", getRealName())
            .append("remark", getRemark())
            .append("productName", getProductName())
            .append("direction", getDirection())
            .append("tradeType", getTradeType())
            .append("purchaseAmount", getPurchaseAmount())
            .append("balanceAfterPurchase", getBalanceAfterPurchase())
            .append("openingPrice", getOpeningPrice())
            .append("closingPrice", getClosingPrice())
            .append("openingTime", getOpeningTime())
            .append("closingTime", getClosingTime())
            .append("orderType", getOrderType())
            .append("dealResult", getDealResult())
            .append("individualControl", getIndividualControl())
            .append("ipAddress", getIpAddress())
            .toString();
    }
}
