package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.mapper.ErrorLogMapper;
import com.ruoyi.system.service.IErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 错误日志Service业务层处理
 * 
 * @author dorion
 * @date 2024-04-13
 */
@Service
public class ErrorLogServiceImpl implements IErrorLogService 
{
    @Autowired
    private ErrorLogMapper errorLogMapper;

    /**
     * 查询错误日志
     * 
     * @param idErrorLog 错误日志主键
     * @return 错误日志
     */
    @Override
    public ErrorLog selectErrorLogByIdErrorLog(Long idErrorLog)
    {
        return errorLogMapper.selectErrorLogByIdErrorLog(idErrorLog);
    }

    /**
     * 查询错误日志列表
     * 
     * @param errorLog 错误日志
     * @return 错误日志
     */
    @Override
    public List<ErrorLog> selectErrorLogList(ErrorLog errorLog)
    {
        return errorLogMapper.selectErrorLogList(errorLog);
    }

    /**
     * 新增错误日志
     * 
     * @param errorLog 错误日志
     * @return 结果
     */
    @Override
    public int insertErrorLog(ErrorLog errorLog)
    {
        String userName = ShiroUtils.getLoginName();
        errorLog.setFcu(userName);
        errorLog.setLcu(userName);
        return errorLogMapper.insertErrorLog(errorLog);
    }

    /**
     * 修改错误日志
     * 
     * @param errorLog 错误日志
     * @return 结果
     */
    @Override
    public int updateErrorLog(ErrorLog errorLog)
    {
        String userName = ShiroUtils.getLoginName();
        errorLog.setLcu(userName);
        return errorLogMapper.updateErrorLog(errorLog);
    }

    /**
     * 批量删除错误日志
     * 
     * @param idErrorLogs 需要删除的错误日志主键
     * @return 结果
     */
    @Override
    public int deleteErrorLogByIdErrorLogs(String idErrorLogs)
    {
        return errorLogMapper.deleteErrorLogByIdErrorLogs(Convert.toStrArray(idErrorLogs));
    }

    /**
     * 删除错误日志信息
     * 
     * @param idErrorLog 错误日志主键
     * @return 结果
     */
    @Override
    public int deleteErrorLogByIdErrorLog(Long idErrorLog)
    {
        return errorLogMapper.deleteErrorLogByIdErrorLog(idErrorLog);
    }
}
