package com.ruoyi.system.service.impl;


import com.ruoyi.common.core.domain.entity.TrxExchangeFail;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.*;
import com.ruoyi.system.handler.TRX2EneryTransferHandler;
import com.ruoyi.system.mapper.TrxExchangeFailMapper;
import com.ruoyi.system.service.IAccountAddressInfoService;
import com.ruoyi.system.service.ITrxExchangeFailService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.tron.trident.core.ApiWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 发送失败记录Service业务层处理
 * 
 * @author dorion
 * @date 2024-07-07
 */
@Service
public class TrxExchangeFailServiceImpl implements ITrxExchangeFailService
{
    private static final Logger log = LoggerFactory.getLogger(TrxExchangeFailServiceImpl.class);
    @Autowired
    private TrxExchangeFailMapper trxExchangeFailMapper;

    @Autowired
    private TRX2EneryTransferHandler trx2EneryTransferHandler;

    @Autowired
    private IAccountAddressInfoService accountAddressInfoService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 查询发送失败记录
     * 
     * @param idTrxExchangeFail 发送失败记录主键
     * @return 发送失败记录
     */
    @Override
    public TrxExchangeFail selectTrxExchangeFailByIdTrxExchangeFail(Long idTrxExchangeFail)
    {
        return trxExchangeFailMapper.selectTrxExchangeFailByIdTrxExchangeFail(idTrxExchangeFail);
    }

    /**
     * 查询发送失败记录列表
     * 
     * @param trxExchangeFail 发送失败记录
     * @return 发送失败记录
     */
    @Override
    public List<TrxExchangeFail> selectTrxExchangeFailList(TrxExchangeFail trxExchangeFail)
    {
        return trxExchangeFailMapper.selectTrxExchangeFailList(trxExchangeFail);
    }

    /**
     * 新增发送失败记录
     * 
     * @param trxExchangeFail 发送失败记录
     * @return 结果
     */
    @Override
    public int insertTrxExchangeFail(TrxExchangeFail trxExchangeFail)
    {
        trxExchangeFail.setCreateTime(DateUtils.getNowDate());
        return trxExchangeFailMapper.insertTrxExchangeFail(trxExchangeFail);
    }

    /**
     * 修改发送失败记录
     * 
     * @param trxExchangeFail 发送失败记录
     * @return 结果
     */
    @Override
    public int updateTrxExchangeFail(TrxExchangeFail trxExchangeFail)
    {
        trxExchangeFail.setUpdateTime(DateUtils.getNowDate());
        return trxExchangeFailMapper.updateTrxExchangeFail(trxExchangeFail);
    }

    /**
     * 批量删除发送失败记录
     * 
     * @param idTrxExchangeFails 需要删除的发送失败记录主键
     * @return 结果
     */
    @Override
    public int deleteTrxExchangeFailByIdTrxExchangeFails(String idTrxExchangeFails)
    {
        return trxExchangeFailMapper.deleteTrxExchangeFailByIdTrxExchangeFails(Convert.toStrArray(idTrxExchangeFails));
    }

    /**
     * 删除发送失败记录信息
     * 
     * @param idTrxExchangeFail 发送失败记录主键
     * @return 结果
     */
    @Override
    public int deleteTrxExchangeFailByIdTrxExchangeFail(Long idTrxExchangeFail)
    {
        return trxExchangeFailMapper.deleteTrxExchangeFailByIdTrxExchangeFail(idTrxExchangeFail);
    }

    @Override
    public int complete(String ids) throws Exception {
        List<String> idsList = Arrays.asList(ids.split(","));

        List<TrxExchangeFail> trxExchangeFails = trxExchangeFailMapper.selectTrxExchangeFailListByIdList(idsList);

        String apiKey = DictUtils.getRandomDictValue("sys_tron_api_key");


        for (TrxExchangeFail trxExchangeFail : trxExchangeFails) {
            String decryptPrivateKey = accountAddressInfoService.getDecryptPrivateKey(trxExchangeFail.getAccountAddress());

            ApiWrapper apiWrapper = ApiWrapper.ofMainnet( decryptPrivateKey,apiKey);
            String txID = trxExchangeFail.getTrxTxId();
            Boolean hasKey = redisTemplate.hasKey("transfer_trx_" + txID);
            if (hasKey) {
                continue;
            }


            RLock lock = redissonClient.getLock("lock_" + txID);
            try {
                boolean res = lock.tryLock(3, 50, TimeUnit.SECONDS);
                if (!res) {
                    //有其他程序正在处理则不需要再处理
                    continue;
                }

                trx2EneryTransferHandler.calcBalanceAndDelegate(txID,
                        apiWrapper,
                        trxExchangeFail.getAccountAddress(),
                        trxExchangeFail.getTranferCount(),
                        trxExchangeFail.getFromAddress(),
                        trxExchangeFail.getLockPeriod(),
                        trxExchangeFail.getToAddress(),
                        trxExchangeFail.getPrice(),
                        trxExchangeFail.getEnergyBusiType(),
                        trxExchangeFail.getTrxAmount(),
                        trxExchangeFail.getTrxAmountUnit(),
                        ShiroUtils.getLoginName(),
                        trxExchangeFail.getResourceCode(), trxExchangeFail.getCalcRule(), null);


            }catch (Exception e){
               log.error("doDelegateResource业务处理异常{},txid:{},exception:", trxExchangeFail.getFromAddress(), txID, e);
               throw new RuntimeException("补偿失败");
            }finally {
                if (lock.isLocked()) {
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
        }

        return idsList.size();
    }
}
