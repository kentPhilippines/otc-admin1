package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户详情对象 med_user
 * 
 * @author kkkkk
 * @date 2024-10-23
 */
public class MedUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long id;

    /** 会员头像 */
    @Excel(name = "会员头像")
    private String avatar;

    /** 账户 */
    @Excel(name = "账户")
    private String account;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String realName;

    /** 余额 */
    @Excel(name = "余额")
    private BigDecimal balance;

    /** USDT余额 */
    @Excel(name = "USDT余额")
    private BigDecimal usdtBalance;

    /** 信用积分 */
    @Excel(name = "信用积分")
    private Long creditScore;

    /** 上级ID */
    @Excel(name = "上级ID")
    private Long parentId;

    /** 是否在线 0: 否, 1: 是 */
    @Excel(name = "是否在线 0: 否, 1: 是")
    private Integer isOnline;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registrationTime;

    /** 最后登陆时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastLoginTime;

    /** 登录IP */
    @Excel(name = "登录IP")
    private String loginIp;

    /** 银行账户 */
    @Excel(name = "银行账户")
    private String bankAccount;

    /** 银行名 */
    @Excel(name = "银行名")
    private String bankName;

    /** 银行地址 */
    @Excel(name = "银行地址")
    private String bankAddress;

    /** 会员风控 0: 正常, 1: 风控中 */
    @Excel(name = "会员风控 0: 正常, 1: 风控中")
    private Integer riskControlStatus;

    /** 用户状态 1: 正常, 0: 禁用 */
    @Excel(name = "用户状态 1: 正常, 0: 禁用")
    private Integer userStatus;

    /** 登陆密码 */
    @Excel(name = "登陆密码")
    private String password;

    /** 资金密码 */
    @Excel(name = "资金密码")
    private String fundPassword;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
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

    public void setBalance(BigDecimal balance) 
    {
        this.balance = balance;
    }

    public BigDecimal getBalance() 
    {
        return balance;
    }

    public void setUsdtBalance(BigDecimal usdtBalance) 
    {
        this.usdtBalance = usdtBalance;
    }

    public BigDecimal getUsdtBalance() 
    {
        return usdtBalance;
    }

    public void setCreditScore(Long creditScore) 
    {
        this.creditScore = creditScore;
    }

    public Long getCreditScore() 
    {
        return creditScore;
    }

    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }

    public void setIsOnline(Integer isOnline) 
    {
        this.isOnline = isOnline;
    }

    public Integer getIsOnline() 
    {
        return isOnline;
    }

    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    public void setRegistrationTime(Date registrationTime) 
    {
        this.registrationTime = registrationTime;
    }

    public Date getRegistrationTime() 
    {
        return registrationTime;
    }

    public void setLastLoginTime(Date lastLoginTime) 
    {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastLoginTime() 
    {
        return lastLoginTime;
    }

    public void setLoginIp(String loginIp) 
    {
        this.loginIp = loginIp;
    }

    public String getLoginIp() 
    {
        return loginIp;
    }

    public void setBankAccount(String bankAccount) 
    {
        this.bankAccount = bankAccount;
    }

    public String getBankAccount() 
    {
        return bankAccount;
    }

    public void setBankName(String bankName) 
    {
        this.bankName = bankName;
    }

    public String getBankName() 
    {
        return bankName;
    }

    public void setBankAddress(String bankAddress) 
    {
        this.bankAddress = bankAddress;
    }

    public String getBankAddress() 
    {
        return bankAddress;
    }

    public void setRiskControlStatus(Integer riskControlStatus) 
    {
        this.riskControlStatus = riskControlStatus;
    }

    public Integer getRiskControlStatus() 
    {
        return riskControlStatus;
    }

    public void setUserStatus(Integer userStatus) 
    {
        this.userStatus = userStatus;
    }

    public Integer getUserStatus() 
    {
        return userStatus;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setFundPassword(String fundPassword) 
    {
        this.fundPassword = fundPassword;
    }

    public String getFundPassword() 
    {
        return fundPassword;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("avatar", getAvatar())
            .append("account", getAccount())
            .append("realName", getRealName())
            .append("balance", getBalance())
            .append("usdtBalance", getUsdtBalance())
            .append("creditScore", getCreditScore())
            .append("parentId", getParentId())
            .append("isOnline", getIsOnline())
            .append("remarks", getRemarks())
            .append("registrationTime", getRegistrationTime())
            .append("lastLoginTime", getLastLoginTime())
            .append("loginIp", getLoginIp())
            .append("bankAccount", getBankAccount())
            .append("bankName", getBankName())
            .append("bankAddress", getBankAddress())
            .append("riskControlStatus", getRiskControlStatus())
            .append("userStatus", getUserStatus())
            .append("password", getPassword())
            .append("fundPassword", getFundPassword())
            .toString();
    }
}
