package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SmsTaskTbl;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * WS短信任务配置Mapper接口
 * 
 * @author dorion
 * @date 2024-06-01
 */
public interface SmsTaskTblMapper 
{
    /**
     * 查询WS短信任务配置
     * 
     * @param idSmsTask WS短信任务配置主键
     * @return WS短信任务配置
     */
    public SmsTaskTbl selectSmsTaskTblByIdSmsTask(Long idSmsTask);

    /**
     * 查询WS短信任务配置列表
     * 
     * @param smsTaskTbl WS短信任务配置
     * @return WS短信任务配置集合
     */
    public List<SmsTaskTbl> selectSmsTaskTblList(SmsTaskTbl smsTaskTbl);

    /**
     * 新增WS短信任务配置
     * 
     * @param smsTaskTbl WS短信任务配置
     * @return 结果
     */
    public int insertSmsTaskTbl(SmsTaskTbl smsTaskTbl);

    /**
     * 修改WS短信任务配置
     * 
     * @param smsTaskTbl WS短信任务配置
     * @return 结果
     */
    public int updateSmsTaskTbl(SmsTaskTbl smsTaskTbl);

    /**
     * 删除WS短信任务配置
     * 
     * @param idSmsTask WS短信任务配置主键
     * @return 结果
     */
    public int deleteSmsTaskTblByIdSmsTask(Long idSmsTask);

    /**
     * 批量删除WS短信任务配置
     * 
     * @param idSmsTasks 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSmsTaskTblByIdSmsTasks(String[] idSmsTasks);

    List<SmsTaskTbl> selectSmsTaskTblListNotComplete();

    void updateSmsTaskTblByTaskId(SmsTaskTbl smsTaskTbl);

    List<SmsTaskTbl> selectSmsTaskTbl(@Param("idsList") List<String> idsList);
}
