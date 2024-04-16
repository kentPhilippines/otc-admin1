package com.ruoyi.system.service;


import com.ruoyi.common.core.domain.entity.ErrorLog;

import java.util.List;

/**
 * 错误日志Service接口
 * 
 * @author dorion
 * @date 2024-04-13
 */
public interface IErrorLogService 
{
    /**
     * 查询错误日志
     * 
     * @param idErrorLog 错误日志主键
     * @return 错误日志
     */
    public ErrorLog selectErrorLogByIdErrorLog(Long idErrorLog);

    /**
     * 查询错误日志列表
     * 
     * @param errorLog 错误日志
     * @return 错误日志集合
     */
    public List<ErrorLog> selectErrorLogList(ErrorLog errorLog);

    /**
     * 新增错误日志
     * 
     * @param errorLog 错误日志
     * @return 结果
     */
    public int insertErrorLog(ErrorLog errorLog);

    /**
     * 修改错误日志
     * 
     * @param errorLog 错误日志
     * @return 结果
     */
    public int updateErrorLog(ErrorLog errorLog);

    /**
     * 批量删除错误日志
     * 
     * @param idErrorLogs 需要删除的错误日志主键集合
     * @return 结果
     */
    public int deleteErrorLogByIdErrorLogs(String idErrorLogs);

    /**
     * 删除错误日志信息
     * 
     * @param idErrorLog 错误日志主键
     * @return 结果
     */
    public int deleteErrorLogByIdErrorLog(Long idErrorLog);
}
