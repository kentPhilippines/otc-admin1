package com.ruoyi.web.controller.exchange;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.UsdtExchangeInfo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.IUsdtExchangeInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * USDT交易明细Controller
 * 
 * @author dorion
 * @date 2024-04-19
 */
@Controller
@RequestMapping("/exchange/usdt")
public class UsdtExchangeInfoController extends BaseController
{
    private String prefix = "exchange/usdt";

    @Autowired
    private IUsdtExchangeInfoService usdtExchangeInfoService;

    @RequiresPermissions("exchange:usdt:view")
    @GetMapping()
    public String usdt()
    {
        return prefix + "/usdt";
    }

    /**
     * 查询USDT交易明细列表
     */
    @RequiresPermissions("exchange:usdt:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UsdtExchangeInfo usdtExchangeInfo)
    {
        startPage();
        List<UsdtExchangeInfo> list = usdtExchangeInfoService.selectUsdtExchangeInfoList(usdtExchangeInfo);
        return getDataTable(list);
    }

    /**
     * 导出USDT交易明细列表
     */
    @RequiresPermissions("exchange:usdt:export")
    @Log(title = "USDT交易明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(UsdtExchangeInfo usdtExchangeInfo)
    {
        List<UsdtExchangeInfo> list = usdtExchangeInfoService.selectUsdtExchangeInfoList(usdtExchangeInfo);
        ExcelUtil<UsdtExchangeInfo> util = new ExcelUtil<UsdtExchangeInfo>(UsdtExchangeInfo.class);
        return util.exportExcel(list, "USDT交易明细数据");
    }

    /**
     * 新增USDT交易明细
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存USDT交易明细
     */
    @RequiresPermissions("exchange:usdt:add")
    @Log(title = "USDT交易明细", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(UsdtExchangeInfo usdtExchangeInfo) throws Exception {
        return toAjax(usdtExchangeInfoService.insertUsdtExchangeInfo(usdtExchangeInfo));
    }

    /**
     * 修改USDT交易明细
     */
    @RequiresPermissions("exchange:usdt:edit")
    @GetMapping("/edit/{idUsdtExchangeInfo}")
    public String edit(@PathVariable("idUsdtExchangeInfo") Long idUsdtExchangeInfo, ModelMap mmap)
    {
        UsdtExchangeInfo usdtExchangeInfo = usdtExchangeInfoService.selectUsdtExchangeInfoByIdUsdtExchangeInfo(idUsdtExchangeInfo);
        mmap.put("usdtExchangeInfo", usdtExchangeInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存USDT交易明细
     */
    @RequiresPermissions("exchange:usdt:edit")
    @Log(title = "USDT交易明细", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(UsdtExchangeInfo usdtExchangeInfo)
    {
        return toAjax(usdtExchangeInfoService.updateUsdtExchangeInfo(usdtExchangeInfo));
    }

    /**
     * 删除USDT交易明细
     */
    @RequiresPermissions("exchange:usdt:remove")
    @Log(title = "USDT交易明细", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(usdtExchangeInfoService.deleteUsdtExchangeInfoByIdUsdtExchangeInfos(ids));
    }
}
