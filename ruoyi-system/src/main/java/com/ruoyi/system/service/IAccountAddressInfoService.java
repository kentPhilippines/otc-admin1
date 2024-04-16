package com.ruoyi.system.service;


import com.ruoyi.common.core.domain.entity.AccountAddressInfo;

import java.util.List;

/**
 * 出账账户Service接口
 * 
 * @author dorion
 * @date 2024-04-13
 */
public interface IAccountAddressInfoService 
{
    /**
     * 查询出账账户
     * 
     * @param idAccoutAddressInfo 出账账户主键
     * @return 出账账户
     */
    public AccountAddressInfo selectAccountAddressInfoByIdAccoutAddressInfo(Long idAccoutAddressInfo);

    /**
     * 查询出账账户列表
     * 
     * @param accountAddressInfo 出账账户
     * @return 出账账户集合
     */
    public List<AccountAddressInfo> selectAccountAddressInfoList(AccountAddressInfo accountAddressInfo) ;

    /**
     * 新增出账账户
     * 
     * @param accountAddressInfo 出账账户
     * @return 结果
     */
    public int insertAccountAddressInfo(AccountAddressInfo accountAddressInfo) throws Exception;

    /**
     * 修改出账账户
     * 
     * @param accountAddressInfo 出账账户
     * @return 结果
     */
    public int updateAccountAddressInfo(AccountAddressInfo accountAddressInfo) throws Exception;

    /**
     * 批量删除出账账户
     * 
     * @param idAccoutAddressInfos 需要删除的出账账户主键集合
     * @return 结果
     */
    public int deleteAccountAddressInfoByIdAccoutAddressInfos(String idAccoutAddressInfos);

    /**
     * 删除出账账户信息
     * 
     * @param idAccoutAddressInfo 出账账户主键
     * @return 结果
     */
    public int deleteAccountAddressInfoByIdAccoutAddressInfo(Long idAccoutAddressInfo);

    Object selectAccountAddressInfoAll();

    String getDecryptPrivateKey(String address) throws Exception;

    List<AccountAddressInfo> selectAccountAddressInfoListByResouce(AccountAddressInfo accountAddressInfo) throws Exception;
}
