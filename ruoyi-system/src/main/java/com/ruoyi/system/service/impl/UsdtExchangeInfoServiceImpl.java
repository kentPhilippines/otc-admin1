package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.base.Preconditions;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.AccountAddressInfo;
import com.ruoyi.common.core.domain.entity.UsdtExchangeInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.system.handler.Usdt2TrxTransferHandler;
import com.ruoyi.system.mapper.AccountAddressInfoMapper;
import com.ruoyi.system.mapper.UsdtExchangeInfoMapper;
import com.ruoyi.system.service.IUsdtExchangeInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * USDT交易明细Service业务层处理
 *
 * @author dorion
 * @date 2024-04-19
 */
@Service
public class UsdtExchangeInfoServiceImpl implements IUsdtExchangeInfoService {
    @Autowired
    private UsdtExchangeInfoMapper usdtExchangeInfoMapper;

    @Autowired
    private Usdt2TrxTransferHandler usdt2TrxTransferHandler;
    @Autowired
    private AccountAddressInfoMapper accountAddressInfoMapper;
    @Autowired
    private AccountAddressInfoServiceImpl accountAddressInfoService;
    @Autowired
    private SysConfigServiceImpl configService;

    /**
     * 查询USDT交易明细
     *
     * @param idUsdtExchangeInfo USDT交易明细主键
     * @return USDT交易明细
     */
    @Override
    public UsdtExchangeInfo selectUsdtExchangeInfoByIdUsdtExchangeInfo(Long idUsdtExchangeInfo) {
        return usdtExchangeInfoMapper.selectUsdtExchangeInfoByIdUsdtExchangeInfo(idUsdtExchangeInfo);
    }

    /**
     * 查询USDT交易明细列表
     *
     * @param usdtExchangeInfo USDT交易明细
     * @return USDT交易明细
     */
    @Override
    public List<UsdtExchangeInfo> selectUsdtExchangeInfoList(UsdtExchangeInfo usdtExchangeInfo) {
        return usdtExchangeInfoMapper.selectUsdtExchangeInfoList(usdtExchangeInfo);
    }

    /**
     * 新增USDT交易明细
     *
     * @param usdtExchangeInfo USDT交易明细
     * @return 结果
     */
    @Override
    public int insertUsdtExchangeInfo(UsdtExchangeInfo usdtExchangeInfo) throws Exception {
        BigDecimal oneUsdtToTrx;
        String systronApiSwitch = configService.selectConfigByKey("sys.tron.api");
        Pair<BigDecimal, BigDecimal> oneUsdtToTrxPair = null;
        if (UserConstants.YES.equals(systronApiSwitch)) {
//            oneUsdtToTrx = usdt2TrxTransferHandler.getOneUsdtToTrx().getFirst();
            oneUsdtToTrxPair = usdt2TrxTransferHandler.getOneUsdtToTrx();
        } else {
            oneUsdtToTrxPair=Pair.of(BigDecimal.TEN,BigDecimal.TEN);
        }


        BigDecimal usdtAmount = usdtExchangeInfo.getUsdtAmount();

        BigDecimal trxValue = usdtAmount.multiply(oneUsdtToTrxPair.getFirst());
        String accountAddress = usdtExchangeInfo.getAccountAddress();
        if (StringUtils.isEmpty(accountAddress)) {
            AccountAddressInfo accountAddressInfoExample = new AccountAddressInfo();
            accountAddressInfoExample.setIsValid("Y");
            accountAddressInfoExample.setBusiType(DictUtils.getDictValue("sys_busi_type", "USDT兑TRX"));
            List<AccountAddressInfo> accountAddressInfoList = accountAddressInfoMapper.selectAccountAddressInfoList(accountAddressInfoExample);
            Preconditions.checkState(CollectionUtil.isNotEmpty(accountAddressInfoList), "无合适的转trx账户,请先设置");
            accountAddress = accountAddressInfoList.get(0).getAddress();
        }

        String decryptPrivateKey = accountAddressInfoService.getDecryptPrivateKey(accountAddress);
        String tronApiKey = DictUtils.getDictValue("sys_tron_api_key", "synp@outlook");

        String fromAddress = usdtExchangeInfo.getFromAddress();
        usdt2TrxTransferHandler.
                doTransferUsdtAndStore(oneUsdtToTrxPair, tronApiKey, decryptPrivateKey, accountAddress, fromAddress, trxValue, null, null, usdtAmount);

        return 1;
    }

    /**
     * 修改USDT交易明细
     *
     * @param usdtExchangeInfo USDT交易明细
     * @return 结果
     */
    @Override
    public int updateUsdtExchangeInfo(UsdtExchangeInfo usdtExchangeInfo) {
        return usdtExchangeInfoMapper.updateUsdtExchangeInfo(usdtExchangeInfo);
    }

    /**
     * 批量删除USDT交易明细
     *
     * @param idUsdtExchangeInfos 需要删除的USDT交易明细主键
     * @return 结果
     */
    @Override
    public int deleteUsdtExchangeInfoByIdUsdtExchangeInfos(String idUsdtExchangeInfos) {
        return usdtExchangeInfoMapper.deleteUsdtExchangeInfoByIdUsdtExchangeInfos(Convert.toStrArray(idUsdtExchangeInfos));
    }

    /**
     * 删除USDT交易明细信息
     *
     * @param idUsdtExchangeInfo USDT交易明细主键
     * @return 结果
     */
    @Override
    public int deleteUsdtExchangeInfoByIdUsdtExchangeInfo(Long idUsdtExchangeInfo) {
        return usdtExchangeInfoMapper.deleteUsdtExchangeInfoByIdUsdtExchangeInfo(idUsdtExchangeInfo);
    }
}
