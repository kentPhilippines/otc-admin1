package com.ruoyi.system.service.impl;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.FreezeBalanceInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.mapper.FreezeBalanceInfoMapper;
import com.ruoyi.system.service.IAccountAddressInfoService;
import com.ruoyi.system.service.IFreezeBalanceInfoService;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Common;
import org.tron.trident.proto.Response;

import java.util.List;

/**
 * 抵押流水记录Service业务层处理
 * 
 * @author dorion
 * @date 2024-04-13
 */
@Service
public class FreezeBalanceInfoServiceImpl implements IFreezeBalanceInfoService 
{
    @Autowired
    private FreezeBalanceInfoMapper freezeBalanceInfoMapper;

    @Autowired
    private IAccountAddressInfoService accountAddressInfoService;

    @Autowired
    private ISysConfigService configService;

    /**
     * 查询抵押流水记录
     * 
     * @param idFreezeBalanceInfo 抵押流水记录主键
     * @return 抵押流水记录
     */
    @Override
    public FreezeBalanceInfo selectFreezeBalanceInfoByIdFreezeBalanceInfo(Long idFreezeBalanceInfo)
    {
        return freezeBalanceInfoMapper.selectFreezeBalanceInfoByIdFreezeBalanceInfo(idFreezeBalanceInfo);
    }

    /**
     * 查询抵押流水记录列表
     * 
     * @param freezeBalanceInfo 抵押流水记录
     * @return 抵押流水记录
     */
    @Override
    public List<FreezeBalanceInfo> selectFreezeBalanceInfoList(FreezeBalanceInfo freezeBalanceInfo)
    {
        return freezeBalanceInfoMapper.selectFreezeBalanceInfoList(freezeBalanceInfo);
    }

    /**
     * 新增抵押流水记录
     * 
     * @param freezeBalanceInfo 抵押流水记录
     * @return 结果
     */
    @Override
    public int insertFreezeBalanceInfo(FreezeBalanceInfo freezeBalanceInfo) throws Exception {

        String systronApiSwitch = configService.selectConfigByKey("sys.tron.api");
        if(UserConstants.YES.equals(systronApiSwitch)){
            Long freezeTrxAmount = freezeBalanceInfo.getFreezeTrxAmount();
            String address = freezeBalanceInfo.getAddress();
            String decryptPrivateKey = accountAddressInfoService.getDecryptPrivateKey(address);

            String tronApiKey = DictUtils.getDictValue("sys_tron_api_key", "synp@outlook");
            //发起抵押
            ApiWrapper apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey,tronApiKey);

            Long frozenBalance = freezeTrxAmount * 1000000L;

            Response.TransactionExtention transactionExtention = apiWrapper.freezeBalanceV2(address, frozenBalance, Common.ResourceCode.valueOf(freezeBalanceInfo.getExchangeResourceType()).getNumber());

            //签名
            Chain.Transaction signTransaction = apiWrapper.signTransaction(transactionExtention);
            //广播 交易hash
            String hexTxid = apiWrapper.broadcastTransaction(signTransaction);

            freezeBalanceInfo.setTxId(hexTxid);
        }

        String userName = ShiroUtils.getLoginName();
        freezeBalanceInfo.setFcu(userName);
        freezeBalanceInfo.setLcu(userName);
        return freezeBalanceInfoMapper.insertFreezeBalanceInfo(freezeBalanceInfo);
    }


    /**
     * 修改抵押流水记录
     * 
     * @param freezeBalanceInfo 抵押流水记录
     * @return 结果
     */
    @Override
    public int updateFreezeBalanceInfo(FreezeBalanceInfo freezeBalanceInfo)
    {
        return freezeBalanceInfoMapper.updateFreezeBalanceInfo(freezeBalanceInfo);
    }

    /**
     * 批量删除抵押流水记录
     * 
     * @param idFreezeBalanceInfos 需要删除的抵押流水记录主键
     * @return 结果
     */
    @Override
    public int deleteFreezeBalanceInfoByIdFreezeBalanceInfos(String idFreezeBalanceInfos)
    {
        return freezeBalanceInfoMapper.deleteFreezeBalanceInfoByIdFreezeBalanceInfos(Convert.toStrArray(idFreezeBalanceInfos));
    }

    /**
     * 删除抵押流水记录信息
     * 
     * @param idFreezeBalanceInfo 抵押流水记录主键
     * @return 结果
     */
    @Override
    public int deleteFreezeBalanceInfoByIdFreezeBalanceInfo(Long idFreezeBalanceInfo)
    {
        return freezeBalanceInfoMapper.deleteFreezeBalanceInfoByIdFreezeBalanceInfo(idFreezeBalanceInfo);
    }
}
