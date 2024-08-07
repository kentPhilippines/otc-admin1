package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.TenantInfo;

import java.util.List;


/**
 * 租户Service接口
 * 
 * @author dorion
 * @date 2024-04-14
 */
public interface ITenantInfoService 
{
    /**
     * 查询租户
     * 
     * @param idTenantInfo 租户主键
     * @return 租户
     */
    public TenantInfo selectTenantInfoByIdTenantInfo(Long idTenantInfo);

    /**
     * 查询租户列表
     * 
     * @param tenantInfo 租户
     * @return 租户集合
     */
    public List<TenantInfo> selectTenantInfoList(TenantInfo tenantInfo);

    /**
     * 新增租户
     * 
     * @param tenantInfo 租户
     * @return 结果
     */
    public int insertTenantInfo(TenantInfo tenantInfo);

    /**
     * 修改租户
     * 
     * @param tenantInfo 租户
     * @return 结果
     */
    public int updateTenantInfo(TenantInfo tenantInfo);

    /**
     * 批量删除租户
     * 
     * @param idTenantInfos 需要删除的租户主键集合
     * @return 结果
     */
    public int deleteTenantInfoByIdTenantInfos(String idTenantInfos);

    /**
     * 删除租户信息
     * 
     * @param idTenantInfo 租户主键
     * @return 结果
     */
    public int deleteTenantInfoByIdTenantInfo(Long idTenantInfo);

    int activeDataTenantInfoByIdTenantInfos(String ids) throws Exception;

//    void doDelegateEnergy(TenantInfo tenantInfo) throws Exception;

    void doDelegateEnergy(TenantInfo tenantInfo, String userName) throws Exception;

    void doDelegateEnergy(TenantInfo tenantInfo, String userName, String accountAddress, String monitorAddress) throws Exception;

    List<TenantInfo> selectTenantInfoListNotExistsInExchange(TenantInfo tenantInfoExample);
}
