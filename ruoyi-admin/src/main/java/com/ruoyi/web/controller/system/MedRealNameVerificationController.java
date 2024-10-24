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
import com.ruoyi.system.domain.MedRealNameVerification;
import com.ruoyi.system.service.IMedRealNameVerificationService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 实名审核Controller
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
@Controller
@RequestMapping("/system/verification")
public class MedRealNameVerificationController extends BaseController
{
    private String prefix = "system/verification";

    @Autowired
    private IMedRealNameVerificationService medRealNameVerificationService;

    @RequiresPermissions("system:verification:view")
    @GetMapping()
    public String verification()
    {
        return prefix + "/verification";
    }

    /**
     * 查询实名审核列表
     */
    @RequiresPermissions("system:verification:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MedRealNameVerification medRealNameVerification)
    {
        startPage();
        List<MedRealNameVerification> list = medRealNameVerificationService.selectMedRealNameVerificationList(medRealNameVerification);
        return getDataTable(list);
    }

    /**
     * 导出实名审核列表
     */
    @RequiresPermissions("system:verification:export")
    @Log(title = "实名审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MedRealNameVerification medRealNameVerification)
    {
        List<MedRealNameVerification> list = medRealNameVerificationService.selectMedRealNameVerificationList(medRealNameVerification);
        ExcelUtil<MedRealNameVerification> util = new ExcelUtil<MedRealNameVerification>(MedRealNameVerification.class);
        return util.exportExcel(list, "实名审核数据");
    }

    /**
     * 新增实名审核
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存实名审核
     */
    @RequiresPermissions("system:verification:add")
    @Log(title = "实名审核", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MedRealNameVerification medRealNameVerification)
    {
        return toAjax(medRealNameVerificationService.insertMedRealNameVerification(medRealNameVerification));
    }

    /**
     * 修改实名审核
     */
    @RequiresPermissions("system:verification:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        MedRealNameVerification medRealNameVerification = medRealNameVerificationService.selectMedRealNameVerificationById(id);
        mmap.put("medRealNameVerification", medRealNameVerification);
        return prefix + "/edit";
    }

    /**
     * 修改保存实名审核
     */
    @RequiresPermissions("system:verification:edit")
    @Log(title = "实名审核", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MedRealNameVerification medRealNameVerification)
    {
        return toAjax(medRealNameVerificationService.updateMedRealNameVerification(medRealNameVerification));
    }

    /**
     * 删除实名审核
     */
    @RequiresPermissions("system:verification:remove")
    @Log(title = "实名审核", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(medRealNameVerificationService.deleteMedRealNameVerificationByIds(ids));
    }
}
