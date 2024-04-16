package com.ruoyi.web.controller.account;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.FreezeBalanceInfo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.IAccountAddressInfoService;
import com.ruoyi.system.service.IFreezeBalanceInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 抵押流水记录Controller
 * 
 * @author dorion
 * @date 2024-04-13
 */
@Controller
@RequestMapping("/account/freeze")
public class FreezeBalanceInfoController extends BaseController
{
    private String prefix = "account/freeze";

    @Autowired
    private IFreezeBalanceInfoService freezeBalanceInfoService;

    @Autowired
    private IAccountAddressInfoService accountAddressInfoService;

    @RequiresPermissions("account:freeze:view")
    @GetMapping()
    public String freeze()
    {
        return prefix + "/freeze";
    }

    /**
     * 查询抵押流水记录列表
     */
    @RequiresPermissions("account:freeze:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FreezeBalanceInfo freezeBalanceInfo)
    {
        startPage();
        List<FreezeBalanceInfo> list = freezeBalanceInfoService.selectFreezeBalanceInfoList(freezeBalanceInfo);
        return getDataTable(list);
    }

    /**
     * 导出抵押流水记录列表
     */
    @RequiresPermissions("account:freeze:export")
    @Log(title = "抵押流水记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FreezeBalanceInfo freezeBalanceInfo)
    {
        List<FreezeBalanceInfo> list = freezeBalanceInfoService.selectFreezeBalanceInfoList(freezeBalanceInfo);
        ExcelUtil<FreezeBalanceInfo> util = new ExcelUtil<FreezeBalanceInfo>(FreezeBalanceInfo.class);
        return util.exportExcel(list, "抵押流水记录数据");
    }

    /**
     * 新增抵押流水记录
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("accountAddressList", accountAddressInfoService.selectAccountAddressInfoAll());
        return prefix + "/add";
    }

    /**
     * 新增保存抵押流水记录
     */
    @RequiresPermissions("account:freeze:add")
    @Log(title = "抵押流水记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FreezeBalanceInfo freezeBalanceInfo) throws Exception {
        return toAjax(freezeBalanceInfoService.insertFreezeBalanceInfo(freezeBalanceInfo));
    }

    /**
     * 修改抵押流水记录
     */
    @RequiresPermissions("account:freeze:edit")
    @GetMapping("/edit/{idFreezeBalanceInfo}")
    public String edit(@PathVariable("idFreezeBalanceInfo") Long idFreezeBalanceInfo, ModelMap mmap)
    {
        FreezeBalanceInfo freezeBalanceInfo = freezeBalanceInfoService.selectFreezeBalanceInfoByIdFreezeBalanceInfo(idFreezeBalanceInfo);
        mmap.put("freezeBalanceInfo", freezeBalanceInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存抵押流水记录
     */
    @RequiresPermissions("account:freeze:edit")
    @Log(title = "抵押流水记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FreezeBalanceInfo freezeBalanceInfo)
    {
        return toAjax(freezeBalanceInfoService.updateFreezeBalanceInfo(freezeBalanceInfo));
    }

    /**
     * 删除抵押流水记录
     */
    @RequiresPermissions("account:freeze:remove")
    @Log(title = "抵押流水记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(freezeBalanceInfoService.deleteFreezeBalanceInfoByIdFreezeBalanceInfos(ids));
    }
}
