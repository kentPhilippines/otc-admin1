package com.ruoyi.web.controller.sms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.CountryTbl;
import com.ruoyi.common.core.domain.entity.SmsCountryPrice;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISmsCountryPriceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 群发国家单价配置Controller
 *
 * @author dorion
 * @date 2024-07-21
 */
@Controller
@RequestMapping("/sms/country")
public class SmsCountryPriceController extends BaseController {
    private String prefix = "sms/country";

    @Autowired
    private ISmsCountryPriceService smsCountryPriceService;

    @RequiresPermissions("sms:country:view")
    @GetMapping()
    public String country(ModelMap mmp) {
        List<CountryTbl> countries = smsCountryPriceService.selectAllCountries();
        mmp.put("countries", countries);
        return prefix + "/country";
    }

    /**
     * 查询群发国家单价配置列表
     */
    @RequiresPermissions("sms:country:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SmsCountryPrice smsCountryPrice) {
        startPage();
        List<SmsCountryPrice> list = smsCountryPriceService.selectSmsCountryPriceList(smsCountryPrice);
        return getDataTable(list);
    }

    /**
     * 导出群发国家单价配置列表
     */
    @RequiresPermissions("sms:country:export")
    @Log(title = "群发国家单价配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SmsCountryPrice smsCountryPrice) {
        List<SmsCountryPrice> list = smsCountryPriceService.selectSmsCountryPriceList(smsCountryPrice);
        ExcelUtil<SmsCountryPrice> util = new ExcelUtil<SmsCountryPrice>(SmsCountryPrice.class);
        return util.exportExcel(list, "群发国家单价配置数据");
    }

    /**
     * 新增群发国家单价配置
     */
    @GetMapping("/add")
    public String add(ModelMap mmp) {
        List<CountryTbl> countries = smsCountryPriceService.selectAllCountries();
        mmp.put("countries", countries);
        return prefix + "/add";
    }

    /**
     * 新增保存群发国家单价配置
     */
    @RequiresPermissions("sms:country:add")
    @Log(title = "群发国家单价配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SmsCountryPrice smsCountryPrice) {
        return toAjax(smsCountryPriceService.insertSmsCountryPrice(smsCountryPrice));
    }

    /**
     * 修改群发国家单价配置
     */
    @RequiresPermissions("sms:country:edit")
    @GetMapping("/edit/{idSmsCountryPrice}")
    public String edit(@PathVariable("idSmsCountryPrice") Long idSmsCountryPrice, ModelMap mmap) {
        SmsCountryPrice smsCountryPrice = smsCountryPriceService.selectSmsCountryPriceByIdSmsCountryPrice(idSmsCountryPrice);
        mmap.put("smsCountryPrice", smsCountryPrice);
        List<CountryTbl> countries = smsCountryPriceService.selectAllCountries();
        mmap.put("countries", countries);
        return prefix + "/edit";
    }

    /**
     * 修改保存群发国家单价配置
     */
    @RequiresPermissions("sms:country:edit")
    @Log(title = "群发国家单价配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SmsCountryPrice smsCountryPrice) {
        return toAjax(smsCountryPriceService.updateSmsCountryPrice(smsCountryPrice));
    }

    /**
     * 删除群发国家单价配置
     */
    @RequiresPermissions("sms:country:remove")
    @Log(title = "群发国家单价配置", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(smsCountryPriceService.deleteSmsCountryPriceByIdSmsCountryPrices(ids));
    }
}
