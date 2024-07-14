package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.TgMessageTask;

import java.util.List;


/**
 * TG消息任务管理Service接口
 * 
 * @author dorion
 * @date 2024-07-14
 */
public interface ITgMessageTaskService 
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
     * 批量删除TG消息任务管理
     * 
     * @param idTgMessageTasks 需要删除的TG消息任务管理主键集合
     * @return 结果
     */
    public int deleteTgMessageTaskByIdTgMessageTasks(String idTgMessageTasks);

    /**
     * 删除TG消息任务管理信息
     * 
     * @param idTgMessageTask TG消息任务管理主键
     * @return 结果
     */
    public int deleteTgMessageTaskByIdTgMessageTask(Long idTgMessageTask);
}
