package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.MedRecharge;

/**
 * 用户充值Service接口
 * 
 * @author kkkkkk
 * @date 2024-10-23
 */
public interface IMedRechargeService 
{
    /**
     * 查询用户充值
     * 
     * @param id 用户充值主键
     * @return 用户充值
     */
    public MedRecharge selectMedRechargeById(Long id);

    /**
     * 查询用户充值列表
     * 
     * @param medRecharge 用户充值
     * @return 用户充值集合
     */
    public List<MedRecharge> selectMedRechargeList(MedRecharge medRecharge);

    /**
     * 新增用户充值
     * 
     * @param medRecharge 用户充值
     * @return 结果
     */
    public int insertMedRecharge(MedRecharge medRecharge);

    /**
     * 修改用户充值
     * 
     * @param medRecharge 用户充值
     * @return 结果
     */
    public int updateMedRecharge(MedRecharge medRecharge);

    /**
     * 批量删除用户充值
     * 
     * @param ids 需要删除的用户充值主键集合
     * @return 结果
     */
    public int deleteMedRechargeByIds(String ids);

    /**
     * 删除用户充值信息
     * 
     * @param id 用户充值主键
     * @return 结果
     */
    public int deleteMedRechargeById(Long id);
}
