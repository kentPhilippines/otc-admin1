package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.google.common.base.Preconditions;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.common.core.domain.entity.TenantInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.TrxExchange;
import com.ruoyi.system.mapper.MonitorAddressInfoMapper;
import com.ruoyi.system.mapper.TenantInfoMapper;
import com.ruoyi.system.service.ITenantInfoService;
import com.ruoyi.system.service.ITrxExchangeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 租户Service业务层处理
 * 
 * @author dorion
 * @date 2024-04-14
 */
@Service
public class TenantInfoServiceImpl implements ITenantInfoService 
{
    @Autowired
    private TenantInfoMapper tenantInfoMapper;

    @Autowired
    private ITrxExchangeInfoService trxExchangeInfoService;
    @Autowired
    private MonitorAddressInfoMapper monitorAddressInfoMapper;

    /**
     * 查询租户
     * 
     * @param idTenantInfo 租户主键
     * @return 租户
     */
    @Override
    public TenantInfo selectTenantInfoByIdTenantInfo(Long idTenantInfo)
    {
        return tenantInfoMapper.selectTenantInfoByIdTenantInfo(idTenantInfo);
    }

    /**
     * 查询租户列表
     * 
     * @param tenantInfo 租户
     * @return 租户
     */
    @Override
    public List<TenantInfo> selectTenantInfoList(TenantInfo tenantInfo)
    {
        return tenantInfoMapper.selectTenantInfoList(tenantInfo);
    }

    /**
     * 新增租户
     * 
     * @param tenantInfo 租户
     * @return 结果
     */
    @Override
    public int insertTenantInfo(TenantInfo tenantInfo)
    {
        Long price = tenantInfo.getPrice();
        Long transferCount = tenantInfo.getTransferCount();

        long exchangeAmount = price * transferCount;
        tenantInfo.setExchangeAmount(exchangeAmount);
        tenantInfo.setIsPaid(UserConstants.NO);
        Long period = tenantInfo.getPeriod();

        DateTime dateTime = DateUtil.offsetDay(DateUtil.date(), period.intValue());

        tenantInfo.setFinishTransferTime( DateUtil.endOfDay(dateTime));

        String loginName = ShiroUtils.getLoginName();
        tenantInfo.setFcd(new Date());
        tenantInfo.setFcu(loginName);

        tenantInfo.setLcd(new Date());
        tenantInfo.setLcu(loginName);

        return tenantInfoMapper.insertTenantInfo(tenantInfo);
    }


    /**
     * 修改租户
     * 
     * @param tenantInfo 租户
     * @return 结果
     */
    @Override
    public int updateTenantInfo(TenantInfo tenantInfo)
    {
        Long price = tenantInfo.getPrice();
        Long transferCount = tenantInfo.getTransferCount();

        long exchangeAmount = price * transferCount;
        tenantInfo.setExchangeAmount(exchangeAmount);
        Long period = tenantInfo.getPeriod();

        DateTime dateTime = DateUtil.offsetDay(tenantInfo.getFcd(), period.intValue());
        tenantInfo.setFinishTransferTime( DateUtil.endOfDay(dateTime));

        String loginName = ShiroUtils.getLoginName();

        tenantInfo.setLcd(new Date());
        tenantInfo.setLcu(loginName);

        return tenantInfoMapper.updateTenantInfo(tenantInfo);
    }

    /**
     * 批量删除租户
     * 
     * @param idTenantInfos 需要删除的租户主键
     * @return 结果
     */
    @Override
    public int deleteTenantInfoByIdTenantInfos(String idTenantInfos)
    {
        return tenantInfoMapper.deleteTenantInfoByIdTenantInfos(Convert.toStrArray(idTenantInfos));
    }

    /**
     * 删除租户信息
     * 
     * @param idTenantInfo 租户主键
     * @return 结果
     */
    @Override
    public int deleteTenantInfoByIdTenantInfo(Long idTenantInfo)
    {
        return tenantInfoMapper.deleteTenantInfoByIdTenantInfo(idTenantInfo);
    }

    @Override
    public int activeDataTenantInfoByIdTenantInfos(String idTenantInfos) throws Exception {
        String[] idArray = Convert.toStrArray(idTenantInfos);
        for (String id : idArray) {
            TenantInfo tenantInfo = tenantInfoMapper.selectTenantInfoByIdTenantInfo(Long.valueOf(id));

            Date finishTransferTime = tenantInfo.getFinishTransferTime();
            int compare = DateUtil.compare(new Date(), finishTransferTime);

            Preconditions.checkState(compare <= 0, "租户已过期,无法再次委托能量");

            MonitorAddressInfo monitorAddressInfoExample = new MonitorAddressInfo();
            monitorAddressInfoExample.setMonitorAddress(tenantInfo.getMonitorAddress());
            monitorAddressInfoExample.setIsValid(UserConstants.YES);
            List<MonitorAddressInfo> monitorAddressInfoList = monitorAddressInfoMapper.selectMonitorAddressInfoList(monitorAddressInfoExample);

            Preconditions.checkState(CollectionUtil.isNotEmpty(monitorAddressInfoList), "监听地址不存在或者已失效,无法再次委托能量");

            MonitorAddressInfo monitorAddressInfo = monitorAddressInfoList.get(0);

            TrxExchange trxExchange = new TrxExchange();
            trxExchange.setFromAddress(tenantInfo.getReceiverAddress());
            trxExchange.setAccountAddress(monitorAddressInfo.getAccountAddress());
            trxExchange.setTransferNumber(tenantInfo.getTransferCount());
            trxExchange.setLockNum(24L);

            trxExchangeInfoService.delegate(trxExchange, true);
        }

        return 1;
    }
}
