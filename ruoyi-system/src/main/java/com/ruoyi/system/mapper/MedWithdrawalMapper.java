package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.MedWithdrawal;

/**
 * 用户提现订单Mapper接口
 * 
 * @author kkkkkk
 * @date 2024-10-23
 */
public interface MedWithdrawalMapper 
{
    /**
     * 查询用户提现订单
     * 
     * @param id 用户提现订单主键
     * @return 用户提现订单
     */
    public MedWithdrawal selectMedWithdrawalById(Long id);

    /**
     * 查询用户提现订单列表
     * 
     * @param medWithdrawal 用户提现订单
     * @return 用户提现订单集合
     */
    public List<MedWithdrawal> selectMedWithdrawalList(MedWithdrawal medWithdrawal);

    /**
     * 新增用户提现订单
     * 
     * @param medWithdrawal 用户提现订单
     * @return 结果
     */
    public int insertMedWithdrawal(MedWithdrawal medWithdrawal);

    /**
     * 修改用户提现订单
     * 
     * @param medWithdrawal 用户提现订单
     * @return 结果
     */
    public int updateMedWithdrawal(MedWithdrawal medWithdrawal);

    /**
     * 删除用户提现订单
     * 
     * @param id 用户提现订单主键
     * @return 结果
     */
    public int deleteMedWithdrawalById(Long id);

    /**
     * 批量删除用户提现订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMedWithdrawalByIds(String[] ids);
}
