package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.PointRechargeOrder;

import java.util.List;


/**
 * 群发充值管理Service接口
 * 
 * @author dorion
 * @date 2024-07-21
 */
public interface IPointRechargeOrderService 
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
     * 批量删除群发充值管理
     * 
     * @param idPointRechargeOrders 需要删除的群发充值管理主键集合
     * @return 结果
     */
    public int deletePointRechargeOrderByIdPointRechargeOrders(String idPointRechargeOrders);

    /**
     * 删除群发充值管理信息
     * 
     * @param idPointRechargeOrder 群发充值管理主键
     * @return 结果
     */
    public int deletePointRechargeOrderByIdPointRechargeOrder(Long idPointRechargeOrder);

    Long rechargeOrder(PointRechargeOrder pointRechargeOrder);
}
