package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 实名审核对象 med_real_name_verification
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
public class MedRealNameVerification extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 实名认证记录ID */
    private Long id;

    /** 会员ID */
    @Excel(name = "会员ID")
    private Long memberId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String idCardNumber;

    /** 身份证正面图片路径 */
    @Excel(name = "身份证正面图片路径")
    private String idCardFront;

    /** 身份证反面图片路径 */
    @Excel(name = "身份证反面图片路径")
    private String idCardBack;

    /** 实名认证时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实名认证时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date verificationTime;

    /** 审核状态 (0: 待审核, 1: 审核通过, 2: 审核不通过) */
    @Excel(name = "审核状态 (0: 待审核, 1: 审核通过, 2: 审核不通过)")
    private Integer status;

    /** 审核账号 */
    @Excel(name = "审核账号")
    private String reviewerAccount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setIdCardNumber(String idCardNumber) 
    {
        this.idCardNumber = idCardNumber;
    }

    public String getIdCardNumber() 
    {
        return idCardNumber;
    }

    public void setIdCardFront(String idCardFront) 
    {
        this.idCardFront = idCardFront;
    }

    public String getIdCardFront() 
    {
        return idCardFront;
    }

    public void setIdCardBack(String idCardBack) 
    {
        this.idCardBack = idCardBack;
    }

    public String getIdCardBack() 
    {
        return idCardBack;
    }

    public void setVerificationTime(Date verificationTime) 
    {
        this.verificationTime = verificationTime;
    }

    public Date getVerificationTime() 
    {
        return verificationTime;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setReviewerAccount(String reviewerAccount) 
    {
        this.reviewerAccount = reviewerAccount;
    }

    public String getReviewerAccount() 
    {
        return reviewerAccount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("memberId", getMemberId())
            .append("name", getName())
            .append("idCardNumber", getIdCardNumber())
            .append("idCardFront", getIdCardFront())
            .append("idCardBack", getIdCardBack())
            .append("verificationTime", getVerificationTime())
            .append("status", getStatus())
            .append("reviewerAccount", getReviewerAccount())
            .toString();
    }
}
