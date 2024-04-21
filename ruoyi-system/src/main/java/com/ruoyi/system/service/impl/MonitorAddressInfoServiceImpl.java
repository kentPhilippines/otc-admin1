package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.mapper.MonitorAddressInfoMapper;
import com.ruoyi.system.service.IMonitorAddressInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 监听账户入账Service业务层处理
 *
 * @author dorion
 * @date 2024-04-13
 */
@Service
@Slf4j
public class MonitorAddressInfoServiceImpl implements IMonitorAddressInfoService {
    @Autowired
    private MonitorAddressInfoMapper monitorAddressInfoMapper;


    /**
     * 查询监听账户入账
     *
     * @param idMonitorAddress 监听账户入账主键
     * @return 监听账户入账
     */
    @Override
    public MonitorAddressInfo selectMonitorAddressInfoByIdMonitorAddress(Long idMonitorAddress) {
        return monitorAddressInfoMapper.selectMonitorAddressInfoByIdMonitorAddress(idMonitorAddress);
    }

    /**
     * 查询监听账户入账列表
     *
     * @param monitorAddressInfo 监听账户入账
     * @return 监听账户入账
     */
    @Override
    public List<MonitorAddressInfo> selectMonitorAddressInfoList(MonitorAddressInfo monitorAddressInfo) {
        return monitorAddressInfoMapper.selectMonitorAddressInfoList(monitorAddressInfo);
    }

    /**
     * 新增监听账户入账
     *
     * @param monitorAddressInfo 监听账户入账
     * @return 结果
     */
    @Override
    public int insertMonitorAddressInfo(MonitorAddressInfo monitorAddressInfo) {
        String userName = ShiroUtils.getLoginName();
        monitorAddressInfo.setFcu(userName);
        monitorAddressInfo.setLcu(userName);
        return monitorAddressInfoMapper.insertMonitorAddressInfo(monitorAddressInfo);
    }

    /**
     * 修改监听账户入账
     *
     * @param monitorAddressInfo 监听账户入账
     * @return 结果
     */
    @Override
    public int updateMonitorAddressInfo(MonitorAddressInfo monitorAddressInfo) {
        String userName = ShiroUtils.getLoginName();
        monitorAddressInfo.setLcu(userName);
        return monitorAddressInfoMapper.updateMonitorAddressInfo(monitorAddressInfo);
    }

    /**
     * 批量删除监听账户入账
     *
     * @param idMonitorAddresss 需要删除的监听账户入账主键
     * @return 结果
     */
    @Override
    public int deleteMonitorAddressInfoByIdMonitorAddresss(String idMonitorAddresss) {
        return monitorAddressInfoMapper.deleteMonitorAddressInfoByIdMonitorAddresss(Convert.toStrArray(idMonitorAddresss));
    }

    /**
     * 删除监听账户入账信息
     *
     * @param idMonitorAddress 监听账户入账主键
     * @return 结果
     */
    @Override
    public int deleteMonitorAddressInfoByIdMonitorAddress(Long idMonitorAddress) {
        return monitorAddressInfoMapper.deleteMonitorAddressInfoByIdMonitorAddress(idMonitorAddress);
    }


  /*  @Override
    public List<MonitorAddressAccount> selectAllMonitorAddressAccount(MonitorAddressAccount monitorAddressAccountExample) {
        return monitorAddressInfoMapper.selectAllMonitorAddressAccount(monitorAddressAccountExample);
    }*/

    @Override
    public List<MonitorAddressInfo> selectAllValidMonitorAddressAccount(String busiType) {
        MonitorAddressInfo monitorAddressInfo = new MonitorAddressInfo();
        monitorAddressInfo.setIsValid("Y");
        monitorAddressInfo.setBusiType(busiType);

        return   monitorAddressInfoMapper.selectMonitorAddressInfoList(monitorAddressInfo);
    }


}
