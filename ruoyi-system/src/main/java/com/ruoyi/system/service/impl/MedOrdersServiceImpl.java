package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MedOrdersMapper;
import com.ruoyi.system.domain.MedOrders;
import com.ruoyi.system.service.IMedOrdersService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户交易订单Service业务层处理
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
@Service
public class MedOrdersServiceImpl implements IMedOrdersService 
{
    @Autowired
    private MedOrdersMapper medOrdersMapper;

    /**
     * 查询用户交易订单
     * 
     * @param id 用户交易订单主键
     * @return 用户交易订单
     */
    @Override
    public MedOrders selectMedOrdersById(Long id)
    {
        return medOrdersMapper.selectMedOrdersById(id);
    }

    /**
     * 查询用户交易订单列表
     * 
     * @param medOrders 用户交易订单
     * @return 用户交易订单
     */
    @Override
    public List<MedOrders> selectMedOrdersList(MedOrders medOrders)
    {
        return medOrdersMapper.selectMedOrdersList(medOrders);
    }

    /**
     * 新增用户交易订单
     * 
     * @param medOrders 用户交易订单
     * @return 结果
     */
    @Override
    public int insertMedOrders(MedOrders medOrders)
    {
        return medOrdersMapper.insertMedOrders(medOrders);
    }

    /**
     * 修改用户交易订单
     * 
     * @param medOrders 用户交易订单
     * @return 结果
     */
    @Override
    public int updateMedOrders(MedOrders medOrders)
    {
        return medOrdersMapper.updateMedOrders(medOrders);
    }

    /**
     * 批量删除用户交易订单
     * 
     * @param ids 需要删除的用户交易订单主键
     * @return 结果
     */
    @Override
    public int deleteMedOrdersByIds(String ids)
    {
        return medOrdersMapper.deleteMedOrdersByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户交易订单信息
     * 
     * @param id 用户交易订单主键
     * @return 结果
     */
    @Override
    public int deleteMedOrdersById(Long id)
    {
        return medOrdersMapper.deleteMedOrdersById(id);
    }
}
