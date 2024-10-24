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
import com.ruoyi.system.domain.MedWithdrawal;
import com.ruoyi.system.service.IMedWithdrawalService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户提现订单Controller
 * 
 * @author kkkkkk
 * @date 2024-10-23
 */
@Controller
@RequestMapping("/system/withdrawal")
public class MedWithdrawalController extends BaseController
{
    private String prefix = "system/withdrawal";

    @Autowired
    private IMedWithdrawalService medWithdrawalService;

    @RequiresPermissions("system:withdrawal:view")
    @GetMapping()
    public String withdrawal()
    {
        return prefix + "/withdrawal";
    }

    /**
     * 查询用户提现订单列表
     */
    @RequiresPermissions("system:withdrawal:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MedWithdrawal medWithdrawal)
    {
        startPage();
        List<MedWithdrawal> list = medWithdrawalService.selectMedWithdrawalList(medWithdrawal);
        return getDataTable(list);
    }

    /**
     * 导出用户提现订单列表
     */
    @RequiresPermissions("system:withdrawal:export")
    @Log(title = "用户提现订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MedWithdrawal medWithdrawal)
    {
        List<MedWithdrawal> list = medWithdrawalService.selectMedWithdrawalList(medWithdrawal);
        ExcelUtil<MedWithdrawal> util = new ExcelUtil<MedWithdrawal>(MedWithdrawal.class);
        return util.exportExcel(list, "用户提现订单数据");
    }

    /**
     * 新增用户提现订单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户提现订单
     */
    @RequiresPermissions("system:withdrawal:add")
    @Log(title = "用户提现订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MedWithdrawal medWithdrawal)
    {
        return toAjax(medWithdrawalService.insertMedWithdrawal(medWithdrawal));
    }

    /**
     * 修改用户提现订单
     */
    @RequiresPermissions("system:withdrawal:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        MedWithdrawal medWithdrawal = medWithdrawalService.selectMedWithdrawalById(id);
        mmap.put("medWithdrawal", medWithdrawal);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户提现订单
     */
    @RequiresPermissions("system:withdrawal:edit")
    @Log(title = "用户提现订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MedWithdrawal medWithdrawal)
    {
        return toAjax(medWithdrawalService.updateMedWithdrawal(medWithdrawal));
    }

    /**
     * 删除用户提现订单
     */
    @RequiresPermissions("system:withdrawal:remove")
    @Log(title = "用户提现订单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(medWithdrawalService.deleteMedWithdrawalByIds(ids));
    }
}
