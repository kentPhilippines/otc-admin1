package com.ruoyi.web.controller.trx2Energy;


import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.TrxExchangeFail;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ITrxExchangeFailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 发送失败记录Controller
 * 
 * @author dorion
 * @date 2024-07-07
 */
@Controller
@RequestMapping("/trx2Energy/fail")
public class TrxExchangeFailController extends BaseController
{
    private String prefix = "trx2Energy/fail";

    @Autowired
    private ITrxExchangeFailService trxExchangeFailService;

    @RequiresPermissions("trx2Energy:fail:view")
    @GetMapping()
    public String fail()
    {
        return prefix + "/fail";
    }

    /**
     * 查询发送失败记录列表
     */
    @RequiresPermissions("trx2Energy:fail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TrxExchangeFail trxExchangeFail)
    {
        startPage();
        List<TrxExchangeFail> list = trxExchangeFailService.selectTrxExchangeFailList(trxExchangeFail);
        return getDataTable(list);
    }

    /**
     * 导出发送失败记录列表
     */
    @RequiresPermissions("trx2Energy:fail:export")
    @Log(title = "发送失败记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TrxExchangeFail trxExchangeFail)
    {
        List<TrxExchangeFail> list = trxExchangeFailService.selectTrxExchangeFailList(trxExchangeFail);
        ExcelUtil<TrxExchangeFail> util = new ExcelUtil<TrxExchangeFail>(TrxExchangeFail.class);
        return util.exportExcel(list, "发送失败记录数据");
    }

    /**
     * 新增发送失败记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存发送失败记录
     */
    @RequiresPermissions("trx2Energy:fail:add")
    @Log(title = "发送失败记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TrxExchangeFail trxExchangeFail)
    {
        return toAjax(trxExchangeFailService.insertTrxExchangeFail(trxExchangeFail));
    }

    /**
     * 修改发送失败记录
     */
    @RequiresPermissions("trx2Energy:fail:edit")
    @GetMapping("/edit/{idTrxExchangeFail}")
    public String edit(@PathVariable("idTrxExchangeFail") Long idTrxExchangeFail, ModelMap mmap)
    {
        TrxExchangeFail trxExchangeFail = trxExchangeFailService.selectTrxExchangeFailByIdTrxExchangeFail(idTrxExchangeFail);
        mmap.put("trxExchangeFail", trxExchangeFail);
        return prefix + "/edit";
    }

    /**
     * 修改保存发送失败记录
     */
    @RequiresPermissions("trx2Energy:fail:edit")
    @Log(title = "发送失败记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TrxExchangeFail trxExchangeFail)
    {
        return toAjax(trxExchangeFailService.updateTrxExchangeFail(trxExchangeFail));
    }

    /**
     * 删除发送失败记录
     */
    @RequiresPermissions("trx2Energy:fail:remove")
    @Log(title = "发送失败记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(trxExchangeFailService.deleteTrxExchangeFailByIdTrxExchangeFails(ids));
    }


    @RequiresPermissions("trx2Energy:fail:edit")
    @Log(title = "批量补偿", businessType = BusinessType.ACTIVE_DATA)
    @PostMapping( "/completeTask")
    @ResponseBody
    public AjaxResult completeTask(String ids) throws Exception {
        return toAjax(trxExchangeFailService.complete(ids));
    }
}
