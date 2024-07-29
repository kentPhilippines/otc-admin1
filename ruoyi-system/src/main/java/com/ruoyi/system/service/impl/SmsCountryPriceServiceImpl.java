package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.CountryTbl;
import com.ruoyi.common.core.domain.entity.SmsCountryPrice;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.mapper.SmsCountryPriceMapper;
import com.ruoyi.system.service.ISmsCountryPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 群发国家单价配置Service业务层处理
 * 
 * @author dorion
 * @date 2024-07-21
 */
@Service
public class SmsCountryPriceServiceImpl implements ISmsCountryPriceService 
{
    @Autowired
    private SmsCountryPriceMapper smsCountryPriceMapper;

    /**
     * 查询群发国家单价配置
     * 
     * @param idSmsCountryPrice 群发国家单价配置主键
     * @return 群发国家单价配置
     */
    @Override
    public SmsCountryPrice selectSmsCountryPriceByIdSmsCountryPrice(Long idSmsCountryPrice)
    {
        return smsCountryPriceMapper.selectSmsCountryPriceByIdSmsCountryPrice(idSmsCountryPrice);
    }

    /**
     * 查询群发国家单价配置列表
     * 
     * @param smsCountryPrice 群发国家单价配置
     * @return 群发国家单价配置
     */
    @Override
    public List<SmsCountryPrice> selectSmsCountryPriceList(SmsCountryPrice smsCountryPrice)
    {
        return smsCountryPriceMapper.selectSmsCountryPriceList(smsCountryPrice);
    }

    /**
     * 新增群发国家单价配置
     * 
     * @param smsCountryPrice 群发国家单价配置
     * @return 结果
     */
    @Override
    public int insertSmsCountryPrice(SmsCountryPrice smsCountryPrice)
    {
        CountryTbl countryTbl = smsCountryPriceMapper.selectCountryById(smsCountryPrice.getCountryId());

        smsCountryPrice.setAreaCode(countryTbl.getAreaCode().toString());
        String loginName = ShiroUtils.getLoginName();
        smsCountryPrice.setCreateBy(loginName);
        smsCountryPrice.setUpdateBy(loginName);
        return smsCountryPriceMapper.insertSmsCountryPrice(smsCountryPrice);
    }

    /**
     * 修改群发国家单价配置
     * 
     * @param smsCountryPrice 群发国家单价配置
     * @return 结果
     */
    @Override
    public int updateSmsCountryPrice(SmsCountryPrice smsCountryPrice)
    {
        CountryTbl countryTbl = smsCountryPriceMapper.selectCountryById(smsCountryPrice.getCountryId());

        smsCountryPrice.setAreaCode(countryTbl.getAreaCode().toString());

        String loginName = ShiroUtils.getLoginName();
        smsCountryPrice.setUpdateBy(loginName);
        return smsCountryPriceMapper.updateSmsCountryPrice(smsCountryPrice);
    }

    /**
     * 批量删除群发国家单价配置
     * 
     * @param idSmsCountryPrices 需要删除的群发国家单价配置主键
     * @return 结果
     */
    @Override
    public int deleteSmsCountryPriceByIdSmsCountryPrices(String idSmsCountryPrices)
    {
        return smsCountryPriceMapper.deleteSmsCountryPriceByIdSmsCountryPrices(Convert.toStrArray(idSmsCountryPrices));
    }

    /**
     * 删除群发国家单价配置信息
     * 
     * @param idSmsCountryPrice 群发国家单价配置主键
     * @return 结果
     */
    @Override
    public int deleteSmsCountryPriceByIdSmsCountryPrice(Long idSmsCountryPrice)
    {
        return smsCountryPriceMapper.deleteSmsCountryPriceByIdSmsCountryPrice(idSmsCountryPrice);
    }

    @Override
    public List<CountryTbl> selectAllCountries() {
        return smsCountryPriceMapper.selectAllCountries();
    }
}
