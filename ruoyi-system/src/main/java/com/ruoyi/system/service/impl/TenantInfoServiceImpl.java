package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.base.Preconditions;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.AccountAddressInfo;
import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.common.core.domain.entity.TenantInfo;
import com.ruoyi.common.core.domain.entity.TrxExchangeInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.TrxExchange;
import com.ruoyi.system.domain.vo.TrxExchangeInfoVO;
import com.ruoyi.system.mapper.AccountAddressInfoMapper;
import com.ruoyi.system.mapper.MonitorAddressInfoMapper;
import com.ruoyi.system.mapper.TenantInfoMapper;
import com.ruoyi.system.service.ITenantInfoService;
import com.ruoyi.system.service.ITrxExchangeInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tron.trident.proto.Common;

import java.util.Date;
import java.util.List;

/**
 * 租户Service业务层处理
 *
 * @author dorion
 * @date 2024-04-14
 */
@Service
public class TenantInfoServiceImpl implements ITenantInfoService {
    @Autowired
    private TenantInfoMapper tenantInfoMapper;

    @Autowired
    private ITrxExchangeInfoService trxExchangeInfoService;
    @Autowired
    private MonitorAddressInfoMapper monitorAddressInfoMapper;
    @Autowired
    private AccountAddressInfoMapper accountAddressInfoMapper;

    /**
     * 查询租户
     *
     * @param idTenantInfo 租户主键
     * @return 租户
     */
    @Override
    public TenantInfo selectTenantInfoByIdTenantInfo(Long idTenantInfo) {
        return tenantInfoMapper.selectTenantInfoByIdTenantInfo(idTenantInfo);
    }

    /**
     * 查询租户列表
     *
     * @param tenantInfo 租户
     * @return 租户
     */
    @Override
    @DataScope(userAlias = "u")
    public List<TenantInfo> selectTenantInfoList(TenantInfo tenantInfo) {
        return tenantInfoMapper.selectTenantInfoList(tenantInfo);
    }

    /**
     * 新增租户
     *
     * @param tenantInfo 租户
     * @return 结果
     */
    @Override
    public int insertTenantInfo(TenantInfo tenantInfo) {

        String energyBusiType = tenantInfo.getEnergyBusiType();
        Preconditions.checkState(!(energyBusiType.equals("1") || energyBusiType.equals("2")), "套餐类型不正确,定制业务不支持");

        Long delegatedDays = tenantInfo.getDelegatedDays();

        if (energyBusiType.equals("4")) {
            Preconditions.checkState(delegatedDays == null, "笔数不限时套餐天数必须为无");
        }


        TenantInfo tenantInfoExample = new TenantInfo();
        tenantInfoExample.setReceiverAddress(tenantInfo.getReceiverAddress());
        List<TenantInfo> tenantInfoList = tenantInfoMapper.selectTenantInfoList(tenantInfoExample);
        boolean exists = false;
        if (CollectionUtil.isNotEmpty(tenantInfoList)) {
            exists = tenantInfoList.stream().anyMatch(tenantInfo1 -> {
                return tenantInfo1.getStatus().equals(DictUtils.getDictValue("sys_tenant_status", "生效中"));
            });
        }
        Preconditions.checkState(!exists, "该接收能量地址已有一笔正在生效中的业务,请勿重复添加");

        Long price = tenantInfo.getPrice();
        Long transferCount = tenantInfo.getTransferCount();

        long exchangeAmount = price * transferCount;
        tenantInfo.setExchangeAmount(exchangeAmount);
        tenantInfo.setIsPaid(UserConstants.NO);


        tenantInfo.setDelegatedDays(0L);

        Long userId = ShiroUtils.getUserId();
        tenantInfo.setUserId(userId.toString());

        String loginName = ShiroUtils.getLoginName();
        tenantInfo.setFcd(new Date());
        tenantInfo.setFcu(loginName);
        String status = DictUtils.getDictValue("sys_tenant_status", "生效中");
        tenantInfo.setStatus(status);

        tenantInfo.setTotalCountUsed(0L);

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
    public int updateTenantInfo(TenantInfo tenantInfo) {
        Long price = tenantInfo.getPrice();
        Long transferCount = tenantInfo.getTransferCount();
        Long period = tenantInfo.getPeriod();

        long exchangeAmount = price * transferCount * period;
        tenantInfo.setExchangeAmount(exchangeAmount);

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
    public int deleteTenantInfoByIdTenantInfos(String idTenantInfos) {
        return tenantInfoMapper.deleteTenantInfoByIdTenantInfos(Convert.toStrArray(idTenantInfos));
    }

    /**
     * 删除租户信息
     *
     * @param idTenantInfo 租户主键
     * @return 结果
     */
    @Override
    public int deleteTenantInfoByIdTenantInfo(Long idTenantInfo) {
        return tenantInfoMapper.deleteTenantInfoByIdTenantInfo(idTenantInfo);
    }

    @Override
    public int activeDataTenantInfoByIdTenantInfos(String idTenantInfos) throws Exception {
        String[] idArray = Convert.toStrArray(idTenantInfos);
        for (String id : idArray) {
            TenantInfo tenantInfo = tenantInfoMapper.selectTenantInfoByIdTenantInfo(Long.valueOf(id));

//            Long totalCountUsed = tenantInfo.getTotalCountUsed();
//
//            Preconditions.checkState(totalCountUsed == 0, "该接收能量地址已在任务中,请勿重复发起");

            String status = DictUtils.getDictValue("sys_tenant_status", "生效中");
            Preconditions.checkState(status.equals(tenantInfo.getStatus()), "该租户不是生效中状态,不能发起委托任务");

            String dictValue = DictUtils.getDictValue("sys_delegate_status", "已委托");
            String sysEnergyBusiType = DictUtils.getDictValue("sys_energy_busi_type", "天数套餐");
            TrxExchangeInfo trxExchangeInfo = TrxExchangeInfo.builder().fromAddress(tenantInfo.getReceiverAddress())
                    .delegateStatus(dictValue)
                    .energyBusiType(sysEnergyBusiType)
                    .build();

            List<TrxExchangeInfoVO> trxExchangeInfos = trxExchangeInfoService.selectTrxExchangeInfoList(trxExchangeInfo);


            Preconditions.checkState(CollectionUtil.isEmpty(trxExchangeInfos), "该接收能量地址已在任务中,请勿重复发起");

            doDelegateEnergy(tenantInfo, null);
        }

        return 1;
    }

    @Override
    public void doDelegateEnergy(TenantInfo tenantInfo, String userName) throws Exception {
        String accountAddress = null;
        String monitorAddress = null;
        if (StringUtils.isNotEmpty(tenantInfo.getMonitorAddress())) {
            MonitorAddressInfo monitorAddressInfoExample = new MonitorAddressInfo();
            monitorAddressInfoExample.setMonitorAddress(tenantInfo.getMonitorAddress());
            monitorAddressInfoExample.setIsValid(UserConstants.YES);
            List<MonitorAddressInfo> monitorAddressInfoList = monitorAddressInfoMapper.selectMonitorAddressInfoList(monitorAddressInfoExample);
            Preconditions.checkState(CollectionUtil.isNotEmpty(monitorAddressInfoList), "监听地址不存在或者已失效,无法再次委托能量");

            MonitorAddressInfo monitorAddressInfo = monitorAddressInfoList.get(0);
            accountAddress = monitorAddressInfo.getAccountAddress();

            monitorAddress = monitorAddressInfo.getMonitorAddress();
        } else {
            AccountAddressInfo accountAddressInfo = new AccountAddressInfo();
            accountAddressInfo.setIsValid("Y");
            List<AccountAddressInfo> accountAddressInfoList = accountAddressInfoMapper.selectAccountAddressInfoList(accountAddressInfo);
            Preconditions.checkState(CollectionUtil.isNotEmpty(accountAddressInfoList), "无有效的出账地址无法委托能量");

            accountAddress = accountAddressInfoList.get(0).getAddress();

        }


        doDelegateEnergy(tenantInfo, userName, accountAddress, monitorAddress);

        tenantInfo.setDelegatedDays(tenantInfo.getDelegatedDays() + 1);
        tenantInfo.setTotalCountUsed(0L);
        tenantInfo.setLcd(new Date());
        tenantInfo.setLcu("system");
        tenantInfoMapper.updateTenantInfo(tenantInfo);
    }

    @Override
    public void doDelegateEnergy(TenantInfo tenantInfo, String userName, String accountAddress, String monitorAddress) throws Exception {
        TrxExchange trxExchange = new TrxExchange();
        trxExchange.setFromAddress(tenantInfo.getReceiverAddress());

        trxExchange.setAccountAddress(accountAddress);

        trxExchange.setTransferNumber(2L);

        trxExchange.setPrice(tenantInfo.getPrice());

        trxExchange.setMonitorAddress(monitorAddress);

//        long between = DateUtil.between(DateUtil.date(), DateUtil.endOfDay(DateUtil.date()), DateUnit.HOUR);
        trxExchange.setLockNum(999L);

        trxExchange.setEnergyBusiType(tenantInfo.getEnergyBusiType());
        trxExchange.setResourceCode(Common.ResourceCode.ENERGY.name());
        trxExchange.setCalcRule(tenantInfo.getCalcRule());

        userName = userName == null ? ShiroUtils.getLoginName() : userName;

        trxExchangeInfoService.delegate(trxExchange, true, userName);
    }

    @Override
    public List<TenantInfo> selectTenantInfoListNotExistsInExchange(TenantInfo tenantInfoExample) {
        return tenantInfoMapper.selectTenantInfoListNotExistsInExchange(tenantInfoExample);
    }

}
