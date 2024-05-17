package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;


/**
 * trx兑能量记录对象 trx_exchange_info
 * 
 * @author dorion
 * @date 2024-04-13
 */
@Data
public class TrxExchange extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 转出账户 */
    private String fromAddress;

    /** 实际出账账户 */
    private String accountAddress;

    private String monitorAddress;

    private String resourceCode;

    private Long transferNumber;

    private Long lockNum;

    private Long price;





}
