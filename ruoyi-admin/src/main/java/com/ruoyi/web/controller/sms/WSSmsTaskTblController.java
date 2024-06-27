package com.ruoyi.web.controller.sms;


import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SmsTaskTbl;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISmsTaskTblService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WS短信任务配置Controller
 * 
 * @author dorion
 * @date 2024-06-01
 */
@Controller
@RequestMapping("/sms/task/ws")
public class WSSmsTaskTblController extends BaseController
{
    private String prefix = "sms/task/ws";

    @Autowired
    private ISmsTaskTblService smsTaskTblService;

    @RequiresPermissions("sms:task:ws:view")
    @GetMapping()
    public String task()
    {
        return prefix + "/task";
    }

    /**
     * 查询WS短信任务配置列表
     */
    @RequiresPermissions("sms:task:ws:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SmsTaskTbl smsTaskTbl)
    {
        startPage();
        List<SmsTaskTbl> list = smsTaskTblService.selectSmsTaskTblList(smsTaskTbl);
        return getDataTable(list);
    }

    /**
     * 导出WS短信任务配置列表
     */
    @RequiresPermissions("sms:task:ws:export")
    @Log(title = "WS短信任务配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SmsTaskTbl smsTaskTbl)
    {
        List<SmsTaskTbl> list = smsTaskTblService.selectSmsTaskTblList(smsTaskTbl);
        ExcelUtil<SmsTaskTbl> util = new ExcelUtil<SmsTaskTbl>(SmsTaskTbl.class);
        return util.exportExcel(list, "WS短信任务配置数据");
    }

    /**
     * 新增WS短信任务配置
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存WS短信任务配置
     */
    @RequiresPermissions("sms:task:ws:add")
    @Log(title = "WS短信任务配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SmsTaskTbl smsTaskTbl)
    {
        smsTaskTbl.setSmsBusiType("WS");
        return toAjax(smsTaskTblService.insertSmsTaskTbl(smsTaskTbl));
    }

    /**
     * 修改WS短信任务配置
     */
    @RequiresPermissions("sms:task:ws:edit")
    @GetMapping("/edit/{idSmsTask}")
    public String edit(@PathVariable("idSmsTask") Long idSmsTask, ModelMap mmap)
    {
        SmsTaskTbl smsTaskTbl = smsTaskTblService.selectSmsTaskTblByIdSmsTask(idSmsTask);
        mmap.put("smsTaskTbl", smsTaskTbl);
        return prefix + "/edit";
    }

    /**
     * 修改保存WS短信任务配置
     */
    @RequiresPermissions("sms:task:ws:edit")
    @Log(title = "WS短信任务配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SmsTaskTbl smsTaskTbl)
    {
        return toAjax(smsTaskTblService.updateSmsTaskTbl(smsTaskTbl));
    }

    /**
     * 删除WS短信任务配置
     */
    @RequiresPermissions("sms:task:ws:remove")
    @Log(title = "WS短信任务配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(smsTaskTblService.deleteSmsTaskTblByIdSmsTasks(ids));
    }
}
