package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ForwardCounter;
import com.ruoyi.common.utils.LogUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.bot.TgLongPollingBot;
import com.ruoyi.system.bot.utils.SendContent;
import com.ruoyi.system.mapper.ErrorLogMapper;
import com.ruoyi.system.service.IErrorLogService;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 错误日志Service业务层处理
 *
 * @author dorion
 * @date 2024-04-13
 */
@Service
@Slf4j
public class ErrorLogServiceImpl implements IErrorLogService {

    @Autowired(required = false)
    private TgLongPollingBot tgLongPollingBot;

    @Autowired
    private ErrorLogMapper errorLogMapper;

    @Autowired
    private SendContent sendContent;
    @Autowired
    private ISysConfigService configService;

    /**
     * 查询错误日志
     *
     * @param idErrorLog 错误日志主键
     * @return 错误日志
     */
    @Override
    public ErrorLog selectErrorLogByIdErrorLog(Long idErrorLog) {
        return errorLogMapper.selectErrorLogByIdErrorLog(idErrorLog);
    }

    /**
     * 查询错误日志列表
     *
     * @param errorLog 错误日志
     * @return 错误日志
     */
    @Override
    public List<ErrorLog> selectErrorLogList(ErrorLog errorLog) {
        return errorLogMapper.selectErrorLogList(errorLog);
    }

    /**
     * 新增错误日志
     *
     * @param errorLog 错误日志
     * @return 结果
     */
    @Override
    @Async
    public int insertErrorLog(ErrorLog errorLog){
        String sysErrorLogTemplate = configService.selectConfigByKey("sys.error.log.template");
        String sysErrorLogChatId = configService.selectConfigByKey("sys.error.log.chatid");
        if (tgLongPollingBot != null && StringUtils.isNotEmpty(sysErrorLogChatId) && StringUtils.isNotEmpty(sysErrorLogTemplate)) {
            Map<String, Object> arguments = new HashMap<>();
            arguments.put("errorCode", errorLog.getErrorCode());
            arguments.put("address", errorLog.getAddress());
            arguments.put("txTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            StrSubstitutor substitutor = new StrSubstitutor(arguments, "{", "}");
            String message = substitutor.replace(sysErrorLogTemplate);
            SendMessage sendMessage = sendContent.messageText(sysErrorLogChatId, message, ParseMode.HTML);
            try {
                tgLongPollingBot.execute(sendMessage);
            } catch (TelegramApiException e) {
                String exceptionString = LogUtils.doRecursiveReversePrintStackCause(e, 5, ForwardCounter.builder().count(0).build(), 5);
                log.error("TelegramApiException异常:{}", exceptionString);
            }
        }

        return errorLogMapper.insertErrorLog(errorLog);
    }

    /**
     * 修改错误日志
     *
     * @param errorLog 错误日志
     * @return 结果
     */
    @Override
    public int updateErrorLog(ErrorLog errorLog) {
        return errorLogMapper.updateErrorLog(errorLog);
    }

    /**
     * 批量删除错误日志
     *
     * @param idErrorLogs 需要删除的错误日志主键
     * @return 结果
     */
    @Override
    public int deleteErrorLogByIdErrorLogs(String idErrorLogs) {
        return errorLogMapper.deleteErrorLogByIdErrorLogs(Convert.toStrArray(idErrorLogs));
    }

    /**
     * 删除错误日志信息
     *
     * @param idErrorLog 错误日志主键
     * @return 结果
     */
    @Override
    public int deleteErrorLogByIdErrorLog(Long idErrorLog) {
        return errorLogMapper.deleteErrorLogByIdErrorLog(idErrorLog);
    }
}
