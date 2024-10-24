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
import com.ruoyi.system.domain.MedRecharge;
import com.ruoyi.system.service.IMedRechargeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户充值Controller
 * 
 * @author kkkkkk
 * @date 2024-10-23
 */
@Controller
@RequestMapping("/system/recharge")
public class MedRechargeController extends BaseController
{
    private String prefix = "system/recharge";

    @Autowired
    private IMedRechargeService medRechargeService;

    @RequiresPermissions("system:recharge:view")
    @GetMapping()
    public String recharge()
    {
        return prefix + "/recharge";
    }

    /**
     * 查询用户充值列表
     */
    @RequiresPermissions("system:recharge:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MedRecharge medRecharge)
    {
        startPage();
        List<MedRecharge> list = medRechargeService.selectMedRechargeList(medRecharge);
        return getDataTable(list);
    }

    /**
     * 导出用户充值列表
     */
    @RequiresPermissions("system:recharge:export")
    @Log(title = "用户充值", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MedRecharge medRecharge)
    {
        List<MedRecharge> list = medRechargeService.selectMedRechargeList(medRecharge);
        ExcelUtil<MedRecharge> util = new ExcelUtil<MedRecharge>(MedRecharge.class);
        return util.exportExcel(list, "用户充值数据");
    }

    /**
     * 新增用户充值
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户充值
     */
    @RequiresPermissions("system:recharge:add")
    @Log(title = "用户充值", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MedRecharge medRecharge)
    {
        return toAjax(medRechargeService.insertMedRecharge(medRecharge));
    }

    /**
     * 修改用户充值
     */
    @RequiresPermissions("system:recharge:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        MedRecharge medRecharge = medRechargeService.selectMedRechargeById(id);
        mmap.put("medRecharge", medRecharge);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户充值
     */
    @RequiresPermissions("system:recharge:edit")
    @Log(title = "用户充值", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MedRecharge medRecharge)
    {
        return toAjax(medRechargeService.updateMedRecharge(medRecharge));
    }

    /**
     * 删除用户充值
     */
    @RequiresPermissions("system:recharge:remove")
    @Log(title = "用户充值", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(medRechargeService.deleteMedRechargeByIds(ids));
    }
}
