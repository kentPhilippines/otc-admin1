package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MedUserMapper;
import com.ruoyi.system.domain.MedUser;
import com.ruoyi.system.service.IMedUserService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户详情Service业务层处理
 * 
 * @author kkkkk
 * @date 2024-10-23
 */
@Service
public class MedUserServiceImpl implements IMedUserService 
{
    @Autowired
    private MedUserMapper medUserMapper;

    /**
     * 查询用户详情
     * 
     * @param id 用户详情主键
     * @return 用户详情
     */
    @Override
    public MedUser selectMedUserById(Long id)
    {
        return medUserMapper.selectMedUserById(id);
    }

    /**
     * 查询用户详情列表
     * 
     * @param medUser 用户详情
     * @return 用户详情
     */
    @Override
    public List<MedUser> selectMedUserList(MedUser medUser)
    {
        return medUserMapper.selectMedUserList(medUser);
    }

    /**
     * 新增用户详情
     * 
     * @param medUser 用户详情
     * @return 结果
     */
    @Override
    public int insertMedUser(MedUser medUser)
    {
        return medUserMapper.insertMedUser(medUser);
    }

    /**
     * 修改用户详情
     * 
     * @param medUser 用户详情
     * @return 结果
     */
    @Override
    public int updateMedUser(MedUser medUser)
    {
        return medUserMapper.updateMedUser(medUser);
    }

    /**
     * 批量删除用户详情
     * 
     * @param ids 需要删除的用户详情主键
     * @return 结果
     */
    @Override
    public int deleteMedUserByIds(String ids)
    {
        return medUserMapper.deleteMedUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户详情信息
     * 
     * @param id 用户详情主键
     * @return 结果
     */
    @Override
    public int deleteMedUserById(Long id)
    {
        return medUserMapper.deleteMedUserById(id);
    }
}
