package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.MedOrders;

/**
 * 用户交易订单Service接口
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
public interface IMedOrdersService 
{
    /**
     * 查询用户交易订单
     * 
     * @param id 用户交易订单主键
     * @return 用户交易订单
     */
    public MedOrders selectMedOrdersById(Long id);

    /**
     * 查询用户交易订单列表
     * 
     * @param medOrders 用户交易订单
     * @return 用户交易订单集合
     */
    public List<MedOrders> selectMedOrdersList(MedOrders medOrders);

    /**
     * 新增用户交易订单
     * 
     * @param medOrders 用户交易订单
     * @return 结果
     */
    public int insertMedOrders(MedOrders medOrders);

    /**
     * 修改用户交易订单
     * 
     * @param medOrders 用户交易订单
     * @return 结果
     */
    public int updateMedOrders(MedOrders medOrders);

    /**
     * 批量删除用户交易订单
     * 
     * @param ids 需要删除的用户交易订单主键集合
     * @return 结果
     */
    public int deleteMedOrdersByIds(String ids);

    /**
     * 删除用户交易订单信息
     * 
     * @param id 用户交易订单主键
     * @return 结果
     */
    public int deleteMedOrdersById(Long id);
}