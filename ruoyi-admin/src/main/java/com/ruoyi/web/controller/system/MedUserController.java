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
import com.ruoyi.system.domain.MedUser;
import com.ruoyi.system.service.IMedUserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户详情Controller
 * 
 * @author kkkkk
 * @date 2024-10-23
 */
@Controller
@RequestMapping("/system/mduser")
public class MedUserController extends BaseController
{
    private String prefix = "system/mduser";

    @Autowired
    private IMedUserService medUserService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/user";
    }

    /**
     * 查询用户详情列表
     */
    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MedUser medUser)
    {
        startPage();
        List<MedUser> list = medUserService.selectMedUserList(medUser);
        return getDataTable(list);
    }

    /**
     * 导出用户详情列表
     */
    @RequiresPermissions("system:user:export")
    @Log(title = "用户详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MedUser medUser)
    {
        List<MedUser> list = medUserService.selectMedUserList(medUser);
        ExcelUtil<MedUser> util = new ExcelUtil<MedUser>(MedUser.class);
        return util.exportExcel(list, "用户详情数据");
    }

    /**
     * 新增用户详情
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户详情
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户详情", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MedUser medUser)
    {
        return toAjax(medUserService.insertMedUser(medUser));
    }

    /**
     * 修改用户详情
     */
    @RequiresPermissions("system:user:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        MedUser medUser = medUserService.selectMedUserById(id);
        mmap.put("medUser", medUser);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户详情
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户详情", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MedUser medUser)
    {
        return toAjax(medUserService.updateMedUser(medUser));
    }

    /**
     * 删除用户详情
     */
    @RequiresPermissions("system:user:remove")
    @Log(title = "用户详情", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(medUserService.deleteMedUserByIds(ids));
    }
}
