package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 租户对象 tenant_info
 *
 * @author dorion
 * @date 2024-04-14
 */
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

    /** 最终到期日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最终到期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishTransferTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fcd;

    /** 创建用户 */
    @Excel(name = "创建用户")
    private String fcu;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lcd;

    /** 更新用户 */
    @Excel(name = "更新用户")
    private String lcu;

    public void setIdTenantInfo(Long idTenantInfo)
    {
        this.idTenantInfo = idTenantInfo;
    }

    public Long getIdTenantInfo()
    {
        return idTenantInfo;
    }
    public void setReceiverAddress(String receiverAddress)
    {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverAddress()
    {
        return receiverAddress;
    }
    public void setMonitorAddress(String monitorAddress)
    {
        this.monitorAddress = monitorAddress;
    }

    public String getMonitorAddress()
    {
        return monitorAddress;
    }
    public void setPrice(Long price)
    {
        this.price = price;
    }

    public Long getPrice()
    {
        return price;
    }
    public void setTransferCount(Long transferCount)
    {
        this.transferCount = transferCount;
    }

    public Long getTransferCount()
    {
        return transferCount;
    }
    public void setExchangeUnit(String exchangeUnit)
    {
        this.exchangeUnit = exchangeUnit;
    }

    public String getExchangeUnit()
    {
        return exchangeUnit;
    }
    public void setTxId(String txId)
    {
        this.txId = txId;
    }

    public String getTxId()
    {
        return txId;
    }
    public void setExchangeAmount(Long exchangeAmount)
    {
        this.exchangeAmount = exchangeAmount;
    }

    public Long getExchangeAmount()
    {
        return exchangeAmount;
    }
    public void setIsPaid(String isPaid)
    {
        this.isPaid = isPaid;
    }

    public String getIsPaid()
    {
        return isPaid;
    }
    public void setPeriod(Long period)
    {
        this.period = period;
    }

    public Long getPeriod()
    {
        return period;
    }
    public void setFinishTransferTime(Date finishTransferTime)
    {
        this.finishTransferTime = finishTransferTime;
    }

    public Date getFinishTransferTime()
    {
        return finishTransferTime;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("idTenantInfo", getIdTenantInfo())
                .append("receiverAddress", getReceiverAddress())
                .append("monitorAddress", getMonitorAddress())
                .append("price", getPrice())
                .append("transferCount", getTransferCount())
                .append("exchangeUnit", getExchangeUnit())
                .append("txId", getTxId())
                .append("exchangeAmount", getExchangeAmount())
                .append("isPaid", getIsPaid())
                .append("period", getPeriod())
                .append("finishTransferTime", getFinishTransferTime())
                .append("fcd", getFcd())
                .append("fcu", getFcu())
                .append("lcd", getLcd())
                .append("lcu", getLcu())
                .toString();
    }
}
