package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.UserPoint;

import java.util.List;

/**
 * 用户积分Mapper接口
 * 
 * @author dorion
 * @date 2024-07-21
 */
public interface UserPointMapper 
{
    /**
     * 查询用户积分
     * 
     * @param idUserPoint 用户积分主键
     * @return 用户积分
     */
    public UserPoint selectUserPointByIdUserPoint(Long idUserPoint);

    /**
     * 查询用户积分列表
     * 
     * @param userPoint 用户积分
     * @return 用户积分集合
     */
    public List<UserPoint> selectUserPointList(UserPoint userPoint);

    /**
     * 新增用户积分
     * 
     * @param userPoint 用户积分
     * @return 结果
     */
    public int insertUserPoint(UserPoint userPoint);

    /**
     * 修改用户积分
     * 
     * @param userPoint 用户积分
     * @return 结果
     */
    public int updateUserPoint(UserPoint userPoint);

    /**
     * 删除用户积分
     * 
     * @param idUserPoint 用户积分主键
     * @return 结果
     */
    public int deleteUserPointByIdUserPoint(Long idUserPoint);

    /**
     * 批量删除用户积分
     * 
     * @param idUserPoints 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserPointByIdUserPoints(String[] idUserPoints);
}
