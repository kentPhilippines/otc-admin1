package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品对象 med_product
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
public class MedProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 产品代码 */
    @Excel(name = "产品代码")
    private String code;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String title;

    /** 产品图标 */
    @Excel(name = "产品图标")
    private String img;

    /** 产品分类 */
    @Excel(name = "产品分类")
    private String type;

    /** 时间玩法间隔 */
    @Excel(name = "时间玩法间隔")
    private String gamesType;

    /** 盈亏控制开关 0 关闭 1。开启 */
    @Excel(name = "盈亏控制开关 0 关闭 1。开启")
    private Integer controlStatus;

    /** 盈亏规则:盈亏规则: */
    @Excel(name = "盈亏规则:盈亏规则:")
    private String controlRules;

    /** 交易类型 USDT/CNY */
    @Excel(name = "交易类型 USDT/CNY")
    private String dealType;

    /** 是否开启 */
    @Excel(name = "是否开启")
    private Integer isOpen;

    /** 状态 正常/禁用 */
    @Excel(name = "状态 正常/禁用")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setImg(String img) 
    {
        this.img = img;
    }

    public String getImg() 
    {
        return img;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    public void setGamesType(String gamesType) 
    {
        this.gamesType = gamesType;
    }

    public String getGamesType() 
    {
        return gamesType;
    }

    public void setControlStatus(Integer controlStatus) 
    {
        this.controlStatus = controlStatus;
    }

    public Integer getControlStatus() 
    {
        return controlStatus;
    }

    public void setControlRules(String controlRules) 
    {
        this.controlRules = controlRules;
    }

    public String getControlRules() 
    {
        return controlRules;
    }

    public void setDealType(String dealType) 
    {
        this.dealType = dealType;
    }

    public String getDealType() 
    {
        return dealType;
    }

    public void setIsOpen(Integer isOpen) 
    {
        this.isOpen = isOpen;
    }

    public Integer getIsOpen() 
    {
        return isOpen;
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
            .append("code", getCode())
            .append("title", getTitle())
            .append("img", getImg())
            .append("type", getType())
            .append("gamesType", getGamesType())
            .append("controlStatus", getControlStatus())
            .append("controlRules", getControlRules())
            .append("remark", getRemark())
            .append("dealType", getDealType())
            .append("isOpen", getIsOpen())
            .append("status", getStatus())
            .toString();
    }
}
