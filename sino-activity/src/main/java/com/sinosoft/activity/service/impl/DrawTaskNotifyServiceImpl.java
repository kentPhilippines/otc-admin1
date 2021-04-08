package com.sinosoft.activity.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinosoft.activity.mapper.DrawTaskNotifyMapper;
import com.sinosoft.activity.domain.DrawTaskNotify;
import com.sinosoft.activity.service.IDrawTaskNotifyService;
import com.ruoyi.common.core.text.Convert;

/**
 * 活动次数记录信息Service业务层处理
 * 
 * @author dy
 * @date 2021-03-26
 */
@Service
public class DrawTaskNotifyServiceImpl implements IDrawTaskNotifyService 
{
    @Autowired
    private DrawTaskNotifyMapper drawTaskNotifyMapper;

    /**
     * 查询活动次数记录信息
     * 
     * @param USERID 活动次数记录信息ID
     * @return 活动次数记录信息
     */
    @Override
    public DrawTaskNotify selectDrawTaskNotifyById(String USERID)
    {
        return drawTaskNotifyMapper.selectDrawTaskNotifyById(USERID);
    }

    /**
     * 查询活动次数记录信息列表
     * 
     * @param drawTaskNotify 活动次数记录信息
     * @return 活动次数记录信息
     */
    @Override
    public List<DrawTaskNotify> selectDrawTaskNotifyList(DrawTaskNotify drawTaskNotify)
    {
        return drawTaskNotifyMapper.selectDrawTaskNotifyList(drawTaskNotify);
    }

    /**
     * 新增活动次数记录信息
     * 
     * @param drawTaskNotify 活动次数记录信息
     * @return 结果
     */
    @Override
    public int insertDrawTaskNotify(DrawTaskNotify drawTaskNotify)
    {
        return drawTaskNotifyMapper.insertDrawTaskNotify(drawTaskNotify);
    }

    /**
     * 修改活动次数记录信息
     * 
     * @param drawTaskNotify 活动次数记录信息
     * @return 结果
     */
    @Override
    public int updateDrawTaskNotify(DrawTaskNotify drawTaskNotify)
    {
        return drawTaskNotifyMapper.updateDrawTaskNotify(drawTaskNotify);
    }

    /**
     * 删除活动次数记录信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDrawTaskNotifyByIds(String ids)
    {
        return drawTaskNotifyMapper.deleteDrawTaskNotifyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除活动次数记录信息信息
     * 
     * @param USERID 活动次数记录信息ID
     * @return 结果
     */
    @Override
    public int deleteDrawTaskNotifyById(String USERID)
    {
        return drawTaskNotifyMapper.deleteDrawTaskNotifyById(USERID);
    }
}