package com.ruoyi.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.domain.entity.SmsTaskTbl;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.api.IU02cxApi;
import com.ruoyi.system.api.entity.U02cx.AddTaskRequest;
import com.ruoyi.system.api.entity.U02cx.GetTokenRequest;
import com.ruoyi.system.domain.SmsChannelTbl;
import com.ruoyi.system.mapper.SmsChannelTblMapper;
import com.ruoyi.system.mapper.SmsTaskTblMapper;
import com.ruoyi.system.service.ISmsTaskTblService;
import com.ruoyi.system.util.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
    private static final Logger log = LoggerFactory.getLogger(SmsTaskTblServiceImpl.class);
    @Autowired
    private SmsTaskTblMapper smsTaskTblMapper;

    @Autowired
    private SmsChannelTblMapper smsChannelTblMapper;

    @Autowired
    private IU02cxApi u02cxApi;

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
        log.info("insertSmsTaskTbl:{}", smsTaskTbl.toString());
        SmsChannelTbl smsChannelTbl = smsChannelTblMapper.selectSmsChannelTblByIdSmsChannel(1L);

        GetTokenRequest getTokenRequest = new GetTokenRequest();
        BeanUtils.copyProperties(smsChannelTbl, getTokenRequest);

        String token = u02cxApi.getToken(getTokenRequest);
        log.info("token:{}", token);
        AddTaskRequest addTaskRequest = new AddTaskRequest();
        addTaskRequest.setTaskName(smsTaskTbl.getTaskName())
                        .setPrice(smsTaskTbl.getPrice())
                        .setTaskBeginTime(DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",smsTaskTbl.getTaskBeginTime()))
                        .setFileName(smsTaskTbl.getFileName())
                        .setFilePath(smsTaskTbl.getFilePath())
                        .setFileMd5(smsTaskTbl.getFileMd5())
                        .setContext(smsTaskTbl.getContext())
                        .setType(Integer.parseInt(smsTaskTbl.getSmsContentType()));
        try {
            // 将公钥字符串转换为 PublicKey 对象
//            PublicKey publicKey = KeyUtils.getPublicKeyFromBase64String(smsChannelTbl.getPublicKey());

            // 使用公钥加密数据
            String jsonStr = JSONUtil.toJsonStr(addTaskRequest);
            log.info("jsonStr:{}", jsonStr);
//            String encryptedData = EncryptionUtils.encrypt(jsonStr, publicKey);
            String encryptedData = RsaUtils.encryptData(jsonStr, smsChannelTbl.getPublicKey());
            String taskId = u02cxApi.addTask(encryptedData,token,smsChannelTbl.getAppId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
