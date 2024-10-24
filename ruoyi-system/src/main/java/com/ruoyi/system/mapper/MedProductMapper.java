package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.MedProduct;

/**
 * 产品Mapper接口
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
public interface MedProductMapper 
{
    /**
     * 查询产品
     * 
     * @param id 产品主键
     * @return 产品
     */
    public MedProduct selectMedProductById(Long id);

    /**
     * 查询产品列表
     * 
     * @param medProduct 产品
     * @return 产品集合
     */
    public List<MedProduct> selectMedProductList(MedProduct medProduct);

    /**
     * 新增产品
     * 
     * @param medProduct 产品
     * @return 结果
     */
    public int insertMedProduct(MedProduct medProduct);

    /**
     * 修改产品
     * 
     * @param medProduct 产品
     * @return 结果
     */
    public int updateMedProduct(MedProduct medProduct);

    /**
     * 删除产品
     * 
     * @param id 产品主键
     * @return 结果
     */
    public int deleteMedProductById(Long id);

    /**
     * 批量删除产品
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMedProductByIds(String[] ids);
}
