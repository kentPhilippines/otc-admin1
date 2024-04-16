package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.FreezeBalanceInfo;

import java.util.List;


/**
 * 抵押流水记录Mapper接口
 * 
 * @author dorion
 * @date 2024-04-13
 */
public interface FreezeBalanceInfoMapper 
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
    public int insertFreezeBalanceInfo(FreezeBalanceInfo freezeBalanceInfo);

    /**
     * 修改抵押流水记录
     * 
     * @param freezeBalanceInfo 抵押流水记录
     * @return 结果
     */
    public int updateFreezeBalanceInfo(FreezeBalanceInfo freezeBalanceInfo);

    /**
     * 删除抵押流水记录
     * 
     * @param idFreezeBalanceInfo 抵押流水记录主键
     * @return 结果
     */
    public int deleteFreezeBalanceInfoByIdFreezeBalanceInfo(Long idFreezeBalanceInfo);

    /**
     * 批量删除抵押流水记录
     * 
     * @param idFreezeBalanceInfos 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFreezeBalanceInfoByIdFreezeBalanceInfos(String[] idFreezeBalanceInfos);
}
