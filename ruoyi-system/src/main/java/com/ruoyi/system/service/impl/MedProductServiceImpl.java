package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MedProductMapper;
import com.ruoyi.system.domain.MedProduct;
import com.ruoyi.system.service.IMedProductService;
import com.ruoyi.common.core.text.Convert;

/**
 * 产品Service业务层处理
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
@Service
public class MedProductServiceImpl implements IMedProductService 
{
    @Autowired
    private MedProductMapper medProductMapper;

    /**
     * 查询产品
     * 
     * @param id 产品主键
     * @return 产品
     */
    @Override
    public MedProduct selectMedProductById(Long id)
    {
        return medProductMapper.selectMedProductById(id);
    }

    /**
     * 查询产品列表
     * 
     * @param medProduct 产品
     * @return 产品
     */
    @Override
    public List<MedProduct> selectMedProductList(MedProduct medProduct)
    {
        return medProductMapper.selectMedProductList(medProduct);
    }

    /**
     * 新增产品
     * 
     * @param medProduct 产品
     * @return 结果
     */
    @Override
    public int insertMedProduct(MedProduct medProduct)
    {
        return medProductMapper.insertMedProduct(medProduct);
    }

    /**
     * 修改产品
     * 
     * @param medProduct 产品
     * @return 结果
     */
    @Override
    public int updateMedProduct(MedProduct medProduct)
    {
        return medProductMapper.updateMedProduct(medProduct);
    }

    /**
     * 批量删除产品
     * 
     * @param ids 需要删除的产品主键
     * @return 结果
     */
    @Override
    public int deleteMedProductByIds(String ids)
    {
        return medProductMapper.deleteMedProductByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品信息
     * 
     * @param id 产品主键
     * @return 结果
     */
    @Override
    public int deleteMedProductById(Long id)
    {
        return medProductMapper.deleteMedProductById(id);
    }
}
