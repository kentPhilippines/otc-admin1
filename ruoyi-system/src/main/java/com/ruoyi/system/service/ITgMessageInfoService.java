package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.TgMessageInfo;

import java.util.List;


/**
 * TG消息管理Service接口
 * 
 * @author dorion
 * @date 2024-07-12
 */
public interface ITgMessageInfoService 
{
    /**
     * 查询TG消息管理
     * 
     * @param idTgMessageInfo TG消息管理主键
     * @return TG消息管理
     */
    public TgMessageInfo selectTgMessageInfoByIdTgMessageInfo(Long idTgMessageInfo);

    /**
     * 查询TG消息管理列表
     * 
     * @param tgMessageInfo TG消息管理
     * @return TG消息管理集合
     */
    public List<TgMessageInfo> selectTgMessageInfoList(TgMessageInfo tgMessageInfo);

    /**
     * 新增TG消息管理
     * 
     * @param tgMessageInfo TG消息管理
     * @return 结果
     */
    public int insertTgMessageInfo(TgMessageInfo tgMessageInfo);

    /**
     * 修改TG消息管理
     * 
     * @param tgMessageInfo TG消息管理
     * @return 结果
     */
    public int updateTgMessageInfo(TgMessageInfo tgMessageInfo);

    /**
     * 批量删除TG消息管理
     * 
     * @param idTgMessageInfos 需要删除的TG消息管理主键集合
     * @return 结果
     */
    public int deleteTgMessageInfoByIdTgMessageInfos(String idTgMessageInfos);

    /**
     * 删除TG消息管理信息
     * 
     * @param idTgMessageInfo TG消息管理主键
     * @return 结果
     */
    public int deleteTgMessageInfoByIdTgMessageInfo(Long idTgMessageInfo);
}
