package com.ruoyi.system.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.google.common.base.Preconditions;
import com.ruoyi.common.core.domain.entity.TgMessageTask;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.mapper.TgMessageTaskMapper;
import com.ruoyi.system.service.ITgMessageTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * TG消息任务管理Service业务层处理
 * 
 * @author dorion
 * @date 2024-07-14
 */
@Service
public class TgMessageTaskServiceImpl implements ITgMessageTaskService 
{
    @Autowired
    private TgMessageTaskMapper tgMessageTaskMapper;

    /**
     * 查询TG消息任务管理
     * 
     * @param idTgMessageTask TG消息任务管理主键
     * @return TG消息任务管理
     */
    @Override
    public TgMessageTask selectTgMessageTaskByIdTgMessageTask(Long idTgMessageTask)
    {
        return tgMessageTaskMapper.selectTgMessageTaskByIdTgMessageTask(idTgMessageTask);
    }

    /**
     * 查询TG消息任务管理列表
     * 
     * @param tgMessageTask TG消息任务管理
     * @return TG消息任务管理
     */
    @Override
    public List<TgMessageTask> selectTgMessageTaskList(TgMessageTask tgMessageTask)
    {
        return tgMessageTaskMapper.selectTgMessageTaskList(tgMessageTask);
    }

    /**
     * 新增TG消息任务管理
     * 
     * @param tgMessageTask TG消息任务管理
     * @return 结果
     */
    @Override
    public int insertTgMessageTask(TgMessageTask tgMessageTask)
    {
        Long executionStragy = tgMessageTask.getExecutionStragy();
        if (0 == executionStragy) {
            String intervalTime = tgMessageTask.getIntervalTime();
            Preconditions.checkNotNull(intervalTime, "周期执行时间间隔不能为空");
            checkIntervalTime(intervalTime);
            tgMessageTask.setNextRunTime(tgMessageTask.getBeginTime());
        } else {
            tgMessageTask.setIntervalTime(null);
        }

        String loginName = ShiroUtils.getSysUser().getLoginName();
        tgMessageTask.setCreateBy(loginName);
        tgMessageTask.setUpdateBy(loginName);
        tgMessageTask.setStatus(1L);
        return tgMessageTaskMapper.insertTgMessageTask(tgMessageTask);
    }


    private  void checkIntervalTime(String intervalTime) {
        int length = intervalTime.length();
        String intervalTimeNumber = intervalTime.substring(0, length - 1);
        boolean number = NumberUtil.isNumber(intervalTimeNumber);
        Preconditions.checkState(number, "时间间隔时间必须全部为数字");

        String intervalTimeUnit = intervalTime.substring(length - 1, length);
        boolean expression = intervalTimeUnit.equalsIgnoreCase("s")
                || intervalTimeUnit.equalsIgnoreCase("m")
                || intervalTimeUnit.equalsIgnoreCase("h") ;
        Preconditions.checkState(expression, "时间间隔单位只能为S、M、H");
    }

    /**
     * 修改TG消息任务管理
     * 
     * @param tgMessageTask TG消息任务管理
     * @return 结果
     */
    @Override
    public int updateTgMessageTask(TgMessageTask tgMessageTask)
    {
        Long executionStragy = tgMessageTask.getExecutionStragy();
        if (0 == executionStragy) {
            String intervalTime = tgMessageTask.getIntervalTime();
            checkIntervalTime(intervalTime);
            tgMessageTask.setNextRunTime(tgMessageTask.getBeginTime());
        } else {
            tgMessageTask.setIntervalTime(null);
            tgMessageTask.setNextRunTime(null);
        }
        String loginName = ShiroUtils.getSysUser().getLoginName();
        tgMessageTask.setUpdateBy(loginName);
        tgMessageTask.setUpdateTime(new Date());
        return tgMessageTaskMapper.updateTgMessageTask(tgMessageTask);
    }

    /**
     * 批量删除TG消息任务管理
     * 
     * @param idTgMessageTasks 需要删除的TG消息任务管理主键
     * @return 结果
     */
    @Override
    public int deleteTgMessageTaskByIdTgMessageTasks(String idTgMessageTasks)
    {
        return tgMessageTaskMapper.deleteTgMessageTaskByIdTgMessageTasks(Convert.toStrArray(idTgMessageTasks));
    }

    /**
     * 删除TG消息任务管理信息
     * 
     * @param idTgMessageTask TG消息任务管理主键
     * @return 结果
     */
    @Override
    public int deleteTgMessageTaskByIdTgMessageTask(Long idTgMessageTask)
    {
        return tgMessageTaskMapper.deleteTgMessageTaskByIdTgMessageTask(idTgMessageTask);
    }
}
