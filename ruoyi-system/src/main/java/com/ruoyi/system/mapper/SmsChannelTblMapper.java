package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SmsChannelTbl;

import java.util.List;

/**
 * 短信渠道管理Mapper接口
 * 
 * @author dorion
 * @date 2024-05-28
 */
public interface SmsChannelTblMapper 
{
    /**
     * 查询短信渠道管理
     * 
     * @param idSmsChannel 短信渠道管理主键
     * @return 短信渠道管理
     */
    public SmsChannelTbl selectSmsChannelTblByIdSmsChannel(Long idSmsChannel);

    /**
     * 查询短信渠道管理列表
     * 
     * @param smsChannelTbl 短信渠道管理
     * @return 短信渠道管理集合
     */
    public List<SmsChannelTbl> selectSmsChannelTblList(SmsChannelTbl smsChannelTbl);

    /**
     * 新增短信渠道管理
     * 
     * @param smsChannelTbl 短信渠道管理
     * @return 结果
     */
    public int insertSmsChannelTbl(SmsChannelTbl smsChannelTbl);

    /**
     * 修改短信渠道管理
     * 
     * @param smsChannelTbl 短信渠道管理
     * @return 结果
     */
    public int updateSmsChannelTbl(SmsChannelTbl smsChannelTbl);

    /**
     * 删除短信渠道管理
     * 
     * @param idSmsChannel 短信渠道管理主键
     * @return 结果
     */
    public int deleteSmsChannelTblByIdSmsChannel(Long idSmsChannel);

    /**
     * 批量删除短信渠道管理
     * 
     * @param idSmsChannels 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSmsChannelTblByIdSmsChannels(String[] idSmsChannels);
}
