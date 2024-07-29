package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.UserPoint;

import java.util.List;


/**
 * 用户积分Service接口
 * 
 * @author dorion
 * @date 2024-07-21
 */
public interface IUserPointService 
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
     * 批量删除用户积分
     * 
     * @param idUserPoints 需要删除的用户积分主键集合
     * @return 结果
     */
    public int deleteUserPointByIdUserPoints(String idUserPoints);

    /**
     * 删除用户积分信息
     * 
     * @param idUserPoint 用户积分主键
     * @return 结果
     */
    public int deleteUserPointByIdUserPoint(Long idUserPoint);
}
