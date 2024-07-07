package com.ruoyi.common.core.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 发送失败记录对象 trx_exchange_fail
 * 
 * @author dorion
 * @date 2024-07-07
 */
public class TrxExchangeFail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long idTrxExchangeFail;

    /** 转出账户 */
    @Excel(name = "转出账户")
    private String fromAddress;

    /** 转入账户 */
    @Excel(name = "转入账户")
    private String toAddress;

    /** 实际出账账户 */
    @Excel(name = "实际出账账户")
    private String accountAddress;

    /** 单价 */
    @Excel(name = "单价")
    private Long price;

    /** 订单hash */
    @Excel(name = "订单hash")
    private String trxTxId;

    /** 笔数 */
    @Excel(name = "笔数")
    private Long tranferCount;

    /** 业务类型 */
    @Excel(name = "业务类型")
    private String energyBusiType;

    /** 转入金额 */
    @Excel(name = "转入金额")
    private Long trxAmount;

    /** 转入单位 */
    @Excel(name = "转入单位")
    private String trxAmountUnit;


    /** 资源类型 */
    @Excel(name = "资源类型")
    private String resourceCode;

    /** 锁定周期 */
    @Excel(name = "锁定周期")
    private Long lockPeriod;

    /** 补偿状态 */
    @Excel(name = "补偿状态")
    private String delegateStatus;

    /** 笔数计算规则 */
    @Excel(name = "笔数计算规则")
    private String calcRule;

    public void setIdTrxExchangeFail(Long idTrxExchangeFail) 
    {
        this.idTrxExchangeFail = idTrxExchangeFail;
    }

    public Long getIdTrxExchangeFail() 
    {
        return idTrxExchangeFail;
    }
    public void setFromAddress(String fromAddress) 
    {
        this.fromAddress = fromAddress;
    }

    public String getFromAddress() 
    {
        return fromAddress;
    }
    public void setToAddress(String toAddress) 
    {
        this.toAddress = toAddress;
    }

    public String getToAddress() 
    {
        return toAddress;
    }
    public void setAccountAddress(String accountAddress) 
    {
        this.accountAddress = accountAddress;
    }

    public String getAccountAddress() 
    {
        return accountAddress;
    }
    public void setPrice(Long price) 
    {
        this.price = price;
    }

    public Long getPrice() 
    {
        return price;
    }
    public void setTrxTxId(String trxTxId) 
    {
        this.trxTxId = trxTxId;
    }

    public String getTrxTxId() 
    {
        return trxTxId;
    }
    public void setTranferCount(Long tranferCount) 
    {
        this.tranferCount = tranferCount;
    }

    public Long getTranferCount() 
    {
        return tranferCount;
    }
    public void setEnergyBusiType(String energyBusiType) 
    {
        this.energyBusiType = energyBusiType;
    }

    public String getEnergyBusiType() 
    {
        return energyBusiType;
    }
    public void setTrxAmount(Long trxAmount) 
    {
        this.trxAmount = trxAmount;
    }

    public Long getTrxAmount() 
    {
        return trxAmount;
    }
    public void setTrxAmountUnit(String trxAmountUnit) 
    {
        this.trxAmountUnit = trxAmountUnit;
    }

    public String getTrxAmountUnit() 
    {
        return trxAmountUnit;
    }

    public void setResourceCode(String resourceCode) 
    {
        this.resourceCode = resourceCode;
    }

    public String getResourceCode() 
    {
        return resourceCode;
    }
    public void setLockPeriod(Long lockPeriod) 
    {
        this.lockPeriod = lockPeriod;
    }

    public Long getLockPeriod() 
    {
        return lockPeriod;
    }
    public void setDelegateStatus(String delegateStatus) 
    {
        this.delegateStatus = delegateStatus;
    }

    public String getDelegateStatus() 
    {
        return delegateStatus;
    }
    public void setCalcRule(String calcRule) 
    {
        this.calcRule = calcRule;
    }

    public String getCalcRule() 
    {
        return calcRule;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("idTrxExchangeFail", getIdTrxExchangeFail())
            .append("fromAddress", getFromAddress())
            .append("toAddress", getToAddress())
            .append("accountAddress", getAccountAddress())
            .append("price", getPrice())
            .append("trxTxId", getTrxTxId())
            .append("tranferCount", getTranferCount())
            .append("energyBusiType", getEnergyBusiType())
            .append("trxAmount", getTrxAmount())
            .append("trxAmountUnit", getTrxAmountUnit())
            .append("resourceCode", getResourceCode())
            .append("lockPeriod", getLockPeriod())
            .append("delegateStatus", getDelegateStatus())
            .append("calcRule", getCalcRule())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
