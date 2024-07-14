package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.TgMessageInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.mapper.TgMessageInfoMapper;
import com.ruoyi.system.service.ITgMessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TG消息管理Service业务层处理
 *
 * @author dorion
 * @date 2024-07-12
 */
@Service
public class TgMessageInfoServiceImpl implements ITgMessageInfoService {
    @Autowired
    private TgMessageInfoMapper tgMessageInfoMapper;

    /**
     * 查询TG消息管理
     *
     * @param idTgMessageInfo TG消息管理主键
     * @return TG消息管理
     */
    @Override
    public TgMessageInfo selectTgMessageInfoByIdTgMessageInfo(Long idTgMessageInfo) {
        return tgMessageInfoMapper.selectTgMessageInfoByIdTgMessageInfo(idTgMessageInfo);
    }

    /**
     * 查询TG消息管理列表
     *
     * @param tgMessageInfo TG消息管理
     * @return TG消息管理
     */
    @Override
    public List<TgMessageInfo> selectTgMessageInfoList(TgMessageInfo tgMessageInfo) {
        return tgMessageInfoMapper.selectTgMessageInfoList(tgMessageInfo);
    }

    /**
     * 新增TG消息管理
     *
     * @param tgMessageInfo TG消息管理
     * @return 结果
     */
    @Override
    public int insertTgMessageInfo(TgMessageInfo tgMessageInfo) {

        String loginName = ShiroUtils.getSysUser().getLoginName();
        tgMessageInfo.setCreateBy(loginName);
        tgMessageInfo.setUpdateBy(loginName);

        return tgMessageInfoMapper.insertTgMessageInfo(tgMessageInfo);
    }




    /**
     * 修改TG消息管理
     *
     * @param tgMessageInfo TG消息管理
     * @return 结果
     */
    @Override
    public int updateTgMessageInfo(TgMessageInfo tgMessageInfo) {
        String loginName = ShiroUtils.getSysUser().getLoginName();
        tgMessageInfo.setUpdateBy(loginName);
        return tgMessageInfoMapper.updateTgMessageInfo(tgMessageInfo);
    }

    /**
     * 批量删除TG消息管理
     *
     * @param idTgMessageInfos 需要删除的TG消息管理主键
     * @return 结果
     */
    @Override
    public int deleteTgMessageInfoByIdTgMessageInfos(String idTgMessageInfos) {
        return tgMessageInfoMapper.deleteTgMessageInfoByIdTgMessageInfos(Convert.toStrArray(idTgMessageInfos));
    }

    /**
     * 删除TG消息管理信息
     *
     * @param idTgMessageInfo TG消息管理主键
     * @return 结果
     */
    @Override
    public int deleteTgMessageInfoByIdTgMessageInfo(Long idTgMessageInfo) {
        return tgMessageInfoMapper.deleteTgMessageInfoByIdTgMessageInfo(idTgMessageInfo);
    }

    @Override
    public List<TgMessageInfo> selectTopicTgMessageInfoList() {
        return tgMessageInfoMapper.selectTopicTgMessageInfoList();
    }
}
