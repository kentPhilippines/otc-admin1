package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.TgPremiumOrderInfo;

import java.util.List;


/**
 * TG会员充值Mapper接口
 * 
 * @author dorion
 * @date 2024-05-04
 */
public interface TgPremiumOrderInfoMapper 
{
    /**
     * 查询TG会员充值
     * 
     * @param idTgPremiumOrderInfo TG会员充值主键
     * @return TG会员充值
     */
    public TgPremiumOrderInfo selectTgPremiumOrderInfoByIdTgPremiumOrderInfo(Long idTgPremiumOrderInfo);

    /**
     * 查询TG会员充值列表
     * 
     * @param tgPremiumOrderInfo TG会员充值
     * @return TG会员充值集合
     */
    public List<TgPremiumOrderInfo> selectTgPremiumOrderInfoList(TgPremiumOrderInfo tgPremiumOrderInfo);

    /**
     * 新增TG会员充值
     * 
     * @param tgPremiumOrderInfo TG会员充值
     * @return 结果
     */
    public int insertTgPremiumOrderInfo(TgPremiumOrderInfo tgPremiumOrderInfo);

    /**
     * 修改TG会员充值
     * 
     * @param tgPremiumOrderInfo TG会员充值
     * @return 结果
     */
    public int updateTgPremiumOrderInfo(TgPremiumOrderInfo tgPremiumOrderInfo);

    /**
     * 删除TG会员充值
     * 
     * @param idTgPremiumOrderInfo TG会员充值主键
     * @return 结果
     */
    public int deleteTgPremiumOrderInfoByIdTgPremiumOrderInfo(Long idTgPremiumOrderInfo);

    /**
     * 批量删除TG会员充值
     * 
     * @param idTgPremiumOrderInfos 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTgPremiumOrderInfoByIdTgPremiumOrderInfos(String[] idTgPremiumOrderInfos);
}
