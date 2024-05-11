package com.ruoyi.web.controller.tg;


import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.TgPremiumOrderInfo;
import com.ruoyi.common.core.domain.vo.TgPremiumOrderInfoMultiVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ITgPremiumOrderInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TG会员充值Controller
 * 
 * @author dorion
 * @date 2024-05-04
 */
@Controller
@RequestMapping("/tg/recharge")
public class TgPremiumOrderInfoController extends BaseController
{
    private String prefix = "tg/recharge";

    @Autowired
    private ITgPremiumOrderInfoService tgPremiumOrderInfoService;

    @RequiresPermissions("tg:recharge:view")
    @GetMapping()
    public String recharge()
    {
        return prefix + "/recharge";
    }

    /**
     * 查询TG会员充值列表
     */
    @RequiresPermissions("tg:recharge:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TgPremiumOrderInfo tgPremiumOrderInfo)
    {
        startPage();
        List<TgPremiumOrderInfo> list = tgPremiumOrderInfoService.selectTgPremiumOrderInfoList(tgPremiumOrderInfo);
        return getDataTable(list);
    }

    /**
     * 导出TG会员充值列表
     */
    @RequiresPermissions("tg:recharge:export")
    @Log(title = "TG会员充值", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TgPremiumOrderInfo tgPremiumOrderInfo)
    {
        List<TgPremiumOrderInfo> list = tgPremiumOrderInfoService.selectTgPremiumOrderInfoList(tgPremiumOrderInfo);
        ExcelUtil<TgPremiumOrderInfo> util = new ExcelUtil<TgPremiumOrderInfo>(TgPremiumOrderInfo.class);
        return util.exportExcel(list, "TG会员充值数据");
    }

    /**
     * 新增TG会员充值
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存TG会员充值
     */
    @RequiresPermissions("tg:recharge:add")
    @Log(title = "TG会员充值", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TgPremiumOrderInfo tgPremiumOrderInfo)
    {
        return toAjax(tgPremiumOrderInfoService.insertTgPremiumOrderInfo(tgPremiumOrderInfo));
    }

    /**
     * 新增保存TG会员充值
     */
    @RequiresPermissions("tg:recharge:add")
    @Log(title = "TG会员批量充值", businessType = BusinessType.INSERT)
    @PostMapping("/addMultiple")
    @ResponseBody
    public AjaxResult addSaveMultiple(TgPremiumOrderInfoMultiVO tgPremiumOrderInfoMultiVO)
    {
        return toAjax(tgPremiumOrderInfoService.insertTgPremiumOrderInfoMultiVO(tgPremiumOrderInfoMultiVO));
    }

    /**
     * 修改TG会员充值
     */
    @RequiresPermissions("tg:recharge:edit")
    @GetMapping("/edit/{idTgPremiumOrderInfo}")
    public String edit(@PathVariable("idTgPremiumOrderInfo") Long idTgPremiumOrderInfo, ModelMap mmap)
    {
        TgPremiumOrderInfo tgPremiumOrderInfo = tgPremiumOrderInfoService.selectTgPremiumOrderInfoByIdTgPremiumOrderInfo(idTgPremiumOrderInfo);
        mmap.put("tgPremiumOrderInfo", tgPremiumOrderInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存TG会员充值
     */
    @RequiresPermissions("tg:recharge:edit")
    @Log(title = "TG会员充值", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TgPremiumOrderInfo tgPremiumOrderInfo)
    {
        return toAjax(tgPremiumOrderInfoService.updateTgPremiumOrderInfo(tgPremiumOrderInfo));
    }

    /**
     * 删除TG会员充值
     */
    @RequiresPermissions("tg:recharge:remove")
    @Log(title = "TG会员充值", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tgPremiumOrderInfoService.deleteTgPremiumOrderInfoByIdTgPremiumOrderInfos(ids));
    }


    /**
     * 删除TG会员充值
     */
    @RequiresPermissions("tg:recharge:add")
    @Log(title = "TG会员充值", businessType = BusinessType.ACTIVE_DATA)
    @PostMapping( "/activeData")
    @ResponseBody
    public AjaxResult activeData(String ids)
    {
        return toAjax(tgPremiumOrderInfoService.activeDataByIdTgPremiumOrderInfos(ids));
    }

}
