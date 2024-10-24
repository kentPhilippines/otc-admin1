package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.MedFundFlow;

/**
 * 用户资金流水Mapper接口
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
public interface MedFundFlowMapper 
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
     * 删除用户资金流水
     * 
     * @param id 用户资金流水主键
     * @return 结果
     */
    public int deleteMedFundFlowById(Long id);

    /**
     * 批量删除用户资金流水
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMedFundFlowByIds(String[] ids);
}
