package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Sensitive;
import com.ruoyi.common.enums.DesensitizedType;
import lombok.Data;

import java.util.Date;

@Data
public class TrxExchangeInfoVO {

    private static final long serialVersionUID = 1L;

    /** trx进账表 */
    private Long idTrxExchangeInfo;

    /** 转出账户 */
    @Excel(name = "付款账户")
    private String fromAddress;

    /**
     *
     */
    @Excel(name = "收款账户别名")
    private String monitorAddressName;

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
    @Excel(name = "TRX订单")
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
    @Excel(name = "兑换支出TRX")
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
    @Excel(name = "资源状态")
    private String delegateStatus;
    /** 笔数计算规则 */
    @Excel(name = "笔数计算规则")
    private String calcRule;

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