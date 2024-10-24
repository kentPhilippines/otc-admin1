package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.MedFundFlow;

/**
 * 用户资金流水Service接口
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
public interface IMedFundFlowService 
{
    /**
     * 查询用户资金流水
     * 
     * @param id 用户资金流水主键
     * @return 用户资金流水
     */
    public MedFundFlow selectMedFundFlowById(Long id);

    /**
     * 查询用户资金流水列表
     * 
     * @param medFundFlow 用户资金流水
     * @return 用户资金流水集合
     */
    public List<MedFundFlow> selectMedFundFlowList(MedFundFlow medFundFlow);

    /**
     * 新增用户资金流水
     * 
     * @param medFundFlow 用户资金流水
     * @return 结果
     */
    public int insertMedFundFlow(MedFundFlow medFundFlow);

    /**
     * 修改用户资金流水
     * 
     * @param medFundFlow 用户资金流水
     * @return 结果
     */
    public int updateMedFundFlow(MedFundFlow medFundFlow);

    /**
     * 批量删除用户资金流水
     * 
     * @param ids 需要删除的用户资金流水主键集合
     * @return 结果
     */
    public int deleteMedFundFlowByIds(String ids);

    /**
     * 删除用户资金流水信息
     * 
     * @param id 用户资金流水主键
     * @return 结果
     */
    public int deleteMedFundFlowById(Long id);
}
