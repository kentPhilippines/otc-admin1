package com.ruoyi.web.controller.system;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.json.JSON;
import com.ruoyi.common.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.MedProduct;
import com.ruoyi.system.service.IMedProductService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品Controller
 *
 * @author kkkkkkk
 * @date 2024-10-23
 */
@Controller
@RequestMapping("/system/product")
public class MedProductController extends BaseController {
    private String prefix = "system/product";

    @Autowired
    private IMedProductService medProductService;

    @RequiresPermissions("system:product:view")
    @GetMapping()
    public String product() {
        return prefix + "/product";
    }

    /**
     * 查询产品列表
     */
    @RequiresPermissions("system:product:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MedProduct medProduct) {
        startPage();
        List<MedProduct> list = medProductService.selectMedProductList(medProduct);
        return getDataTable(list);
    }

    /**
     * 导出产品列表
     */
    @RequiresPermissions("system:product:export")
    @Log(title = "产品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MedProduct medProduct) {
        List<MedProduct> list = medProductService.selectMedProductList(medProduct);
        ExcelUtil<MedProduct> util = new ExcelUtil<MedProduct>(MedProduct.class);
        return util.exportExcel(list, "产品数据");
    }

    /**
     * 新增产品
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存产品
     */
    @RequiresPermissions("system:product:add")
    @Log(title = "产品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MedProduct medProduct) {
        return toAjax(medProductService.insertMedProduct(medProduct));
    }

    /**
     * 修改产品
     */
    @RequiresPermissions("system:product:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ObjectMapper objectMapper = new ObjectMapper();
        MedProduct medProduct = medProductService.selectMedProductById(id);

      //      medProduct.setGamesType(objectMapper.writeValueAsString(medProduct.getGamesType()));
        //    medProduct.setControlRules(objectMapper.writeValueAsString(medProduct.getControlRules()));

        mmap.put("medProduct", medProduct);
        return prefix + "/edit";
    }

    /**
     * 修改保存产品
     */
    @RequiresPermissions("system:product:edit")
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MedProduct medProduct) {
        return toAjax(medProductService.updateMedProduct(medProduct));
    }

    /**
     * 删除产品
     */
    @RequiresPermissions("system:product:remove")
    @Log(title = "产品", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(medProductService.deleteMedProductByIds(ids));
    }
}
