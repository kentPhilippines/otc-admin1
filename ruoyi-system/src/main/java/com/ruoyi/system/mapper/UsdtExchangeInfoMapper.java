package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.UsdtExchangeInfo;

import java.util.List;


/**
 * USDT交易明细Mapper接口
 * 
 * @author dorion
 * @date 2024-04-19
 */
public interface UsdtExchangeInfoMapper 
{
    /**
     * 查询USDT交易明细
     * 
     * @param idUsdtExchangeInfo USDT交易明细主键
     * @return USDT交易明细
     */
    public UsdtExchangeInfo selectUsdtExchangeInfoByIdUsdtExchangeInfo(Long idUsdtExchangeInfo);

    /**
     * 查询USDT交易明细列表
     * 
     * @param usdtExchangeInfo USDT交易明细
     * @return USDT交易明细集合
     */
    public List<UsdtExchangeInfo> selectUsdtExchangeInfoList(UsdtExchangeInfo usdtExchangeInfo);

    /**
     * 新增USDT交易明细
     * 
     * @param usdtExchangeInfo USDT交易明细
     * @return 结果
     */
    public int insertUsdtExchangeInfo(UsdtExchangeInfo usdtExchangeInfo);

    /**
     * 修改USDT交易明细
     * 
     * @param usdtExchangeInfo USDT交易明细
     * @return 结果
     */
    public int updateUsdtExchangeInfo(UsdtExchangeInfo usdtExchangeInfo);

    /**
     * 删除USDT交易明细
     * 
     * @param idUsdtExchangeInfo USDT交易明细主键
     * @return 结果
     */
    public int deleteUsdtExchangeInfoByIdUsdtExchangeInfo(Long idUsdtExchangeInfo);

    /**
     * 批量删除USDT交易明细
     * 
     * @param idUsdtExchangeInfos 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUsdtExchangeInfoByIdUsdtExchangeInfos(String[] idUsdtExchangeInfos);

    List<UsdtExchangeInfo> selectUsdtExchangeInfoAndMonitorNameList(UsdtExchangeInfo usdtExchangeInfo);
}