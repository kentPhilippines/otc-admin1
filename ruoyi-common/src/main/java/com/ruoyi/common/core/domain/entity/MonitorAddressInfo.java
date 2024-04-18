package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 监听账户入账对象 monitor_address_info
 *
 * @author dorion
 * @date 2024-04-14
 */
public class MonitorAddressInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idMonitorAddress;

    /** 业务类型 */
    @Excel(name = "业务类型")
    private String busiType;

    /** 监听地址 */
    @Excel(name = "监听地址")
    private String monitorAddress;

    /** 出账地址 */
    @Excel(name = "出账地址")
    private String accountAddress;

    /** 单价 */
    @Excel(name = "单价")
    private Long price;

    /** trx或者usdt */
    @Excel(name = "trx或者usdt")
    private String monitorType;

    /** API_KEY */
    @Excel(name = "API_KEY")
    private String apiKey;

    /** 是否有效 */
    @Excel(name = "是否有效")
    private String isValid;

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

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public void setIdMonitorAddress(Long idMonitorAddress)
    {
        this.idMonitorAddress = idMonitorAddress;
    }

    public Long getIdMonitorAddress()
    {
        return idMonitorAddress;
    }
    public void setMonitorAddress(String monitorAddress)
    {
        this.monitorAddress = monitorAddress;
    }

    public String getMonitorAddress()
    {
        return monitorAddress;
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
    public void setMonitorType(String monitorType)
    {
        this.monitorType = monitorType;
    }

    public String getMonitorType()
    {
        return monitorType;
    }
    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public String getApiKey()
    {
        return apiKey;
    }
    public void setIsValid(String isValid)
    {
        this.isValid = isValid;
    }

    public String getIsValid()
    {
        return isValid;
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
                .append("idMonitorAddress", getIdMonitorAddress())
                .append("monitorAddress", getMonitorAddress())
                .append("accountAddress", getAccountAddress())
                .append("price", getPrice())
                .append("monitorType", getMonitorType())
                .append("apiKey", getApiKey())
                .append("isValid", getIsValid())
                .append("fcd", getFcd())
                .append("fcu", getFcu())
                .append("lcd", getLcd())
                .append("lcu", getLcu())
                .toString();
    }
}
