package com.ruoyi.system.service;


import com.ruoyi.common.core.domain.entity.FreezeBalanceInfo;

import java.util.List;

/**
 * 抵押流水记录Service接口
 * 
 * @author dorion
 * @date 2024-04-13
 */
public interface IFreezeBalanceInfoService 
{
    /**
     * 查询抵押流水记录
     * 
     * @param idFreezeBalanceInfo 抵押流水记录主键
     * @return 抵押流水记录
     */
    public FreezeBalanceInfo selectFreezeBalanceInfoByIdFreezeBalanceInfo(Long idFreezeBalanceInfo);

    /**
     * 查询抵押流水记录列表
     * 
     * @param freezeBalanceInfo 抵押流水记录
     * @return 抵押流水记录集合
     */
    public List<FreezeBalanceInfo> selectFreezeBalanceInfoList(FreezeBalanceInfo freezeBalanceInfo);

    /**
     * 新增抵押流水记录
     * 
     * @param freezeBalanceInfo 抵押流水记录
     * @return 结果
     */
    public int insertFreezeBalanceInfo(FreezeBalanceInfo freezeBalanceInfo) throws Exception;

    /**
     * 修改抵押流水记录
     * 
     * @param freezeBalanceInfo 抵押流水记录
     * @return 结果
     */
    public int updateFreezeBalanceInfo(FreezeBalanceInfo freezeBalanceInfo);

    /**
     * 批量删除抵押流水记录
     * 
     * @param idFreezeBalanceInfos 需要删除的抵押流水记录主键集合
     * @return 结果
     */
    public int deleteFreezeBalanceInfoByIdFreezeBalanceInfos(String idFreezeBalanceInfos);

    /**
     * 删除抵押流水记录信息
     * 
     * @param idFreezeBalanceInfo 抵押流水记录主键
     * @return 结果
     */
    public int deleteFreezeBalanceInfoByIdFreezeBalanceInfo(Long idFreezeBalanceInfo);
}
