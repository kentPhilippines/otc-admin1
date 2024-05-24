package com.ruoyi.system.service;


import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.system.domain.MonitorAddressAccount;
import com.ruoyi.system.domain.vo.MonitorAddressInfoVO;

import java.util.List;

/**
 * 监听账户入账Service接口
 * 
 * @author dorion
 * @date 2024-04-13
 */
public interface IMonitorAddressInfoService 
{
    /**
     * 查询监听账户入账
     * 
     * @param idMonitorAddress 监听账户入账主键
     * @return 监听账户入账
     */
    public MonitorAddressInfo selectMonitorAddressInfoByIdMonitorAddress(Long idMonitorAddress);

    /**
     * 查询监听账户入账列表
     *
     * @param monitorAddressInfo 监听账户入账
     * @return 监听账户入账集合
     */
    public List<MonitorAddressInfoVO> selectMonitorAddressInfoList(MonitorAddressInfo monitorAddressInfo);

    /**
     * 新增监听账户入账
     * 
     * @param monitorAddressInfo 监听账户入账
     * @return 结果
     */
    public int insertMonitorAddressInfo(MonitorAddressInfo monitorAddressInfo);

    /**
     * 修改监听账户入账
     * 
     * @param monitorAddressInfo 监听账户入账
     * @return 结果
     */
    public int updateMonitorAddressInfo(MonitorAddressInfo monitorAddressInfo);

    /**
     * 批量删除监听账户入账
     * 
     * @param idMonitorAddresss 需要删除的监听账户入账主键集合
     * @return 结果
     */
    public int deleteMonitorAddressInfoByIdMonitorAddresss(String idMonitorAddresss);

    /**
     * 删除监听账户入账信息
     * 
     * @param idMonitorAddress 监听账户入账主键
     * @return 结果
     */
    public int deleteMonitorAddressInfoByIdMonitorAddress(Long idMonitorAddress);

    List<MonitorAddressInfo> selectAllValidMonitorAddressAccount(String busiType);

    List<MonitorAddressAccount> selectMonitorAddressAccount(MonitorAddressInfo monitorAddressInfoExample);
}
