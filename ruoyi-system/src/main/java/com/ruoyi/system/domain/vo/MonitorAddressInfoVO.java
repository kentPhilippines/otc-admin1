package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 监听账户入账对象 monitor_address_info
 *
 * @author dorion
 * @date 2024-04-14
 */
@Data
@Accessors(chain = true)
public class MonitorAddressInfoVO
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long idMonitorAddress;

    /** 业务类型 */
    @Excel(name = "业务类型")
    private String busiType;

    /** 地址别名 */
    @Excel(name = "地址别名")
    private String monitorAddressName;

    /** 监听地址 */
    @Excel(name = "监听地址")
    private String monitorAddress;

    @Excel(name = "TRX余额")
    private BigDecimal trxBalance;


    @Excel(name = "USDT余额")
    private BigDecimal usdtBalance;

    /** 出账地址 */
    @Excel(name = "出账地址")
    private String accountAddress;


    /** trx兑换单价 */
    @Excel(name = "trx兑换单价")
    private Long trxPrice;

    /** usdt兑换单价 */
    @Excel(name = "usdt兑换单价")
    private BigDecimal usdtPrice;

    /** trx或者usdt */
    @Excel(name = "trx或者usdt")
    private String monitorType;

    /** API_KEY */
    @Excel(name = "API_KEY")
    private String apiKey;

    /** 是否有效 */
    @Excel(name = "是否有效")
    private String isValid;

    /** 绑定时长 */
    @Excel(name = "绑定时长")
    private String bindPeriod;
    /** 备注 */
    @Excel(name = "备注")
    private String comment;

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



}
