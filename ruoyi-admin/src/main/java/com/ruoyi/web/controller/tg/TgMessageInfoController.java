package com.ruoyi.web.controller.tg;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.TgMessageInfo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ITgMessageInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TG消息管理Controller
 * 
 * @author dorion
 * @date 2024-07-12
 */
@Controller
@RequestMapping("/tg/msg")
public class TgMessageInfoController extends BaseController
{
    private String prefix = "tg/msg";

    @Autowired
    private ITgMessageInfoService tgMessageInfoService;

    @RequiresPermissions("tg:msg:view")
    @GetMapping()
    public String msg()
    {
        return prefix + "/msg";
    }

    /**
     * 查询TG消息管理列表
     */
    @RequiresPermissions("tg:msg:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TgMessageInfo tgMessageInfo)
    {
        startPage();
        List<TgMessageInfo> list = tgMessageInfoService.selectTgMessageInfoList(tgMessageInfo);
        return getDataTable(list);
    }

    /**
     * 导出TG消息管理列表
     */
    @RequiresPermissions("tg:msg:export")
    @Log(title = "TG消息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TgMessageInfo tgMessageInfo)
    {
        List<TgMessageInfo> list = tgMessageInfoService.selectTgMessageInfoList(tgMessageInfo);
        ExcelUtil<TgMessageInfo> util = new ExcelUtil<TgMessageInfo>(TgMessageInfo.class);
        return util.exportExcel(list, "TG消息管理数据");
    }

    /**
     * 新增TG消息管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存TG消息管理
     */
    @RequiresPermissions("tg:msg:add")
    @Log(title = "TG消息管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TgMessageInfo tgMessageInfo)
    {
        return toAjax(tgMessageInfoService.insertTgMessageInfo(tgMessageInfo));
    }

    /**
     * 修改TG消息管理
     */
    @RequiresPermissions("tg:msg:edit")
    @GetMapping("/edit/{idTgMessageInfo}")
    public String edit(@PathVariable("idTgMessageInfo") Long idTgMessageInfo, ModelMap mmap)
    {
        TgMessageInfo tgMessageInfo = tgMessageInfoService.selectTgMessageInfoByIdTgMessageInfo(idTgMessageInfo);
        mmap.put("tgMessageInfo", tgMessageInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存TG消息管理
     */
    @RequiresPermissions("tg:msg:edit")
    @Log(title = "TG消息管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TgMessageInfo tgMessageInfo)
    {
        return toAjax(tgMessageInfoService.updateTgMessageInfo(tgMessageInfo));
    }

    /**
     * 删除TG消息管理
     */
    @RequiresPermissions("tg:msg:remove")
    @Log(title = "TG消息管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tgMessageInfoService.deleteTgMessageInfoByIdTgMessageInfos(ids));
    }
}
