package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MedRealNameVerificationMapper;
import com.ruoyi.system.domain.MedRealNameVerification;
import com.ruoyi.system.service.IMedRealNameVerificationService;
import com.ruoyi.common.core.text.Convert;

/**
 * 实名审核Service业务层处理
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
@Service
public class MedRealNameVerificationServiceImpl implements IMedRealNameVerificationService 
{
    @Autowired
    private MedRealNameVerificationMapper medRealNameVerificationMapper;

    /**
     * 查询实名审核
     * 
     * @param id 实名审核主键
     * @return 实名审核
     */
    @Override
    public MedRealNameVerification selectMedRealNameVerificationById(Long id)
    {
        return medRealNameVerificationMapper.selectMedRealNameVerificationById(id);
    }

    /**
     * 查询实名审核列表
     * 
     * @param medRealNameVerification 实名审核
     * @return 实名审核
     */
    @Override
    public List<MedRealNameVerification> selectMedRealNameVerificationList(MedRealNameVerification medRealNameVerification)
    {
        return medRealNameVerificationMapper.selectMedRealNameVerificationList(medRealNameVerification);
    }

    /**
     * 新增实名审核
     * 
     * @param medRealNameVerification 实名审核
     * @return 结果
     */
    @Override
    public int insertMedRealNameVerification(MedRealNameVerification medRealNameVerification)
    {
        return medRealNameVerificationMapper.insertMedRealNameVerification(medRealNameVerification);
    }

    /**
     * 修改实名审核
     * 
     * @param medRealNameVerification 实名审核
     * @return 结果
     */
    @Override
    public int updateMedRealNameVerification(MedRealNameVerification medRealNameVerification)
    {
        return medRealNameVerificationMapper.updateMedRealNameVerification(medRealNameVerification);
    }

    /**
     * 批量删除实名审核
     * 
     * @param ids 需要删除的实名审核主键
     * @return 结果
     */
    @Override
    public int deleteMedRealNameVerificationByIds(String ids)
    {
        return medRealNameVerificationMapper.deleteMedRealNameVerificationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除实名审核信息
     * 
     * @param id 实名审核主键
     * @return 结果
     */
    @Override
    public int deleteMedRealNameVerificationById(Long id)
    {
        return medRealNameVerificationMapper.deleteMedRealNameVerificationById(id);
    }
}
