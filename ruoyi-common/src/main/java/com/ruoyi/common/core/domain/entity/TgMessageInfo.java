package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * TG消息管理对象 tg_message_info
 *
 * @author dorion
 * @date 2024-07-12
 */
public class TgMessageInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idTgMessageInfo;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String messageInfo;

    /** 消息类型 */
    @Excel(name = "消息类型")
    private Long messageType;


    @Excel(name = "消息别名")
    private String messageAbbrName;



    public String getMessageAbbrName() {
        return messageAbbrName;
    }

    public void setMessageAbbrName(String messageAbbrName) {
        this.messageAbbrName = messageAbbrName;
    }

    public void setIdTgMessageInfo(Long idTgMessageInfo)
    {
        this.idTgMessageInfo = idTgMessageInfo;
    }

    public Long getIdTgMessageInfo()
    {
        return idTgMessageInfo;
    }
    public void setMessageInfo(String messageInfo)
    {
        this.messageInfo = messageInfo;
    }

    public String getMessageInfo()
    {
        return messageInfo;
    }
    public void setMessageType(Long messageType)
    {
        this.messageType = messageType;
    }

    public Long getMessageType()
    {
        return messageType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("idTgMessageInfo", getIdTgMessageInfo())
                .append("messageInfo", getMessageInfo())
                .append("messageType", getMessageType())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
