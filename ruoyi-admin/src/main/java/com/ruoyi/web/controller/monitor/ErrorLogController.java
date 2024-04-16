package com.ruoyi.web.controller.monitor;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.IErrorLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 错误日志Controller
 *
 * @author dorion
 * @date 2024-04-14
 */
@Controller
@RequestMapping("/monitor/err")
public class ErrorLogController extends BaseController
{
    private String prefix = "monitor/err";

    @Autowired
    private IErrorLogService errorLogService;

    @RequiresPermissions("monitor:err:view")
    @GetMapping()
    public String err()
    {
        return prefix + "/err";
    }

    /**
     * 查询错误日志列表
     */
    @RequiresPermissions("monitor:err:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ErrorLog errorLog)
    {
        startPage();
        List<ErrorLog> list = errorLogService.selectErrorLogList(errorLog);
        return getDataTable(list);
    }

    /**
     * 导出错误日志列表
     */
    @RequiresPermissions("monitor:err:export")
    @Log(title = "错误日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ErrorLog errorLog)
    {
        List<ErrorLog> list = errorLogService.selectErrorLogList(errorLog);
        ExcelUtil<ErrorLog> util = new ExcelUtil<ErrorLog>(ErrorLog.class);
        return util.exportExcel(list, "错误日志数据");
    }

    /**
     * 新增错误日志
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存错误日志
     */
    @RequiresPermissions("monitor:err:add")
    @Log(title = "错误日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ErrorLog errorLog)
    {
        return toAjax(errorLogService.insertErrorLog(errorLog));
    }

    /**
     * 修改错误日志
     */
    @RequiresPermissions("monitor:err:edit")
    @GetMapping("/edit/{idErrorLog}")
    public String edit(@PathVariable("idErrorLog") Long idErrorLog, ModelMap mmap)
    {
        ErrorLog errorLog = errorLogService.selectErrorLogByIdErrorLog(idErrorLog);
        mmap.put("errorLog", errorLog);
        return prefix + "/edit";
    }

    /**
     * 修改保存错误日志
     */
    @RequiresPermissions("monitor:err:edit")
    @Log(title = "错误日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ErrorLog errorLog)
    {
        return toAjax(errorLogService.updateErrorLog(errorLog));
    }

    /**
     * 删除错误日志
     */
    @RequiresPermissions("monitor:err:remove")
    @Log(title = "错误日志", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(errorLogService.deleteErrorLogByIdErrorLogs(ids));
    }
}
