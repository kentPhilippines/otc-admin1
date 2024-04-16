package com.ruoyi.system.service;


import com.ruoyi.common.core.domain.entity.TrxExchangeInfo;
import com.ruoyi.system.domain.MonitorAddressAccount;
import com.ruoyi.system.domain.TrxExchange;

import java.util.List;

/**
 * trx兑能量记录Service接口
 * 
 * @author dorion
 * @date 2024-04-13
 */
public interface ITrxExchangeInfoService 
{
    /**
     * 查询trx兑能量记录
     * 
     * @param idTrxExchangeInfo trx兑能量记录主键
     * @return trx兑能量记录
     */
    public TrxExchangeInfo selectTrxExchangeInfoByIdTrxExchangeInfo(Long idTrxExchangeInfo);

    /**
     * 查询trx兑能量记录列表
     * 
     * @param trxExchangeInfo trx兑能量记录
     * @return trx兑能量记录集合
     */
    public List<TrxExchangeInfo> selectTrxExchangeInfoList(TrxExchangeInfo trxExchangeInfo);

    /**
     * 新增trx兑能量记录
     * 
     * @param trxExchangeInfo trx兑能量记录
     * @return 结果
     */
    public int insertTrxExchangeInfo(TrxExchangeInfo trxExchangeInfo);

    /**
     * 修改trx兑能量记录
     * 
     * @param trxExchangeInfo trx兑能量记录
     * @return 结果
     */
    public int updateTrxExchangeInfo(TrxExchangeInfo trxExchangeInfo);

    /**
     * 批量删除trx兑能量记录
     * 
     * @param idTrxExchangeInfos 需要删除的trx兑能量记录主键集合
     * @return 结果
     */
    public int deleteTrxExchangeInfoByIdTrxExchangeInfos(String idTrxExchangeInfos);

    /**
     * 删除trx兑能量记录信息
     * 
     * @param idTrxExchangeInfo trx兑能量记录主键
     * @return 结果
     */
    public int deleteTrxExchangeInfoByIdTrxExchangeInfo(Long idTrxExchangeInfo);

    int delegate(TrxExchange trxExchange,Boolean isTenant) throws Exception;

    void doMonitorTrxTransferByMonitorAddressInfo(MonitorAddressAccount monitorAddressAccount);

    void doUndelegateEnergyByTrxExchangeInfo(TrxExchangeInfo trxExchangeInfo);
}
