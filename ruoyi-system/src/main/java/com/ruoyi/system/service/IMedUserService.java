package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.MedUser;

/**
 * 用户详情Service接口
 * 
 * @author kkkkk
 * @date 2024-10-23
 */
public interface IMedUserService 
{
    /**
     * 查询用户详情
     * 
     * @param id 用户详情主键
     * @return 用户详情
     */
    public MedUser selectMedUserById(Long id);

    /**
     * 查询用户详情列表
     * 
     * @param medUser 用户详情
     * @return 用户详情集合
     */
    public List<MedUser> selectMedUserList(MedUser medUser);

    /**
     * 新增用户详情
     * 
     * @param medUser 用户详情
     * @return 结果
     */
    public int insertMedUser(MedUser medUser);

    /**
     * 修改用户详情
     * 
     * @param medUser 用户详情
     * @return 结果
     */
    public int updateMedUser(MedUser medUser);

    /**
     * 批量删除用户详情
     * 
     * @param ids 需要删除的用户详情主键集合
     * @return 结果
     */
    public int deleteMedUserByIds(String ids);

    /**
     * 删除用户详情信息
     * 
     * @param id 用户详情主键
     * @return 结果
     */
    public int deleteMedUserById(Long id);
}
