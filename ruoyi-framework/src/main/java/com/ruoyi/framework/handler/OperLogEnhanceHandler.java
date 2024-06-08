package com.ruoyi.framework.handler;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysOperLog;

/**
 * 操作日志增强处理器
 * @author 潇湘振宇
 */
public interface OperLogEnhanceHandler {
	
	/**
	 * 获取JSON结果
	 * @param log
	 * @param operLog
	 * @param jsonResult
	 * @return
	 */
	default String getJsonResult(Log log, SysOperLog operLog, Object result) {
		return StringUtils.substring(JSONObject.toJSONString(result), 0, 2000);
	}
	
	/**
	 * 系统操作日志扩展
	 * @param operLog
	 */
	default void extension(SysOperLog operLog) {
	}

}
