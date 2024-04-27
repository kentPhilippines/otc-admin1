package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.system.domain.MonitorAddressAccount;

import java.util.List;


/**
 * 监听账户入账Mapper接口
 *
 * @author dorion
 * @date 2024-04-13
 */
public interface MonitorAddressInfoMapper {
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
    public List<MonitorAddressInfo> selectMonitorAddressInfoList(MonitorAddressInfo monitorAddressInfo);

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
     * 删除监听账户入账
     *
     * @param idMonitorAddress 监听账户入账主键
     * @return 结果
     */
    public int deleteMonitorAddressInfoByIdMonitorAddress(Long idMonitorAddress);

    /**
     * 批量删除监听账户入账
     *
     * @param idMonitorAddresss 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMonitorAddressInfoByIdMonitorAddresss(String[] idMonitorAddresss);


    List<MonitorAddressAccount> selectMonitorAddressAccount(MonitorAddressInfo monitorAddressInfoExample);
}
