package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.core.domain.entity.AccountAddressInfo;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.encrpt.Dt;
import com.ruoyi.system.domain.vo.TransactionLogVO;
import com.ruoyi.system.domain.vo.TronInfoVO;
import com.ruoyi.system.handler.Usdt2TrxTransferHandler;
import com.ruoyi.system.mapper.TrxExchangeInfoMapper;
import com.ruoyi.system.service.IAccountAddressInfoService;
import com.ruoyi.system.service.IApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.contract.Contract;
import org.tron.trident.core.contract.Trc20Contract;
import org.tron.trident.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ApiServiceImpl implements IApiService {
    private static final Logger log = LoggerFactory.getLogger(ApiServiceImpl.class);
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
                oneUsdtToTrx = usdt2TrxTransferHandler.getOneUsdtToTrx().getFirst();
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

    @Override
    public String transferusdt(String usdtAmount) throws Exception {
        AccountAddressInfo accountAddressInfo = accountAddressInfoService.selectAccountAddressInfoAll("usdt2Trx").get(0);

        String decryptPrivateKey = Dt.decrypt(accountAddressInfo.getEncryptPrivateKey(), accountAddressInfo.getEncryptKey());

        ApiWrapper apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey, "1608bf34-16ea-4c91-8432-99513336f391");

        Contract contract = apiWrapper.getContract("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
        Trc20Contract token = new Trc20Contract(contract, accountAddressInfo.getAddress()
                , apiWrapper);

        BigInteger sumAmountValue = Convert.toSun(usdtAmount, Convert.Unit.TRX).toBigInteger();
        log.info("sumAmountValue:{}", sumAmountValue);
        BigInteger fee = Convert.toSun("100", Convert.Unit.TRX).toBigInteger();
        log.info("fee:{}", fee);
        String txid = token.transfer("TTPH84DVkQ2Z2anFNvMeQDvmk48uDRZBXp",sumAmountValue.longValue(),0,"备注",fee.longValue());

        return txid;
    }

}
