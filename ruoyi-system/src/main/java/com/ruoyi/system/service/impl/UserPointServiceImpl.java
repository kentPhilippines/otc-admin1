package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.UserPoint;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.mapper.UserPointMapper;
import com.ruoyi.system.service.IUserPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户积分Service业务层处理
 * 
 * @author dorion
 * @date 2024-07-21
 */
@Service
public class UserPointServiceImpl implements IUserPointService 
{
    @Autowired
    private UserPointMapper userPointMapper;

    /**
     * 查询用户积分
     * 
     * @param idUserPoint 用户积分主键
     * @return 用户积分
     */
    @Override
    public UserPoint selectUserPointByIdUserPoint(Long idUserPoint)
    {
        return userPointMapper.selectUserPointByIdUserPoint(idUserPoint);
    }

    /**
     * 查询用户积分列表
     * 
     * @param userPoint 用户积分
     * @return 用户积分
     */
    @Override
    @DataScope(userAlias = "u")
    public List<UserPoint> selectUserPointList(UserPoint userPoint)
    {
        return userPointMapper.selectUserPointList(userPoint);
    }

    /**
     * 新增用户积分
     * 
     * @param userPoint 用户积分
     * @return 结果
     */
    @Override
    public int insertUserPoint(UserPoint userPoint)
    {
        userPoint.setCreateTime(DateUtils.getNowDate());
        return userPointMapper.insertUserPoint(userPoint);
    }

    /**
     * 修改用户积分
     * 
     * @param userPoint 用户积分
     * @return 结果
     */
    @Override
    public int updateUserPoint(UserPoint userPoint)
    {
        userPoint.setUpdateTime(DateUtils.getNowDate());
        return userPointMapper.updateUserPoint(userPoint);
    }

    /**
     * 批量删除用户积分
     * 
     * @param idUserPoints 需要删除的用户积分主键
     * @return 结果
     */
    @Override
    public int deleteUserPointByIdUserPoints(String idUserPoints)
    {
        return userPointMapper.deleteUserPointByIdUserPoints(Convert.toStrArray(idUserPoints));
    }

    /**
     * 删除用户积分信息
     * 
     * @param idUserPoint 用户积分主键
     * @return 结果
     */
    @Override
    public int deleteUserPointByIdUserPoint(Long idUserPoint)
    {
        return userPointMapper.deleteUserPointByIdUserPoint(idUserPoint);
    }
}
