package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户提现订单对象 med_withdrawal
 * 
 * @author kkkkkk
 * @date 2024-10-23
 */
public class MedWithdrawal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 提现记录ID */
    private Long id;

    /** 会员ID */
    @Excel(name = "会员ID")
    private Long memberId;

    /** 会员名称 */
    @Excel(name = "会员名称")
    private String memberName;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal amount;

    /** 实际到账金额 */
    @Excel(name = "实际到账金额")
    private BigDecimal actualAmount;

    /** 手续费 */
    @Excel(name = "手续费")
    private BigDecimal fee;

    /** 提现方式 (如: 银行卡, 支付宝, 微信) */
    @Excel(name = "提现方式 (如: 银行卡, 支付宝, 微信)")
    private String withdrawalMethod;

    /** 姓名 (真实姓名) */
    @Excel(name = "姓名 (真实姓名)")
    private String name;

    /** 卡号或账号 */
    @Excel(name = "卡号或账号")
    private String accountNumber;

    /** 银行名称 (如使用银行卡提现) */
    @Excel(name = "银行名称 (如使用银行卡提现)")
    private String bankName;

    /** 开户支行 (如使用银行卡提现) */
    @Excel(name = "开户支行 (如使用银行卡提现)")
    private String branchName;

    /** 出款前余额 */
    @Excel(name = "出款前余额")
    private BigDecimal beforeBalance;

    /** 出款后余额 */
    @Excel(name = "出款后余额")
    private BigDecimal afterBalance;

    /** 来源IP地址 */
    @Excel(name = "来源IP地址")
    private String sourceIp;

    /** 来源地址 (地理位置信息) */
    @Excel(name = "来源地址 (地理位置信息)")
    private String sourceAddress;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitTime;

    /** 状态 (0:待处理, 1:处理中, 2:已完成, 3:已拒绝) */
    @Excel(name = "状态 (0:待处理, 1:处理中, 2:已完成, 3:已拒绝)")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }

    public void setMemberName(String memberName) 
    {
        this.memberName = memberName;
    }

    public String getMemberName() 
    {
        return memberName;
    }

    public void setNote(String note) 
    {
        this.note = note;
    }

    public String getNote() 
    {
        return note;
    }

    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }

    public void setActualAmount(BigDecimal actualAmount) 
    {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getActualAmount() 
    {
        return actualAmount;
    }

    public void setFee(BigDecimal fee) 
    {
        this.fee = fee;
    }

    public BigDecimal getFee() 
    {
        return fee;
    }

    public void setWithdrawalMethod(String withdrawalMethod) 
    {
        this.withdrawalMethod = withdrawalMethod;
    }

    public String getWithdrawalMethod() 
    {
        return withdrawalMethod;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setAccountNumber(String accountNumber) 
    {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() 
    {
        return accountNumber;
    }

    public void setBankName(String bankName) 
    {
        this.bankName = bankName;
    }

    public String getBankName() 
    {
        return bankName;
    }

    public void setBranchName(String branchName) 
    {
        this.branchName = branchName;
    }

    public String getBranchName() 
    {
        return branchName;
    }

    public void setBeforeBalance(BigDecimal beforeBalance) 
    {
        this.beforeBalance = beforeBalance;
    }

    public BigDecimal getBeforeBalance() 
    {
        return beforeBalance;
    }

    public void setAfterBalance(BigDecimal afterBalance) 
    {
        this.afterBalance = afterBalance;
    }

    public BigDecimal getAfterBalance() 
    {
        return afterBalance;
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

    public void setSubmitTime(Date submitTime) 
    {
        this.submitTime = submitTime;
    }

    public Date getSubmitTime() 
    {
        return submitTime;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("memberId", getMemberId())
            .append("memberName", getMemberName())
            .append("note", getNote())
            .append("amount", getAmount())
            .append("actualAmount", getActualAmount())
            .append("fee", getFee())
            .append("withdrawalMethod", getWithdrawalMethod())
            .append("name", getName())
            .append("accountNumber", getAccountNumber())
            .append("bankName", getBankName())
            .append("branchName", getBranchName())
            .append("beforeBalance", getBeforeBalance())
            .append("afterBalance", getAfterBalance())
            .append("sourceIp", getSourceIp())
            .append("sourceAddress", getSourceAddress())
            .append("submitTime", getSubmitTime())
            .append("status", getStatus())
            .toString();
    }
}
