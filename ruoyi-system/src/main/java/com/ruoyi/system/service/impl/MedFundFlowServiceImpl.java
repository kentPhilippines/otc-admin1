package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MedFundFlowMapper;
import com.ruoyi.system.domain.MedFundFlow;
import com.ruoyi.system.service.IMedFundFlowService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户资金流水Service业务层处理
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
@Service
public class MedFundFlowServiceImpl implements IMedFundFlowService 
{
    @Autowired
    private MedFundFlowMapper medFundFlowMapper;

    /**
     * 查询用户资金流水
     * 
     * @param id 用户资金流水主键
     * @return 用户资金流水
     */
    @Override
    public MedFundFlow selectMedFundFlowById(Long id)
    {
        return medFundFlowMapper.selectMedFundFlowById(id);
    }

    /**
     * 查询用户资金流水列表
     * 
     * @param medFundFlow 用户资金流水
     * @return 用户资金流水
     */
    @Override
    public List<MedFundFlow> selectMedFundFlowList(MedFundFlow medFundFlow)
    {
        return medFundFlowMapper.selectMedFundFlowList(medFundFlow);
    }

    /**
     * 新增用户资金流水
     * 
     * @param medFundFlow 用户资金流水
     * @return 结果
     */
    @Override
    public int insertMedFundFlow(MedFundFlow medFundFlow)
    {
        return medFundFlowMapper.insertMedFundFlow(medFundFlow);
    }

    /**
     * 修改用户资金流水
     * 
     * @param medFundFlow 用户资金流水
     * @return 结果
     */
    @Override
    public int updateMedFundFlow(MedFundFlow medFundFlow)
    {
        return medFundFlowMapper.updateMedFundFlow(medFundFlow);
    }

    /**
     * 批量删除用户资金流水
     * 
     * @param ids 需要删除的用户资金流水主键
     * @return 结果
     */
    @Override
    public int deleteMedFundFlowByIds(String ids)
    {
        return medFundFlowMapper.deleteMedFundFlowByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户资金流水信息
     * 
     * @param id 用户资金流水主键
     * @return 结果
     */
    @Override
    public int deleteMedFundFlowById(Long id)
    {
        return medFundFlowMapper.deleteMedFundFlowById(id);
    }
}
