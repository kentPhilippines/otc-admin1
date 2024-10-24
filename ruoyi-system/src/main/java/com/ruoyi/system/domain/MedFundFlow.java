package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户资金流水对象 med_fund_flow
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
public class MedFundFlow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流水唯一ID */
    private Long id;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String nickname;

    /** 资金变动类型 (如: 充值, 提现, 交易, 奖励等) */
    @Excel(name = "资金变动类型 (如: 充值, 提现, 交易, 奖励等)")
    private String type;

    /** 账变金额 */
    @Excel(name = "账变金额")
    private BigDecimal changeAmount;

    /** 账变内容 (描述资金变动的原因) */
    @Excel(name = "账变内容 (描述资金变动的原因)")
    private String changeContent;

    /** 账变前金额 */
    @Excel(name = "账变前金额")
    private BigDecimal balanceBefore;

    /** 账变后金额 */
    @Excel(name = "账变后金额")
    private BigDecimal balanceAfter;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setNickname(String nickname) 
    {
        this.nickname = nickname;
    }

    public String getNickname() 
    {
        return nickname;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    public void setChangeAmount(BigDecimal changeAmount) 
    {
        this.changeAmount = changeAmount;
    }

    public BigDecimal getChangeAmount() 
    {
        return changeAmount;
    }

    public void setChangeContent(String changeContent) 
    {
        this.changeContent = changeContent;
    }

    public String getChangeContent() 
    {
        return changeContent;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) 
    {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceBefore() 
    {
        return balanceBefore;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) 
    {
        this.balanceAfter = balanceAfter;
    }

    public BigDecimal getBalanceAfter() 
    {
        return balanceAfter;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("nickname", getNickname())
            .append("type", getType())
            .append("changeAmount", getChangeAmount())
            .append("changeContent", getChangeContent())
            .append("balanceBefore", getBalanceBefore())
            .append("balanceAfter", getBalanceAfter())
            .append("createdAt", getCreatedAt())
            .toString();
    }
}
