package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.core.domain.entity.AccountAddressInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.system.domain.vo.TransactionLogVO;
import com.ruoyi.system.domain.vo.TronInfoVO;
import com.ruoyi.system.handler.Usdt2TrxTransferHandler;
import com.ruoyi.system.mapper.TrxExchangeInfoMapper;
import com.ruoyi.system.service.IAccountAddressInfoService;
import com.ruoyi.system.service.IApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ApiServiceImpl implements IApiService {
    @Autowired
    private IAccountAddressInfoService accountAddressInfoService;
    @Autowired
    private Usdt2TrxTransferHandler usdt2TrxTransferHandler;
    @Autowired
    private TrxExchangeInfoMapper trxExchangeInfoMapper;

    @Override
    public TronInfoVO getTronInfo() throws Exception {
        TronInfoVO tronInfoVO = new TronInfoVO();

        CompletableFuture<Void> queryTrxBalanceFuture = CompletableFuture.runAsync(() -> {
            AccountAddressInfo accountAddressInfoExample = new AccountAddressInfo();
            accountAddressInfoExample.setBusiType(DictUtils.getDictValue("sys_busi_type", "TRX兑能量"));
            List<AccountAddressInfo> accountAddressInfoList = accountAddressInfoService.selectAccountAddressInfoList(accountAddressInfoExample);


            if (CollectionUtil.isNotEmpty(accountAddressInfoList)) {
                AccountAddressInfo accountAddressInfo = accountAddressInfoList.get(0);
                try {
                    accountAddressInfoService.doQueryResourceInfo(accountAddressInfo);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                long trxBalance = accountAddressInfo.getTotalEnergyBalance();
                Long balance = trxBalance + 221000000L;
                tronInfoVO.setEnergyRemaining(balance.toString());
            }
        }).exceptionally(e -> {
            throw new RuntimeException(e);
        });

        CompletableFuture<Void> queryOkxFuture = CompletableFuture.runAsync(() -> {
            BigDecimal oneUsdtToTrx = null;
            try {
                oneUsdtToTrx = usdt2TrxTransferHandler.getOneUsdtToTrx();
//                oneUsdtToTrx = BigDecimal.valueOf(7.53);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            tronInfoVO.setUsdt2TrxPrice(oneUsdtToTrx.toString());
        }).exceptionally(e -> {
            throw new RuntimeException(e);
        });

        CompletableFuture<Void> queryDatabaseFuture = CompletableFuture.runAsync(() -> {
            PageHelper.startPage(0, 10).setReasonable(true);
            List<TransactionLogVO> transactionLogVOList = trxExchangeInfoMapper.selectTransactionLogVO();
            for (TransactionLogVO transactionLogVO : transactionLogVOList) {
                transactionLogVO.setBusiType("TRX => Energy");
                Integer energyCount = Integer.valueOf(transactionLogVO.getCount()) * 32000;
                transactionLogVO.setCount(energyCount.toString());
            }
            tronInfoVO.setTransactionLogList(transactionLogVOList);
        }).exceptionally(e -> {
            throw new RuntimeException(e);
        });

        CompletableFuture.allOf(queryTrxBalanceFuture, queryOkxFuture, queryDatabaseFuture).join();

        return tronInfoVO;
    }
}
