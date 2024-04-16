package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 错误日志对象 error_log
 * 
 * @author dorion
 * @date 2024-04-13
 */
@Builder
public class ErrorLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idErrorLog;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 交易id */
    @Excel(name = "交易id")
    private String trxId;

    /** 其他可能关联的eid */
    @Excel(name = "其他可能关联的eid")
    private String otherId;

    /** 错误类型发生场景 */
    @Excel(name = "错误类型发生场景")
    private String errorCode;

    /** 错误信息 */
    @Excel(name = "错误信息")
    private String errorMsg;

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

    public void setIdErrorLog(Long idErrorLog) 
    {
        this.idErrorLog = idErrorLog;
    }

    public Long getIdErrorLog() 
    {
        return idErrorLog;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setTrxId(String trxId) 
    {
        this.trxId = trxId;
    }

    public String getTrxId() 
    {
        return trxId;
    }
    public void setOtherId(String otherId) 
    {
        this.otherId = otherId;
    }

    public String getOtherId() 
    {
        return otherId;
    }
    public void setErrorCode(String errorCode) 
    {
        this.errorCode = errorCode;
    }

    public String getErrorCode() 
    {
        return errorCode;
    }
    public void setErrorMsg(String errorMsg) 
    {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() 
    {
        return errorMsg;
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
            .append("idErrorLog", getIdErrorLog())
            .append("address", getAddress())
            .append("trxId", getTrxId())
            .append("otherId", getOtherId())
            .append("errorCode", getErrorCode())
            .append("errorMsg", getErrorMsg())
            .append("fcd", getFcd())
            .append("fcu", getFcu())
            .append("lcd", getLcd())
            .append("lcu", getLcu())
            .toString();
    }
}
