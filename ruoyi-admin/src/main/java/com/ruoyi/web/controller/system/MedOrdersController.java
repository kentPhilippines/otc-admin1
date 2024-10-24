package com.ruoyi.web.controller.system;

import java.util.List;
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
import com.ruoyi.system.domain.MedOrders;
import com.ruoyi.system.service.IMedOrdersService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户交易订单Controller
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
@Controller
@RequestMapping("/system/orders")
public class MedOrdersController extends BaseController
{
    private String prefix = "system/orders";

    @Autowired
    private IMedOrdersService medOrdersService;

    @RequiresPermissions("system:orders:view")
    @GetMapping()
    public String orders()
    {
        return prefix + "/orders";
    }

    /**
     * 查询用户交易订单列表
     */
    @RequiresPermissions("system:orders:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MedOrders medOrders)
    {
        startPage();
        List<MedOrders> list = medOrdersService.selectMedOrdersList(medOrders);
        return getDataTable(list);
    }

    /**
     * 导出用户交易订单列表
     */
    @RequiresPermissions("system:orders:export")
    @Log(title = "用户交易订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MedOrders medOrders)
    {
        List<MedOrders> list = medOrdersService.selectMedOrdersList(medOrders);
        ExcelUtil<MedOrders> util = new ExcelUtil<MedOrders>(MedOrders.class);
        return util.exportExcel(list, "用户交易订单数据");
    }

    /**
     * 新增用户交易订单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户交易订单
     */
    @RequiresPermissions("system:orders:add")
    @Log(title = "用户交易订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MedOrders medOrders)
    {
        return toAjax(medOrdersService.insertMedOrders(medOrders));
    }

    /**
     * 修改用户交易订单
     */
    @RequiresPermissions("system:orders:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        MedOrders medOrders = medOrdersService.selectMedOrdersById(id);
        mmap.put("medOrders", medOrders);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户交易订单
     */
    @RequiresPermissions("system:orders:edit")
    @Log(title = "用户交易订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MedOrders medOrders)
    {
        return toAjax(medOrdersService.updateMedOrders(medOrders));
    }

    /**
     * 删除用户交易订单
     */
    @RequiresPermissions("system:orders:remove")
    @Log(title = "用户交易订单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(medOrdersService.deleteMedOrdersByIds(ids));
    }
}
