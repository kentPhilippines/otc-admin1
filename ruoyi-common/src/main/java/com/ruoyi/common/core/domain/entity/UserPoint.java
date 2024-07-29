package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 用户积分对象 user_point
 * 
 * @author dorion
 * @date 2024-07-21
 */
public class UserPoint extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idUserPoint;

    /** 用户 */
    @Excel(name = "用户")
    private Long userId;

    /** 剩余积分 */
    @Excel(name = "剩余积分")
    private BigDecimal pointBalance;

    public void setIdUserPoint(Long idUserPoint) 
    {
        this.idUserPoint = idUserPoint;
    }

    public Long getIdUserPoint() 
    {
        return idUserPoint;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setPointBalance(BigDecimal pointBalance) 
    {
        this.pointBalance = pointBalance;
    }

    public BigDecimal getPointBalance() 
    {
        return pointBalance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("idUserPoint", getIdUserPoint())
            .append("userId", getUserId())
            .append("pointBalance", getPointBalance())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
