package com.ruoyi.system.service.impl;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.AccountAddressInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.encrpt.Dt;
import com.ruoyi.system.mapper.AccountAddressInfoMapper;
import com.ruoyi.system.service.IAccountAddressInfoService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.proto.Response;

import java.math.BigDecimal;
import java.util.List;

/**
 * 出账账户Service业务层处理
 *
 * @author dorion
 * @date 2024-04-13
 */
@Service
public class AccountAddressInfoServiceImpl implements IAccountAddressInfoService {
    private static final Logger log = LoggerFactory.getLogger(AccountAddressInfoServiceImpl.class);
    @Autowired
    private AccountAddressInfoMapper accountAddressInfoMapper;

    /**
     * 查询出账账户
     *
     * @param idAccoutAddressInfo 出账账户主键
     * @return 出账账户
     */
    @Override
    public AccountAddressInfo selectAccountAddressInfoByIdAccoutAddressInfo(Long idAccoutAddressInfo) {
        return accountAddressInfoMapper.selectAccountAddressInfoByIdAccoutAddressInfo(idAccoutAddressInfo);
    }

    /**
     * 查询出账账户列表
     *
     * @param accountAddressInfo 出账账户
     * @return 出账账户
     */
    @Override
    public List<AccountAddressInfo> selectAccountAddressInfoList(AccountAddressInfo accountAddressInfo)  {

        return  accountAddressInfoMapper.selectAccountAddressInfoList(accountAddressInfo);
    }

    /**
     * 新增出账账户
     *
     * @param accountAddressInfo 出账账户
     * @return 结果
     */
    @Override
    public int insertAccountAddressInfo(AccountAddressInfo accountAddressInfo) throws Exception {
        String encryptPrivateKey = accountAddressInfo.getEncryptPrivateKey();
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String encrptKey = secureRandom.nextBytes(24).toHex();
        String encrypt = Dt.encrypt(encryptPrivateKey, encrptKey);
        accountAddressInfo.setEncryptPrivateKey(encrypt);
        accountAddressInfo.setEncryptKey(encrptKey);
        String userName = ShiroUtils.getLoginName();
        accountAddressInfo.setFcu(userName);
        accountAddressInfo.setLcu(userName);
        return accountAddressInfoMapper.insertAccountAddressInfo(accountAddressInfo);
    }

    /**
     * 修改出账账户
     *
     * @param accountAddressInfo 出账账户
     * @return 结果
     */
    @Override
    public int updateAccountAddressInfo(AccountAddressInfo accountAddressInfo) throws Exception {
        String encryptPrivateKey = accountAddressInfo.getEncryptPrivateKey();
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String encrptKey = secureRandom.nextBytes(24).toHex();
        String encrypt = Dt.encrypt(encryptPrivateKey, encrptKey);
        accountAddressInfo.setEncryptPrivateKey(encrypt);
        accountAddressInfo.setEncryptKey(encrptKey);
        String userName = ShiroUtils.getLoginName();
        accountAddressInfo.setLcu(userName);
        return accountAddressInfoMapper.updateAccountAddressInfo(accountAddressInfo);
    }

    /**
     * 批量删除出账账户
     *
     * @param idAccoutAddressInfos 需要删除的出账账户主键
     * @return 结果
     */
    @Override
    public int deleteAccountAddressInfoByIdAccoutAddressInfos(String idAccoutAddressInfos) {
        return accountAddressInfoMapper.deleteAccountAddressInfoByIdAccoutAddressInfos(Convert.toStrArray(idAccoutAddressInfos));
    }

    /**
     * 删除出账账户信息
     *
     * @param idAccoutAddressInfo 出账账户主键
     * @return 结果
     */
    @Override
    public int deleteAccountAddressInfoByIdAccoutAddressInfo(Long idAccoutAddressInfo) {
        return accountAddressInfoMapper.deleteAccountAddressInfoByIdAccoutAddressInfo(idAccoutAddressInfo);
    }

    @Override
    public List<AccountAddressInfo> selectAccountAddressInfoAll(String busiType) {
        AccountAddressInfo accountAddressInfo = new AccountAddressInfo();
        accountAddressInfo.setIsValid(UserConstants.YES);
        accountAddressInfo.setBusiType(busiType);
        return accountAddressInfoMapper.selectAccountAddressInfoList(accountAddressInfo);
    }


    /**
     * 获取解密后私钥
     * @param address
     * @return
     * @throws Exception
     */
    @Override
    public String getDecryptPrivateKey(String address) throws Exception {
        AccountAddressInfo accountAddressInfoExample = new AccountAddressInfo();
        accountAddressInfoExample.setAddress(address);
        List<AccountAddressInfo> accountAddressInfoList = accountAddressInfoMapper.selectAccountAddressInfoList(accountAddressInfoExample);
        AccountAddressInfo accountAddressInfo = accountAddressInfoList.get(0);

        String encryptPrivateKey = accountAddressInfo.getEncryptPrivateKey();
        String encryptKey = accountAddressInfo.getEncryptKey();

        //解密获得秘钥
        String decryptPrivateKey = Dt.decrypt(encryptPrivateKey, encryptKey);
        return decryptPrivateKey;
    }

    @Override
    public List<AccountAddressInfo> selectAccountAddressInfoListByResouce(AccountAddressInfo accountAddressInfo) throws Exception {

        List<AccountAddressInfo> accountAddressInfoList = accountAddressInfoMapper.selectAccountAddressInfoList(accountAddressInfo);
        for (AccountAddressInfo addressInfo : accountAddressInfoList) {
            String encryptPrivateKey = addressInfo.getEncryptPrivateKey();
            String encryptKey = addressInfo.getEncryptKey();

            //解密获得秘钥
            String decryptPrivateKey = Dt.decrypt(encryptPrivateKey, encryptKey);
            String tronApiKey = DictUtils.getDictValue("sys_tron_api_key", "synp@outlook");

            ApiWrapper apiWrapper = ApiWrapper.ofMainnet(decryptPrivateKey,tronApiKey);

            String address = addressInfo.getAddress();
            Response.AccountResourceMessage accountResource = apiWrapper.getAccountResource(address);

            //免费带宽使用量
            long freeNetUsed = accountResource.getFreeNetUsed();
            //免费带宽上限
            long freeNetLimit = accountResource.getFreeNetLimit();
            //网络带宽消耗
            long netUsed = accountResource.getNetUsed();
            //网络上限
            long netLimit = accountResource.getNetLimit();
            //能量消耗
            long energyUsed = accountResource.getEnergyUsed();
            //能量上限
            long energyLimit = accountResource.getEnergyLimit();

            long totalNetUsed = freeNetUsed + netUsed;

            long taotalNetLimit = freeNetLimit + netLimit;

            long totalNetBalance = taotalNetLimit - totalNetUsed;
            long totalEnergyBalance = energyLimit - energyUsed;

            addressInfo.setNetResource(totalNetBalance + "/" + taotalNetLimit);
            addressInfo.setEnergyResource(totalEnergyBalance + "/" + energyLimit);

            Response.Account account = apiWrapper.getAccount(address);

            long balance = account.getBalance();

            List<Response.Account.FreezeV2> frozenV2List = account.getFrozenV2List();

            Long totalFrozen = 0L;
            for (Response.Account.FreezeV2 freezeV2 : frozenV2List) {
                totalFrozen += freezeV2.getAmount();

            }
            addressInfo.setTrxBalance(BigDecimal.valueOf(balance).movePointLeft(6));
            addressInfo.setTotalFrozen(BigDecimal.valueOf(totalFrozen).movePointLeft(6));
        }


        return accountAddressInfoList;
    }
}
