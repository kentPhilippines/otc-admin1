package com.ruoyi.web.controller.trx2Energy;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.AccountAddressInfo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.IAccountAddressInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TRX出账账户Controller
 * 
 * @author dorion
 * @date 2024-04-13
 */
@Controller
@RequestMapping("/trx2Energy/account")
public class Trx2EnergyAccountAddressInfoController extends BaseController
{
    private String prefix = "trx2Energy/account";

    @Autowired
    private IAccountAddressInfoService accountAddressInfoService;

    @RequiresPermissions("trx2Energy:account:view")
    @GetMapping()
    public String transfer()
    {
        return prefix + "/trx2Energy";
    }

    /**
     * 查询TRX出账账户列表
     */
    @RequiresPermissions("trx2Energy:account:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AccountAddressInfo accountAddressInfo) throws Exception {
        startPage();
        accountAddressInfo.setBusiType(DictUtils.getDictValue("sys_busi_type", "TRX兑能量"));
        List<AccountAddressInfo> list = accountAddressInfoService.selectAccountAddressInfoListByResouce(accountAddressInfo);

        return getDataTable(list);
    }

    /**
     * 导出TRX出账账户列表
     */
    @RequiresPermissions("trx2Energy:account:export")
    @Log(title = "TRX出账账户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AccountAddressInfo accountAddressInfo) throws Exception {
        accountAddressInfo.setBusiType(DictUtils.getDictValue("sys_busi_type", "TRX兑能量"));
        List<AccountAddressInfo> list = accountAddressInfoService.selectAccountAddressInfoListByResouce(accountAddressInfo);
        ExcelUtil<AccountAddressInfo> util = new ExcelUtil<AccountAddressInfo>(AccountAddressInfo.class);
        return util.exportExcel(list, "TRX出账账户数据");
    }

    /**
     * 新增TRX出账账户
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存TRX出账账户
     */
    @RequiresPermissions("trx2Energy:account:add")
    @Log(title = "TRX出账账户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AccountAddressInfo accountAddressInfo) throws Exception {
        accountAddressInfo.setBusiType(DictUtils.getDictValue("sys_busi_type", "TRX兑能量"));
        return toAjax(accountAddressInfoService.insertAccountAddressInfo(accountAddressInfo));
    }

    /**
     * 修改TRX出账账户
     */
    @RequiresPermissions("trx2Energy:account:edit")
    @GetMapping("/edit/{idAccoutAddressInfo}")
    public String edit(@PathVariable("idAccoutAddressInfo") Long idAccoutAddressInfo, ModelMap mmap)
    {
        AccountAddressInfo accountAddressInfo = accountAddressInfoService.selectAccountAddressInfoByIdAccoutAddressInfo(idAccoutAddressInfo);
        mmap.put("accountAddressInfo", accountAddressInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存TRX出账账户
     */
    @RequiresPermissions("trx2Energy:account:edit")
    @Log(title = "TRX出账账户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AccountAddressInfo accountAddressInfo) throws Exception {
        return toAjax(accountAddressInfoService.updateAccountAddressInfo(accountAddressInfo));
    }

    /**
     * 删除TRX出账账户
     */
    @RequiresPermissions("trx2Energy:account:remove")
    @Log(title = "TRX出账账户", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(accountAddressInfoService.deleteAccountAddressInfoByIdAccoutAddressInfos(ids));
    }
}
