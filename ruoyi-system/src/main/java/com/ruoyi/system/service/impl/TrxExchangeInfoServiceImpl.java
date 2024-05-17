package com.ruoyi.system.service.impl;


import com.ruoyi.common.core.domain.entity.TrxExchangeInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.TrxExchange;
import com.ruoyi.system.domain.TrxExchangeMonitorAccountInfo;
import com.ruoyi.system.domain.vo.TrxExchangeInfoVO;
import com.ruoyi.system.handler.TRX2EneryTransferHandler;
import com.ruoyi.system.mapper.ErrorLogMapper;
import com.ruoyi.system.mapper.TenantInfoMapper;
import com.ruoyi.system.mapper.TrxExchangeInfoMapper;
import com.ruoyi.system.service.IAccountAddressInfoService;
import com.ruoyi.system.service.ITrxExchangeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tron.trident.core.ApiWrapper;

import java.util.List;

/**
 * trx兑能量记录Service业务层处理
 *
 * @author dorion
 * @date 2024-04-13
 */
@Service
@Slf4j
public class TrxExchangeInfoServiceImpl implements ITrxExchangeInfoService {
    @Autowired
    private TrxExchangeInfoMapper trxExchangeInfoMapper;


    @Autowired
    private IAccountAddressInfoService accountAddressInfoService;

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ErrorLogMapper errorLogMapper;

    @Autowired
    private TenantInfoMapper tenantInfoMapper;

    @Autowired
    private TRX2EneryTransferHandler trx2EneryTransferHandler;


    /**
     * 查询trx兑能量记录
     *
     * @param idTrxExchangeInfo trx兑能量记录主键
     * @return trx兑能量记录
     */
    @Override
    public TrxExchangeInfo selectTrxExchangeInfoByIdTrxExchangeInfo(Long idTrxExchangeInfo) {
        return trxExchangeInfoMapper.selectTrxExchangeInfoByIdTrxExchangeInfo(idTrxExchangeInfo);
    }

    /**
     * 查询trx兑能量记录列表
     *
     * @param trxExchangeInfo trx兑能量记录
     * @return trx兑能量记录
     */
    @Override
    public List<TrxExchangeInfoVO> selectTrxExchangeInfoList(TrxExchangeInfo trxExchangeInfo) {
//        return trxExchangeInfoMapper.selectTrxExchangeInfoList(trxExchangeInfo);
        return trxExchangeInfoMapper.selectTrxExchangeInfoAndMonitorNameList(trxExchangeInfo);
    }

    /**
     * 新增trx兑能量记录
     *
     * @param trxExchangeInfo trx兑能量记录
     * @return 结果
     */
    @Override
    public int insertTrxExchangeInfo(TrxExchangeInfo trxExchangeInfo) {
        String userName = ShiroUtils.getSysUser().getUserName();
        trxExchangeInfo.setFcu(userName);
        trxExchangeInfo.setLcu(userName);
        return trxExchangeInfoMapper.insertTrxExchangeInfo(trxExchangeInfo);
    }

    /**
     * 修改trx兑能量记录
     *
     * @param trxExchangeInfo trx兑能量记录
     * @return 结果
     */
    @Override
    public int updateTrxExchangeInfo(TrxExchangeInfo trxExchangeInfo) {
        String userName = ShiroUtils.getSysUser().getUserName();
        trxExchangeInfo.setLcu(userName);
        return trxExchangeInfoMapper.updateTrxExchangeInfo(trxExchangeInfo);
    }

    /**
     * 批量删除trx兑能量记录
     *
     * @param idTrxExchangeInfos 需要删除的trx兑能量记录主键
     * @return 结果
     */
    @Override
    public int deleteTrxExchangeInfoByIdTrxExchangeInfos(String idTrxExchangeInfos) {
        return trxExchangeInfoMapper.deleteTrxExchangeInfoByIdTrxExchangeInfos(Convert.toStrArray(idTrxExchangeInfos));
    }

    /**
     * 删除trx兑能量记录信息
     *
     * @param idTrxExchangeInfo trx兑能量记录主键
     * @return 结果
     */
    @Override
    public int deleteTrxExchangeInfoByIdTrxExchangeInfo(Long idTrxExchangeInfo) {
        return trxExchangeInfoMapper.deleteTrxExchangeInfoByIdTrxExchangeInfo(idTrxExchangeInfo);
    }

    @Override
    public int delegate(TrxExchange trxExchange, Boolean isTenant,  String userName) throws Exception {
        //转账笔数
        Long transferNumber = trxExchange.getTransferNumber();
        //实际锁定周期
        long lockPeriod = trxExchange.getLockNum();

        String fromAddress = trxExchange.getFromAddress();

         userName = userName == null ? ShiroUtils.getLoginName() : userName;

        String sysEnergyBusiType = DictUtils.getDictValue("sys_energy_busi_type", "闪兑套餐");
        if (isTenant) {
            sysEnergyBusiType = DictUtils.getDictValue("sys_energy_busi_type", "天数套餐");
        } else if (lockPeriod > 1 && lockPeriod <= 24 ) {
            sysEnergyBusiType = DictUtils.getDictValue("sys_energy_busi_type", "笔数套餐");
        }
//        if (UserConstants.YES.equals(systronApiSwitch)) {
        String accountAddress = trxExchange.getAccountAddress();
        String decryptPrivateKey = accountAddressInfoService.getDecryptPrivateKey(accountAddress);

        String tronApiKey = DictUtils.getDictValue("sys_tron_api_key", "synp@outlook");

        ApiWrapper apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey, tronApiKey);

        trx2EneryTransferHandler.calcBalanceAndDelegate(null,
                apiWrapper,
                accountAddress,
                transferNumber,
                fromAddress,
                lockPeriod,
                trxExchange.getMonitorAddress(),
                trxExchange.getPrice(),
                sysEnergyBusiType,
                null,
                null,
                userName,
                trxExchange.getResourceCode());
        return 1;
    }


    @Override
    public List<TrxExchangeMonitorAccountInfo> selectTrxExchangeMonitorAccountInfo(TrxExchangeInfo trxExchangeInfoExample) {
        return trxExchangeInfoMapper.selectTrxExchangeMonitorAccountInfo(trxExchangeInfoExample);
    }


}
