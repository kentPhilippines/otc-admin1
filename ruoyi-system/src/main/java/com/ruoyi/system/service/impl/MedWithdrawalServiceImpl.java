package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MedWithdrawalMapper;
import com.ruoyi.system.domain.MedWithdrawal;
import com.ruoyi.system.service.IMedWithdrawalService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户提现订单Service业务层处理
 * 
 * @author kkkkkk
 * @date 2024-10-23
 */
@Service
public class MedWithdrawalServiceImpl implements IMedWithdrawalService 
{
    @Autowired
    private MedWithdrawalMapper medWithdrawalMapper;

    /**
     * 查询用户提现订单
     * 
     * @param id 用户提现订单主键
     * @return 用户提现订单
     */
    @Override
    public MedWithdrawal selectMedWithdrawalById(Long id)
    {
        return medWithdrawalMapper.selectMedWithdrawalById(id);
    }

    /**
     * 查询用户提现订单列表
     * 
     * @param medWithdrawal 用户提现订单
     * @return 用户提现订单
     */
    @Override
    public List<MedWithdrawal> selectMedWithdrawalList(MedWithdrawal medWithdrawal)
    {
        return medWithdrawalMapper.selectMedWithdrawalList(medWithdrawal);
    }

    /**
     * 新增用户提现订单
     * 
     * @param medWithdrawal 用户提现订单
     * @return 结果
     */
    @Override
    public int insertMedWithdrawal(MedWithdrawal medWithdrawal)
    {
        return medWithdrawalMapper.insertMedWithdrawal(medWithdrawal);
    }

    /**
     * 修改用户提现订单
     * 
     * @param medWithdrawal 用户提现订单
     * @return 结果
     */
    @Override
    public int updateMedWithdrawal(MedWithdrawal medWithdrawal)
    {
        return medWithdrawalMapper.updateMedWithdrawal(medWithdrawal);
    }

    /**
     * 批量删除用户提现订单
     * 
     * @param ids 需要删除的用户提现订单主键
     * @return 结果
     */
    @Override
    public int deleteMedWithdrawalByIds(String ids)
    {
        return medWithdrawalMapper.deleteMedWithdrawalByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户提现订单信息
     * 
     * @param id 用户提现订单主键
     * @return 结果
     */
    @Override
    public int deleteMedWithdrawalById(Long id)
    {
        return medWithdrawalMapper.deleteMedWithdrawalById(id);
    }
}
