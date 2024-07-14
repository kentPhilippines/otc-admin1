package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.TgMessageTask;
import com.ruoyi.system.domain.TgMessageInfoTask;

import java.util.List;


/**
 * TG消息任务管理Mapper接口
 * 
 * @author dorion
 * @date 2024-07-14
 */
public interface TgMessageTaskMapper 
{
    /**
     * 查询TG消息任务管理
     * 
     * @param idTgMessageTask TG消息任务管理主键
     * @return TG消息任务管理
     */
    public TgMessageTask selectTgMessageTaskByIdTgMessageTask(Long idTgMessageTask);

    /**
     * 查询TG消息任务管理列表
     * 
     * @param tgMessageTask TG消息任务管理
     * @return TG消息任务管理集合
     */
    public List<TgMessageTask> selectTgMessageTaskList(TgMessageTask tgMessageTask);

    /**
     * 新增TG消息任务管理
     * 
     * @param tgMessageTask TG消息任务管理
     * @return 结果
     */
    public int insertTgMessageTask(TgMessageTask tgMessageTask);

    /**
     * 修改TG消息任务管理
     * 
     * @param tgMessageTask TG消息任务管理
     * @return 结果
     */
    public int updateTgMessageTask(TgMessageTask tgMessageTask);

    /**
     * 删除TG消息任务管理
     * 
     * @param idTgMessageTask TG消息任务管理主键
     * @return 结果
     */
    public int deleteTgMessageTaskByIdTgMessageTask(Long idTgMessageTask);

    /**
     * 批量删除TG消息任务管理
     * 
     * @param idTgMessageTasks 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTgMessageTaskByIdTgMessageTasks(String[] idTgMessageTasks);

    List<TgMessageInfoTask> selectTgMessageTaskAndInfoList(TgMessageTask tgMessageTask);
}
