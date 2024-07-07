package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.TrxExchangeFail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 发送失败记录Mapper接口
 * 
 * @author dorion
 * @date 2024-07-07
 */
public interface TrxExchangeFailMapper 
{
    /**
     * 查询发送失败记录
     * 
     * @param idTrxExchangeFail 发送失败记录主键
     * @return 发送失败记录
     */
    public TrxExchangeFail selectTrxExchangeFailByIdTrxExchangeFail(Long idTrxExchangeFail);

    /**
     * 查询发送失败记录列表
     * 
     * @param trxExchangeFail 发送失败记录
     * @return 发送失败记录集合
     */
    public List<TrxExchangeFail> selectTrxExchangeFailList(TrxExchangeFail trxExchangeFail);

    /**
     * 新增发送失败记录
     * 
     * @param trxExchangeFail 发送失败记录
     * @return 结果
     */
    public int insertTrxExchangeFail(TrxExchangeFail trxExchangeFail);

    /**
     * 修改发送失败记录
     * 
     * @param trxExchangeFail 发送失败记录
     * @return 结果
     */
    public int updateTrxExchangeFail(TrxExchangeFail trxExchangeFail);

    /**
     * 删除发送失败记录
     * 
     * @param idTrxExchangeFail 发送失败记录主键
     * @return 结果
     */
    public int deleteTrxExchangeFailByIdTrxExchangeFail(Long idTrxExchangeFail);

    /**
     * 批量删除发送失败记录
     * 
     * @param idTrxExchangeFails 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTrxExchangeFailByIdTrxExchangeFails(String[] idTrxExchangeFails);

    public List<TrxExchangeFail> selectTrxExchangeFailListByIdList(@Param("idsList") List<String> idsList);


}
