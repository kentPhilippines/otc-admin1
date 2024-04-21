package com.ruoyi.web.controller.account;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.common.core.domain.entity.TenantInfo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.IMonitorAddressInfoService;
import com.ruoyi.system.service.ITenantInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租户Controller
 * 
 * @author dorion
 * @date 2024-04-14
 */
@Controller
@RequestMapping("/account/tenant")
public class TenantInfoController extends BaseController
{
    private String prefix = "account/tenant";

    @Autowired
    private ITenantInfoService tenantInfoService;
    @Autowired
    private IMonitorAddressInfoService monitorAddressInfoService;

    @RequiresPermissions("account:tenant:view")
    @GetMapping()
    public String tenant()
    {
        return prefix + "/tenant";
    }

    /**
     * 查询租户列表
     */
    @RequiresPermissions("account:tenant:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TenantInfo tenantInfo)
    {
        startPage();
        List<TenantInfo> list = tenantInfoService.selectTenantInfoList(tenantInfo);
        return getDataTable(list);
    }

    /**
     * 导出租户列表
     */
    @RequiresPermissions("account:tenant:export")
    @Log(title = "租户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TenantInfo tenantInfo)
    {
        List<TenantInfo> list = tenantInfoService.selectTenantInfoList(tenantInfo);
        ExcelUtil<TenantInfo> util = new ExcelUtil<TenantInfo>(TenantInfo.class);
        return util.exportExcel(list, "租户数据");
    }

    /**
     * 新增租户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        String trx2Energy = DictUtils.getDictValue("sys_busi_type", "TRX兑能量");
        List<MonitorAddressInfo> monitorAddressAccountList = monitorAddressInfoService.selectAllValidMonitorAddressAccount(trx2Energy);
        mmap.put("monitorAddressInfoList", monitorAddressAccountList);
        return prefix + "/add";
    }

    /**
     * 新增保存租户
     */
    @RequiresPermissions("account:tenant:add")
    @Log(title = "租户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TenantInfo tenantInfo)
    {
        return toAjax(tenantInfoService.insertTenantInfo(tenantInfo));
    }

    /**
     * 修改租户
     */
    @RequiresPermissions("account:tenant:edit")
    @GetMapping("/edit/{idTenantInfo}")
    public String edit(@PathVariable("idTenantInfo") Long idTenantInfo, ModelMap mmap)
    {
        TenantInfo tenantInfo = tenantInfoService.selectTenantInfoByIdTenantInfo(idTenantInfo);
        mmap.put("tenantInfo", tenantInfo);
        String trx2Energy = DictUtils.getDictValue("sys_busi_type", "TRX兑能量");
        List<MonitorAddressInfo> monitorAddressInfoList = monitorAddressInfoService.selectAllValidMonitorAddressAccount(trx2Energy);
        mmap.put("monitorAddressInfoList", monitorAddressInfoList);
        return prefix + "/edit";
    }

    /**
     * 修改保存租户
     */
    @RequiresPermissions("account:tenant:edit")
    @Log(title = "租户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TenantInfo tenantInfo)
    {
        return toAjax(tenantInfoService.updateTenantInfo(tenantInfo));
    }

    /**
     * 删除租户
     */
    @RequiresPermissions("account:tenant:remove")
    @Log(title = "租户", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tenantInfoService.deleteTenantInfoByIdTenantInfos(ids));
    }

    @RequiresPermissions("account:tenant:add")
    @Log(title = "租户", businessType = BusinessType.ACTIVE_DATA)
    @PostMapping( "/activeData")
    @ResponseBody
    public AjaxResult activeData(String ids) throws Exception {
        return toAjax(tenantInfoService.activeDataTenantInfoByIdTenantInfos(ids));
    }
}
