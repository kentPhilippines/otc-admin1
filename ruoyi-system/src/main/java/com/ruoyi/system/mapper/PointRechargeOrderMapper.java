package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.PointRechargeOrder;

import java.util.List;


/**
 * 群发充值管理Mapper接口
 * 
 * @author dorion
 * @date 2024-07-21
 */
public interface PointRechargeOrderMapper 
{
    /**
     * 查询群发充值管理
     * 
     * @param idPointRechargeOrder 群发充值管理主键
     * @return 群发充值管理
     */
    public PointRechargeOrder selectPointRechargeOrderByIdPointRechargeOrder(Long idPointRechargeOrder);

    /**
     * 查询群发充值管理列表
     * 
     * @param pointRechargeOrder 群发充值管理
     * @return 群发充值管理集合
     */
    public List<PointRechargeOrder> selectPointRechargeOrderList(PointRechargeOrder pointRechargeOrder);

    /**
     * 新增群发充值管理
     * 
     * @param pointRechargeOrder 群发充值管理
     * @return 结果
     */
    public int insertPointRechargeOrder(PointRechargeOrder pointRechargeOrder);

    /**
     * 修改群发充值管理
     * 
     * @param pointRechargeOrder 群发充值管理
     * @return 结果
     */
    public int updatePointRechargeOrder(PointRechargeOrder pointRechargeOrder);

    /**
     * 删除群发充值管理
     * 
     * @param idPointRechargeOrder 群发充值管理主键
     * @return 结果
     */
    public int deletePointRechargeOrderByIdPointRechargeOrder(Long idPointRechargeOrder);

    /**
     * 批量删除群发充值管理
     * 
     * @param idPointRechargeOrders 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePointRechargeOrderByIdPointRechargeOrders(String[] idPointRechargeOrders);
}
