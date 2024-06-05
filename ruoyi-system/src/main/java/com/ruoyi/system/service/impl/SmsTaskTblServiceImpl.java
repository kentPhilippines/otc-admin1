package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SmsTaskTbl;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.mapper.SmsTaskTblMapper;
import com.ruoyi.system.service.ISmsTaskTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * WS短信任务配置Service业务层处理
 * 
 * @author dorion
 * @date 2024-06-01
 */
@Service
public class SmsTaskTblServiceImpl implements ISmsTaskTblService 
{
    @Autowired
    private SmsTaskTblMapper smsTaskTblMapper;

    /**
     * 查询WS短信任务配置
     * 
     * @param idSmsTask WS短信任务配置主键
     * @return WS短信任务配置
     */
    @Override
    public SmsTaskTbl selectSmsTaskTblByIdSmsTask(Long idSmsTask)
    {
        return smsTaskTblMapper.selectSmsTaskTblByIdSmsTask(idSmsTask);
    }

    /**
     * 查询WS短信任务配置列表
     * 
     * @param smsTaskTbl WS短信任务配置
     * @return WS短信任务配置
     */
    @Override
    public List<SmsTaskTbl> selectSmsTaskTblList(SmsTaskTbl smsTaskTbl)
    {
        return smsTaskTblMapper.selectSmsTaskTblList(smsTaskTbl);
    }

    /**
     * 新增WS短信任务配置
     * 
     * @param smsTaskTbl WS短信任务配置
     * @return 结果
     */
    @Override
    public int insertSmsTaskTbl(SmsTaskTbl smsTaskTbl)
    {
        smsTaskTbl.setCreateTime(DateUtils.getNowDate());
        return smsTaskTblMapper.insertSmsTaskTbl(smsTaskTbl);
    }

    /**
     * 修改WS短信任务配置
     * 
     * @param smsTaskTbl WS短信任务配置
     * @return 结果
     */
    @Override
    public int updateSmsTaskTbl(SmsTaskTbl smsTaskTbl)
    {
        smsTaskTbl.setUpdateTime(DateUtils.getNowDate());
        return smsTaskTblMapper.updateSmsTaskTbl(smsTaskTbl);
    }

    /**
     * 批量删除WS短信任务配置
     * 
     * @param idSmsTasks 需要删除的WS短信任务配置主键
     * @return 结果
     */
    @Override
    public int deleteSmsTaskTblByIdSmsTasks(String idSmsTasks)
    {
        return smsTaskTblMapper.deleteSmsTaskTblByIdSmsTasks(Convert.toStrArray(idSmsTasks));
    }

    /**
     * 删除WS短信任务配置信息
     * 
     * @param idSmsTask WS短信任务配置主键
     * @return 结果
     */
    @Override
    public int deleteSmsTaskTblByIdSmsTask(Long idSmsTask)
    {
        return smsTaskTblMapper.deleteSmsTaskTblByIdSmsTask(idSmsTask);
    }
}
