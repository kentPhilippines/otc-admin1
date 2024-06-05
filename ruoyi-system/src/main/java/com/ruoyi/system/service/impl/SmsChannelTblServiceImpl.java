package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SmsChannelTbl;
import com.ruoyi.system.mapper.SmsChannelTblMapper;
import com.ruoyi.system.service.ISmsChannelTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信渠道管理Service业务层处理
 * 
 * @author dorion
 * @date 2024-05-28
 */
@Service
public class SmsChannelTblServiceImpl implements ISmsChannelTblService 
{
    @Autowired
    private SmsChannelTblMapper smsChannelTblMapper;

    /**
     * 查询短信渠道管理
     * 
     * @param idSmsChannel 短信渠道管理主键
     * @return 短信渠道管理
     */
    @Override
    public SmsChannelTbl selectSmsChannelTblByIdSmsChannel(Long idSmsChannel)
    {
        return smsChannelTblMapper.selectSmsChannelTblByIdSmsChannel(idSmsChannel);
    }

    /**
     * 查询短信渠道管理列表
     * 
     * @param smsChannelTbl 短信渠道管理
     * @return 短信渠道管理
     */
    @Override
    public List<SmsChannelTbl> selectSmsChannelTblList(SmsChannelTbl smsChannelTbl)
    {
        return smsChannelTblMapper.selectSmsChannelTblList(smsChannelTbl);
    }

    /**
     * 新增短信渠道管理
     * 
     * @param smsChannelTbl 短信渠道管理
     * @return 结果
     */
    @Override
    public int insertSmsChannelTbl(SmsChannelTbl smsChannelTbl)
    {
        smsChannelTbl.setCreateTime(DateUtils.getNowDate());
        return smsChannelTblMapper.insertSmsChannelTbl(smsChannelTbl);
    }

    /**
     * 修改短信渠道管理
     * 
     * @param smsChannelTbl 短信渠道管理
     * @return 结果
     */
    @Override
    public int updateSmsChannelTbl(SmsChannelTbl smsChannelTbl)
    {
        smsChannelTbl.setUpdateTime(DateUtils.getNowDate());
        return smsChannelTblMapper.updateSmsChannelTbl(smsChannelTbl);
    }

    /**
     * 批量删除短信渠道管理
     * 
     * @param idSmsChannels 需要删除的短信渠道管理主键
     * @return 结果
     */
    @Override
    public int deleteSmsChannelTblByIdSmsChannels(String idSmsChannels)
    {
        return smsChannelTblMapper.deleteSmsChannelTblByIdSmsChannels(Convert.toStrArray(idSmsChannels));
    }

    /**
     * 删除短信渠道管理信息
     * 
     * @param idSmsChannel 短信渠道管理主键
     * @return 结果
     */
    @Override
    public int deleteSmsChannelTblByIdSmsChannel(Long idSmsChannel)
    {
        return smsChannelTblMapper.deleteSmsChannelTblByIdSmsChannel(idSmsChannel);
    }
}
