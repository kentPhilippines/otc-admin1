package com.sinosoft.activity.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动收集配置对象 act_page_config_subscribe
 * 
 * @author dy
 * @date 2021-04-08
 */
public class ActPageConfigSubscribe extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;

    /**活动代码  */
    @Excel(name = "活动代码")
    private String actCode;

    /**描述  */
    @Excel(name = "描述")
    private String description;

    /**背景图片  */
    @Excel(name = "背景图片")
    private String bgImg;

    /** 二维码图片 */
    @Excel(name = "二维码图片")
    private String bgImg2;

    /** 关注二维码描述 */
    @Excel(name = "关注二维码描述")
    private String qrCode;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setActCode(String actCode) 
    {
        this.actCode = actCode;
    }

    public String getActCode() 
    {
        return actCode;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setBgImg(String bgImg) 
    {
        this.bgImg = bgImg;
    }

    public String getBgImg() 
    {
        return bgImg;
    }
    public void setBgImg2(String bgImg2) 
    {
        this.bgImg2 = bgImg2;
    }

    public String getBgImg2() 
    {
        return bgImg2;
    }
    public void setQrCode(String qrCode) 
    {
        this.qrCode = qrCode;
    }

    public String getQrCode() 
    {
        return qrCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("actCode", getActCode())
            .append("description", getDescription())
            .append("bgImg", getBgImg())
            .append("bgImg2", getBgImg2())
            .append("qrCode", getQrCode())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}