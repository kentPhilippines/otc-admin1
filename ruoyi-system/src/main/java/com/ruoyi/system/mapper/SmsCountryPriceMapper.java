package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.CountryTbl;
import com.ruoyi.common.core.domain.entity.SmsCountryPrice;

import java.util.List;


/**
 * 群发国家单价配置Mapper接口
 *
 * @author dorion
 * @date 2024-07-21
 */
public interface SmsCountryPriceMapper {
    /**
     * 查询群发国家单价配置
     *
     * @param idSmsCountryPrice 群发国家单价配置主键
     * @return 群发国家单价配置
     */
    public SmsCountryPrice selectSmsCountryPriceByIdSmsCountryPrice(Long idSmsCountryPrice);

    /**
     * 查询群发国家单价配置列表
     *
     * @param smsCountryPrice 群发国家单价配置
     * @return 群发国家单价配置集合
     */
    public List<SmsCountryPrice> selectSmsCountryPriceList(SmsCountryPrice smsCountryPrice);

    /**
     * 新增群发国家单价配置
     *
     * @param smsCountryPrice 群发国家单价配置
     * @return 结果
     */
    public int insertSmsCountryPrice(SmsCountryPrice smsCountryPrice);

    /**
     * 修改群发国家单价配置
     *
     * @param smsCountryPrice 群发国家单价配置
     * @return 结果
     */
    public int updateSmsCountryPrice(SmsCountryPrice smsCountryPrice);

    /**
     * 删除群发国家单价配置
     *
     * @param idSmsCountryPrice 群发国家单价配置主键
     * @return 结果
     */
    public int deleteSmsCountryPriceByIdSmsCountryPrice(Long idSmsCountryPrice);

    /**
     * 批量删除群发国家单价配置
     *
     * @param idSmsCountryPrices 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSmsCountryPriceByIdSmsCountryPrices(String[] idSmsCountryPrices);

    List<CountryTbl> selectAllCountries();

    CountryTbl selectCountryById(Long countryId);
}
