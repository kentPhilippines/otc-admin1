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
import com.ruoyi.system.domain.MedFundFlow;
import com.ruoyi.system.service.IMedFundFlowService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户资金流水Controller
 * 
 * @author kkkkkkk
 * @date 2024-10-23
 */
@Controller
@RequestMapping("/system/flow")
public class MedFundFlowController extends BaseController
{
    private String prefix = "system/flow";

    @Autowired
    private IMedFundFlowService medFundFlowService;

    @RequiresPermissions("system:flow:view")
    @GetMapping()
    public String flow()
    {
        return prefix + "/flow";
    }

    /**
     * 查询用户资金流水列表
     */
    @RequiresPermissions("system:flow:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MedFundFlow medFundFlow)
    {
        startPage();
        List<MedFundFlow> list = medFundFlowService.selectMedFundFlowList(medFundFlow);
        return getDataTable(list);
    }

    /**
     * 导出用户资金流水列表
     */
    @RequiresPermissions("system:flow:export")
    @Log(title = "用户资金流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MedFundFlow medFundFlow)
    {
        List<MedFundFlow> list = medFundFlowService.selectMedFundFlowList(medFundFlow);
        ExcelUtil<MedFundFlow> util = new ExcelUtil<MedFundFlow>(MedFundFlow.class);
        return util.exportExcel(list, "用户资金流水数据");
    }

    /**
     * 新增用户资金流水
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户资金流水
     */
    @RequiresPermissions("system:flow:add")
    @Log(title = "用户资金流水", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MedFundFlow medFundFlow)
    {
        return toAjax(medFundFlowService.insertMedFundFlow(medFundFlow));
    }

    /**
     * 修改用户资金流水
     */
    @RequiresPermissions("system:flow:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        MedFundFlow medFundFlow = medFundFlowService.selectMedFundFlowById(id);
        mmap.put("medFundFlow", medFundFlow);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户资金流水
     */
    @RequiresPermissions("system:flow:edit")
    @Log(title = "用户资金流水", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MedFundFlow medFundFlow)
    {
        return toAjax(medFundFlowService.updateMedFundFlow(medFundFlow));
    }

    /**
     * 删除用户资金流水
     */
    @RequiresPermissions("system:flow:remove")
    @Log(title = "用户资金流水", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(medFundFlowService.deleteMedFundFlowByIds(ids));
    }
}
