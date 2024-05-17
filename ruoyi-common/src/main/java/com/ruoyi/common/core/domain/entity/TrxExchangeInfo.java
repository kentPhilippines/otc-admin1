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

   /* @Excel(name = "监听账户别名")
    private String monitorAddressName;*/

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

    /** 转入单位 */
    @Excel(name = "转入单位")
    private String trxAmountUnit;

    /** 兑换支出能量 */
    @Excel(name = "兑换支出能量")
    private Long delegateAmountTrx;

    /** 资源类型 */
    @Excel(name = "资源类型")
    private String resourceCode;


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


}
