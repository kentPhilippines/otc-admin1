package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 群发国家单价配置对象 sms_country_price
 * 
 * @author dorion
 * @date 2024-07-21
 */
public class SmsCountryPrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idSmsCountryPrice;

    /** 国家 */
    @Excel(name = "国家")
    private Long countryId;

    /** 国家代码 */
    @Excel(name = "国家代码")
    private String areaCode;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    public void setIdSmsCountryPrice(Long idSmsCountryPrice) 
    {
        this.idSmsCountryPrice = idSmsCountryPrice;
    }

    public Long getIdSmsCountryPrice() 
    {
        return idSmsCountryPrice;
    }
    public void setCountryId(Long countryId) 
    {
        this.countryId = countryId;
    }

    public Long getCountryId() 
    {
        return countryId;
    }
    public void setAreaCode(String areaCode) 
    {
        this.areaCode = areaCode;
    }

    public String getAreaCode() 
    {
        return areaCode;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("idSmsCountryPrice", getIdSmsCountryPrice())
            .append("countryId", getCountryId())
            .append("areaCode", getAreaCode())
            .append("price", getPrice())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
