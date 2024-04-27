package com.ruoyi.system.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class TronInfoVO implements Serializable {
    private final static long serialVersionUID = 1L;
    /**
     * 能量池可购买金额
     */
    private String energyRemaining;;
    /**
     * usdt兑换trx实时汇率
     */
    private String usdt2TrxPrice;

    /**
     * 能量交易列表
     */
    private List<TransactionLogVO> transactionLogList;
}
