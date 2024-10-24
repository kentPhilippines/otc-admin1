package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.MedRealNameVerification;

/**
 * 实名审核Mapper接口
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
public interface MedRealNameVerificationMapper 
{
    /**
     * 查询实名审核
     * 
     * @param id 实名审核主键
     * @return 实名审核
     */
    public MedRealNameVerification selectMedRealNameVerificationById(Long id);

    /**
     * 查询实名审核列表
     * 
     * @param medRealNameVerification 实名审核
     * @return 实名审核集合
     */
    public List<MedRealNameVerification> selectMedRealNameVerificationList(MedRealNameVerification medRealNameVerification);

    /**
     * 新增实名审核
     * 
     * @param medRealNameVerification 实名审核
     * @return 结果
     */
    public int insertMedRealNameVerification(MedRealNameVerification medRealNameVerification);

    /**
     * 修改实名审核
     * 
     * @param medRealNameVerification 实名审核
     * @return 结果
     */
    public int updateMedRealNameVerification(MedRealNameVerification medRealNameVerification);

    /**
     * 删除实名审核
     * 
     * @param id 实名审核主键
     * @return 结果
     */
    public int deleteMedRealNameVerificationById(Long id);

    /**
     * 批量删除实名审核
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMedRealNameVerificationByIds(String[] ids);
}
