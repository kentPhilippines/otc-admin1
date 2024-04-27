package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Sensitive;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.enums.DesensitizedType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * trx兑能量记录对象 trx_exchange_info
 * 
 * @author dorion
 * @date 2024-04-13
 */
@Builder
@Data
public class TrxExchangeInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** trx进账表 */
    private Long idTrxExchangeInfo;

    /** 转出账户 */
    @Excel(name = "交易账户")
    private String fromAddress;

    /** 转入账户 */
    @Excel(name = "转入账户")
    @Sensitive(desensitizedType = DesensitizedType.WALLET_ADDRESS)
    private String toAddress;

    /** 实际出账账户 */
    @Excel(name = "实际出账账户")
    @Sensitive(desensitizedType = DesensitizedType.WALLET_ADDRESS)
    private String accountAddress;

    /** 单位 */
    @Excel(name = "单价")
    private Long price;

    /** 订单hash */
    @Excel(name = "订单")
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

    /** 兑换支出能量 */
    @Excel(name = "兑换支出能量")
    private Long delegateAmountTrx;

    /** 能量交易订单hash */
    @Excel(name = "能量交易订单")
    private String delegateTxId;

    /** 锁定周期 */
    @Excel(name = "锁定周期")
    private Long lockPeriod;

    /** 资源委托状态 */
    @Excel(name = "资源委托状态")
    private String delegateStatus;

    /** 回收资源交易id */
    @Excel(name = "回收资源交易")
    private String unDelegateTxId;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date fcd;

    /** 创建用户 */
    @Excel(name = "创建用户")
    private String fcu;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat ="yyyy-MM-dd HH:mm:ss")
    private Date lcd;

    /** 更新用户 */
    @Excel(name = "更新用户")
    private String lcu;

    public void setIdTrxExchangeInfo(Long idTrxExchangeInfo) 
    {
        this.idTrxExchangeInfo = idTrxExchangeInfo;
    }

    public Long getIdTrxExchangeInfo() 
    {
        return idTrxExchangeInfo;
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
    public void setTrxAmount(Long trxAmount) 
    {
        this.trxAmount = trxAmount;
    }

    public Long getTrxAmount() 
    {
        return trxAmount;
    }
    public void setDelegateAmountTrx(Long delegateAmountTrx) 
    {
        this.delegateAmountTrx = delegateAmountTrx;
    }

    public Long getDelegateAmountTrx() 
    {
        return delegateAmountTrx;
    }
    public void setDelegateTxId(String delegateTxId) 
    {
        this.delegateTxId = delegateTxId;
    }

    public String getDelegateTxId() 
    {
        return delegateTxId;
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
    public void setUnDelegateTxId(String unDelegateTxId) 
    {
        this.unDelegateTxId = unDelegateTxId;
    }

    public String getUnDelegateTxId() 
    {
        return unDelegateTxId;
    }
    public void setFcd(Date fcd) 
    {
        this.fcd = fcd;
    }

    public Date getFcd() 
    {
        return fcd;
    }
    public void setFcu(String fcu) 
    {
        this.fcu = fcu;
    }

    public String getFcu() 
    {
        return fcu;
    }
    public void setLcd(Date lcd) 
    {
        this.lcd = lcd;
    }

    public Date getLcd() 
    {
        return lcd;
    }
    public void setLcu(String lcu) 
    {
        this.lcu = lcu;
    }

    public String getLcu() 
    {
        return lcu;
    }

    public Long getTranferCount() {
        return tranferCount;
    }

    public void setTranferCount(Long tranferCount) {
        this.tranferCount = tranferCount;
    }

    public String getEnergyBusiType() {
        return energyBusiType;
    }

    public void setEnergyBusiType(String energyBusiType) {
        this.energyBusiType = energyBusiType;
    }
}
