package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户充值对象 med_recharge
 * 
 * @author kkkkkk
 * @date 2024-10-23
 */
public class MedRecharge extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 充值记录ID */
    private Long id;

    /** 会员名称 */
    @Excel(name = "会员名称")
    private String memberName;

    /** 转账姓名 */
    @Excel(name = "转账姓名")
    private String transferName;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal paymentAmount;

    /** 充值后的剩余余额 */
    @Excel(name = "充值后的剩余余额")
    private BigDecimal remainingBalance;

    /** 充值币种 */
    @Excel(name = "充值币种")
    private String rechargeCurrency;

    /** 支付类型 (如: 银行卡, 支付宝, 微信) */
    @Excel(name = "支付类型 (如: 银行卡, 支付宝, 微信)")
    private String paymentType;

    /** 转账截图 (文件路径) */
    @Excel(name = "转账截图 (文件路径)")
    private String transferScreenshot;

    /** 充值时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "充值时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rechargeTime;

    /** 会员备注 */
    @Excel(name = "会员备注")
    private String memberNote;

    /** 来源IP地址 */
    @Excel(name = "来源IP地址")
    private String sourceIp;

    /** 来源地址 (如: 地理位置) */
    @Excel(name = "来源地址 (如: 地理位置)")
    private String sourceAddress;

    /** 状态 (0:待审核, 1:已通过, 2:已拒绝) */
    @Excel(name = "状态 (0:待审核, 1:已通过, 2:已拒绝)")
    private Integer status;

    /** 审核账号 (审核人员的账户名称) */
    @Excel(name = "审核账号 (审核人员的账户名称)")
    private String reviewAccount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setMemberName(String memberName) 
    {
        this.memberName = memberName;
    }

    public String getMemberName() 
    {
        return memberName;
    }

    public void setTransferName(String transferName) 
    {
        this.transferName = transferName;
    }

    public String getTransferName() 
    {
        return transferName;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) 
    {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getPaymentAmount() 
    {
        return paymentAmount;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) 
    {
        this.remainingBalance = remainingBalance;
    }

    public BigDecimal getRemainingBalance() 
    {
        return remainingBalance;
    }

    public void setRechargeCurrency(String rechargeCurrency) 
    {
        this.rechargeCurrency = rechargeCurrency;
    }

    public String getRechargeCurrency() 
    {
        return rechargeCurrency;
    }

    public void setPaymentType(String paymentType) 
    {
        this.paymentType = paymentType;
    }

    public String getPaymentType() 
    {
        return paymentType;
    }

    public void setTransferScreenshot(String transferScreenshot) 
    {
        this.transferScreenshot = transferScreenshot;
    }

    public String getTransferScreenshot() 
    {
        return transferScreenshot;
    }

    public void setRechargeTime(Date rechargeTime) 
    {
        this.rechargeTime = rechargeTime;
    }

    public Date getRechargeTime() 
    {
        return rechargeTime;
    }

    public void setMemberNote(String memberNote) 
    {
        this.memberNote = memberNote;
    }

    public String getMemberNote() 
    {
        return memberNote;
    }

    public void setSourceIp(String sourceIp) 
    {
        this.sourceIp = sourceIp;
    }

    public String getSourceIp() 
    {
        return sourceIp;
    }

    public void setSourceAddress(String sourceAddress) 
    {
        this.sourceAddress = sourceAddress;
    }

    public String getSourceAddress() 
    {
        return sourceAddress;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setReviewAccount(String reviewAccount) 
    {
        this.reviewAccount = reviewAccount;
    }

    public String getReviewAccount() 
    {
        return reviewAccount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("memberName", getMemberName())
            .append("transferName", getTransferName())
            .append("paymentAmount", getPaymentAmount())
            .append("remainingBalance", getRemainingBalance())
            .append("rechargeCurrency", getRechargeCurrency())
            .append("paymentType", getPaymentType())
            .append("transferScreenshot", getTransferScreenshot())
            .append("rechargeTime", getRechargeTime())
            .append("memberNote", getMemberNote())
            .append("sourceIp", getSourceIp())
            .append("sourceAddress", getSourceAddress())
            .append("status", getStatus())
            .append("reviewAccount", getReviewAccount())
            .toString();
    }
}
