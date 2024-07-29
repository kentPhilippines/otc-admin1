package com.ruoyi.web.controller.sms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.entity.UserPoint;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.IUserPointService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户积分Controller
 * 
 * @author dorion
 * @date 2024-07-21
 */
@Controller
@RequestMapping("/sms/user")
public class UserPointController extends BaseController
{
    private String prefix = "sms/user";

    @Autowired
    private IUserPointService userPointService;
    @Autowired
    private ISysUserService sysUserService;

    @RequiresPermissions("sms:user:view")
    @GetMapping()
    public String user(ModelMap mmap)
    {
        List<SysUser> sysUserList = sysUserService.selectUserList(new SysUser());
        mmap.put("sysUserList",sysUserList);
        return prefix + "/user";
    }

    /**
     * 查询用户积分列表
     */
    @RequiresPermissions("sms:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UserPoint userPoint)
    {
        startPage();
        List<UserPoint> list = userPointService.selectUserPointList(userPoint);
        return getDataTable(list);
    }

    /**
     * 导出用户积分列表
     */
    @RequiresPermissions("sms:user:export")
    @Log(title = "用户积分", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(UserPoint userPoint)
    {
        List<UserPoint> list = userPointService.selectUserPointList(userPoint);
        ExcelUtil<UserPoint> util = new ExcelUtil<UserPoint>(UserPoint.class);
        return util.exportExcel(list, "用户积分数据");
    }

    /**
     * 新增用户积分
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        List<SysUser> sysUserList = sysUserService.selectUserList(new SysUser());
        mmap.put("sysUserList",sysUserList);
        return prefix + "/add";
    }

    /**
     * 新增保存用户积分
     */
    @RequiresPermissions("sms:user:add")
    @Log(title = "用户积分", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(UserPoint userPoint)
    {
        return toAjax(userPointService.insertUserPoint(userPoint));
    }

    /**
     * 修改用户积分
     */
    @RequiresPermissions("sms:user:edit")
    @GetMapping("/edit/{idUserPoint}")
    public String edit(@PathVariable("idUserPoint") Long idUserPoint, ModelMap mmap)
    {
        UserPoint userPoint = userPointService.selectUserPointByIdUserPoint(idUserPoint);
        mmap.put("userPoint", userPoint);
        List<SysUser> sysUserList = sysUserService.selectUserList(new SysUser());
        mmap.put("sysUserList",sysUserList);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户积分
     */
    @RequiresPermissions("sms:user:edit")
    @Log(title = "用户积分", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(UserPoint userPoint)
    {
        return toAjax(userPointService.updateUserPoint(userPoint));
    }

    /**
     * 删除用户积分
     */
    @RequiresPermissions("sms:user:remove")
    @Log(title = "用户积分", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(userPointService.deleteUserPointByIdUserPoints(ids));
    }
}
