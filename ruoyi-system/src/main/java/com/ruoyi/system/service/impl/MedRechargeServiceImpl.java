package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MedRechargeMapper;
import com.ruoyi.system.domain.MedRecharge;
import com.ruoyi.system.service.IMedRechargeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户充值Service业务层处理
 * 
 * @author kkkkkk
 * @date 2024-10-23
 */
@Service
public class MedRechargeServiceImpl implements IMedRechargeService 
{
    @Autowired
    private MedRechargeMapper medRechargeMapper;

    /**
     * 查询用户充值
     * 
     * @param id 用户充值主键
     * @return 用户充值
     */
    @Override
    public MedRecharge selectMedRechargeById(Long id)
    {
        return medRechargeMapper.selectMedRechargeById(id);
    }

    /**
     * 查询用户充值列表
     * 
     * @param medRecharge 用户充值
     * @return 用户充值
     */
    @Override
    public List<MedRecharge> selectMedRechargeList(MedRecharge medRecharge)
    {
        return medRechargeMapper.selectMedRechargeList(medRecharge);
    }

    /**
     * 新增用户充值
     * 
     * @param medRecharge 用户充值
     * @return 结果
     */
    @Override
    public int insertMedRecharge(MedRecharge medRecharge)
    {
        return medRechargeMapper.insertMedRecharge(medRecharge);
    }

    /**
     * 修改用户充值
     * 
     * @param medRecharge 用户充值
     * @return 结果
     */
    @Override
    public int updateMedRecharge(MedRecharge medRecharge)
    {
        return medRechargeMapper.updateMedRecharge(medRecharge);
    }

    /**
     * 批量删除用户充值
     * 
     * @param ids 需要删除的用户充值主键
     * @return 结果
     */
    @Override
    public int deleteMedRechargeByIds(String ids)
    {
        return medRechargeMapper.deleteMedRechargeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户充值信息
     * 
     * @param id 用户充值主键
     * @return 结果
     */
    @Override
    public int deleteMedRechargeById(Long id)
    {
        return medRechargeMapper.deleteMedRechargeById(id);
    }
}
