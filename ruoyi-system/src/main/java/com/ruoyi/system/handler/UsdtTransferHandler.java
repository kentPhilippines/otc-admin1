package com.ruoyi.system.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Preconditions;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.common.core.domain.entity.UsdtExchangeInfo;
import com.ruoyi.system.api.IOkxApi;
import com.ruoyi.system.api.ITronApi;
import com.ruoyi.system.api.entity.okx.OkxResponse;
import com.ruoyi.system.api.enums.ContractType;
import com.ruoyi.system.api.enums.Symbol;
import com.ruoyi.system.dto.Data;
import com.ruoyi.system.dto.Token_info;
import com.ruoyi.system.dto.TronGridResponse;
import com.ruoyi.system.mapper.UsdtExchangeInfoMapper;
import com.ruoyi.system.service.IAccountAddressInfoService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.util.AddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Response;

import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class UsdtTransferHandler {

    @Autowired
    private ITronApi tronApi;
    @Autowired
    private IOkxApi okxApi;

    @Autowired
    private IAccountAddressInfoService accountAddressInfoService;

    @Autowired
    private UsdtExchangeInfoMapper usdtExchangeInfoMapper;
    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    public void doMonitorUsdtTransfer(MonitorAddressInfo monitorAddressInfo) throws Exception {

        String monitorAddress = monitorAddressInfo.getMonitorAddress();
        String apiKey = monitorAddressInfo.getApiKey();
        TronGridResponse tronGridResponse = tronApi.getTronGridResponse(monitorAddress, apiKey);

        List<Data> dataList = tronGridResponse.getData();
        if (CollectionUtil.isEmpty(dataList)) {
            return;
        }


        //获取欧易费率
        BigDecimal oneUsdtToTrx = getOneUsdtToTrx();


        for (Data data : dataList) {
            doMonitorUsdtTransferByData(monitorAddressInfo, data, oneUsdtToTrx, apiKey);
        }

    }

    private void doMonitorUsdtTransferByData(MonitorAddressInfo monitorAddressInfo, Data data, BigDecimal oneUsdtToTrx, String apiKey) throws Exception {
        BigDecimal transferValue = getTransferValue(data);
        if (transferValue == null) return;

        BigDecimal trxValue = transferValue.multiply(oneUsdtToTrx);

        String accountAddress = monitorAddressInfo.getAccountAddress();
        String decryptPrivateKey = accountAddressInfoService.getDecryptPrivateKey(accountAddress);

        //转账地址
        String from = data.getFrom();
        //接收地址
        String dataTo = data.getTo();
        String transactionId = data.getTransaction_id();

        RLock lock = redissonClient.getLock("lock_" + transactionId);
        try {
            boolean res = lock.tryLock(3, 50, TimeUnit.SECONDS);
            if (!res) {
                //有其他程序正在处理则不需要再处理
                return;
            }

            doTransferUsdtAndStore(oneUsdtToTrx, apiKey, decryptPrivateKey, accountAddress, from, trxValue, dataTo, transactionId, transferValue);

            redisTemplate.opsForValue().set("transfer_USDT_" + transactionId, transactionId, 1, TimeUnit.DAYS);
        }finally {
            if (lock.isLocked()) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }

    public void doTransferUsdtAndStore(BigDecimal oneUsdtToTrx, String apiKey, String decryptPrivateKey, String accountAddress, String from, BigDecimal trxValue, String dataTo, String transactionId, BigDecimal transferValue) throws IllegalException {

        String systronApiSwitch = configService.selectConfigByKey("sys.tron.api");

        String txId = null;
        if (UserConstants.YES.equals(systronApiSwitch)) {

            ApiWrapper apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey, apiKey);

            //转账
            Response.TransactionExtention transfer = apiWrapper.transfer(accountAddress, from, trxValue.movePointRight(6).longValue());
            //签名
            Chain.Transaction transaction = apiWrapper.signTransaction(transfer);
            //广播
            txId = apiWrapper.broadcastTransaction(transaction);
        }


        UsdtExchangeInfo usdtExchangeInfo = new UsdtExchangeInfo();
        usdtExchangeInfo.setFromAddress(AddressUtil.hexToBase58(from))
                .setToAddress(AddressUtil.hexToBase58(dataTo))
                .setAccountAddress(accountAddress)
                .setUsdtTxId(transactionId)
                .setUsdtAmount(transferValue)
                .setTrxAmount(trxValue)
                .setExchangeRate(oneUsdtToTrx)
                .setOrginalExchangeRate(oneUsdtToTrx)
                .setTrxTxId(txId);

        usdtExchangeInfoMapper.insertUsdtExchangeInfo(usdtExchangeInfo);
    }

    /**
     * 获取对方转账的U数量
     *
     * @param data
     * @return
     */
    private BigDecimal getTransferValue(Data data) {
        String transFerName = ContractType.Transfer.name();
        if (!transFerName.equals(data.getType())) {
            log.warn("未知交易类型:{},交易数据:{}", data.getType(), JSONUtil.toJsonStr(data));
            return null;
        }


        Token_info tokenInfo = data.getToken_info();

        if (!Symbol.USDT.name().equals(tokenInfo.getSymbol())) {
            log.warn("未知交易币种:{},交易数据:{}", tokenInfo.getSymbol(), JSONUtil.toJsonStr(data));
            return null;
        }
        int decimals = tokenInfo.getDecimals();


        //转账金额
        BigDecimal transferValue = new BigDecimal(data.getValue()).movePointLeft(decimals);

        if (transferValue.compareTo(BigDecimal.ONE) == -1) {
            log.warn("交易金额小于1,交易数据:{}", JSONUtil.toJsonStr(data));
            return null;
        }
        return transferValue;
    }

    /**
     * 获取欧易费率 1U
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public BigDecimal getOneUsdtToTrx() throws NoSuchAlgorithmException, InvalidKeyException {
        OkxResponse oksResponse = okxApi.getSingleTickerOkxResponse();
        Preconditions.checkNotNull(oksResponse, "查询费率失败无法兑换");
        String code = oksResponse.getCode();

        Preconditions.checkState("0".equals(code), "okx响应码异常:" + JSONUtil.toJsonStr(oksResponse));

        List<com.ruoyi.system.api.entity.okx.Data> oksResponseDataList = oksResponse.getData();
        Preconditions.checkState(CollectionUtil.isNotEmpty(oksResponseDataList), "okx费率响应为空:" + JSONUtil.toJsonStr(oksResponse));

        log.info("oksResponse:{}",oksResponse);
        String last = oksResponseDataList.get(0).getLast();

        BigDecimal oneUsdtToTrx = BigDecimal.ONE.divide(new BigDecimal(last)).setScale(6, BigDecimal.ROUND_HALF_DOWN);
        return oneUsdtToTrx;
    }


}
