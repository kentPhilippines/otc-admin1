package com.ruoyi.web.controller.sms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SmsChannelTbl;
import com.ruoyi.system.service.ISmsChannelTblService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短信渠道管理Controller
 * 
 * @author dorion
 * @date 2024-05-28
 */
@Controller
@RequestMapping("/sms/channel")
public class SmsChannelTblController extends BaseController
{
    private String prefix = "sms/channel";

    @Autowired
    private ISmsChannelTblService smsChannelTblService;

    @RequiresPermissions("sms:channel:view")
    @GetMapping()
    public String channel()
    {
        return prefix + "/channel";
    }

    /**
     * 查询短信渠道管理列表
     */
    @RequiresPermissions("sms:channel:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SmsChannelTbl smsChannelTbl)
    {
        startPage();
        List<SmsChannelTbl> list = smsChannelTblService.selectSmsChannelTblList(smsChannelTbl);
        return getDataTable(list);
    }

    /**
     * 导出短信渠道管理列表
     */
    @RequiresPermissions("sms:channel:export")
    @Log(title = "短信渠道管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SmsChannelTbl smsChannelTbl)
    {
        List<SmsChannelTbl> list = smsChannelTblService.selectSmsChannelTblList(smsChannelTbl);
        ExcelUtil<SmsChannelTbl> util = new ExcelUtil<SmsChannelTbl>(SmsChannelTbl.class);
        return util.exportExcel(list, "短信渠道管理数据");
    }

    /**
     * 新增短信渠道管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存短信渠道管理
     */
    @RequiresPermissions("sms:channel:add")
    @Log(title = "短信渠道管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SmsChannelTbl smsChannelTbl)
    {
        return toAjax(smsChannelTblService.insertSmsChannelTbl(smsChannelTbl));
    }

    /**
     * 修改短信渠道管理
     */
    @RequiresPermissions("sms:channel:edit")
    @GetMapping("/edit/{idSmsChannel}")
    public String edit(@PathVariable("idSmsChannel") Long idSmsChannel, ModelMap mmap)
    {
        SmsChannelTbl smsChannelTbl = smsChannelTblService.selectSmsChannelTblByIdSmsChannel(idSmsChannel);
        mmap.put("smsChannelTbl", smsChannelTbl);
        return prefix + "/edit";
    }

    /**
     * 修改保存短信渠道管理
     */
    @RequiresPermissions("sms:channel:edit")
    @Log(title = "短信渠道管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SmsChannelTbl smsChannelTbl)
    {
        return toAjax(smsChannelTblService.updateSmsChannelTbl(smsChannelTbl));
    }

    /**
     * 删除短信渠道管理
     */
    @RequiresPermissions("sms:channel:remove")
    @Log(title = "短信渠道管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(smsChannelTblService.deleteSmsChannelTblByIdSmsChannels(ids));
    }
}
