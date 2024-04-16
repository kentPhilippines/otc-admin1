package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * trx兑能量记录对象 trx_exchange_info
 * 
 * @author dorion
 * @date 2024-04-13
 */

public class TrxExchange extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 转出账户 */
    private String fromAddress;

    /** 实际出账账户 */
    private String accountAddress;

    private Long transferNumber;

    private Long lockNum;

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public Long getTransferNumber() {
        return transferNumber;
    }

    public void setTransferNumber(Long transferNumber) {
        this.transferNumber = transferNumber;
    }

    public Long getLockNum() {
        return lockNum;
    }

    public void setLockNum(Long lockNum) {
        this.lockNum = lockNum;
    }
}
