package com.ruoyi.web.controller.tg;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.TgMessageInfo;
import com.ruoyi.common.core.domain.entity.TgMessageTask;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ITgMessageInfoService;
import com.ruoyi.system.service.ITgMessageTaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TG消息任务管理Controller
 * 
 * @author dorion
 * @date 2024-07-14
 */
@Controller
@RequestMapping("/tg/task")
public class TgMessageTaskController extends BaseController
{
    private String prefix = "tg/task";

    @Autowired
    private ITgMessageInfoService iTgMessageInfoService;

    @Autowired
    private ITgMessageTaskService tgMessageTaskService;

    @RequiresPermissions("tg:task:view")
    @GetMapping()
    public String task(ModelMap mmap)
    {   mmap.put("topicTgmessageInfoList", iTgMessageInfoService.selectTgMessageInfoList(new TgMessageInfo()));
        return prefix + "/task";
    }

    /**
     * 查询TG消息任务管理列表
     */
    @RequiresPermissions("tg:task:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TgMessageTask tgMessageTask)
    {
        startPage();
        List<TgMessageTask> list = tgMessageTaskService.selectTgMessageTaskList(tgMessageTask);
        return getDataTable(list);
    }

    /**
     * 导出TG消息任务管理列表
     */
    @RequiresPermissions("tg:task:export")
    @Log(title = "TG消息任务管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TgMessageTask tgMessageTask)
    {
        List<TgMessageTask> list = tgMessageTaskService.selectTgMessageTaskList(tgMessageTask);
        ExcelUtil<TgMessageTask> util = new ExcelUtil<TgMessageTask>(TgMessageTask.class);
        return util.exportExcel(list, "TG消息任务管理数据");
    }

    /**
     * 新增TG消息任务管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("topicTgmessageInfoList", iTgMessageInfoService.selectTopicTgMessageInfoList());
        return prefix + "/add";
    }

    /**
     * 新增保存TG消息任务管理
     */
    @RequiresPermissions("tg:task:add")
    @Log(title = "TG消息任务管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TgMessageTask tgMessageTask)
    {
        return toAjax(tgMessageTaskService.insertTgMessageTask(tgMessageTask));
    }

    /**
     * 修改TG消息任务管理
     */
    @RequiresPermissions("tg:task:edit")
    @GetMapping("/edit/{idTgMessageTask}")
    public String edit(@PathVariable("idTgMessageTask") Long idTgMessageTask, ModelMap mmap)
    {
        TgMessageTask tgMessageTask = tgMessageTaskService.selectTgMessageTaskByIdTgMessageTask(idTgMessageTask);
        mmap.put("topicTgmessageInfoList", iTgMessageInfoService.selectTopicTgMessageInfoList());
        mmap.put("tgMessageTask", tgMessageTask);
        return prefix + "/edit";
    }

    /**
     * 修改保存TG消息任务管理
     */
    @RequiresPermissions("tg:task:edit")
    @Log(title = "TG消息任务管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TgMessageTask tgMessageTask)
    {
        return toAjax(tgMessageTaskService.updateTgMessageTask(tgMessageTask));
    }

    /**
     * 删除TG消息任务管理
     */
    @RequiresPermissions("tg:task:remove")
    @Log(title = "TG消息任务管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tgMessageTaskService.deleteTgMessageTaskByIdTgMessageTasks(ids));
    }
}
