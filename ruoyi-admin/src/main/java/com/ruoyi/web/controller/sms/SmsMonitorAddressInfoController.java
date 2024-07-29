package com.ruoyi.web.controller.sms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.common.core.domain.entity.TgMessageInfo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.MonitorAddressInfoVO;
import com.ruoyi.system.service.IAccountAddressInfoService;
import com.ruoyi.system.service.IMonitorAddressInfoService;
import com.ruoyi.system.service.impl.TgMessageInfoServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 监听账户入账Controller
 * 
 * @author dorion
 * @date 2024-04-13
 */
@Controller
@RequestMapping("/sms/monitor")
public class SmsMonitorAddressInfoController extends BaseController
{
    private String prefix = "sms/monitor";

    @Autowired
    private IMonitorAddressInfoService monitorAddressInfoService;
    @Autowired
    private IAccountAddressInfoService accountAddressInfoService;
    @Autowired
    private TgMessageInfoServiceImpl tgMessageInfoService;
    @Autowired
    private ServerConfig serverConfig;

    @RequiresPermissions("sms:monitor:view")
    @GetMapping()
    public String monitor(ModelMap mmap)
    {
        TgMessageInfo tgMessageInfo = new TgMessageInfo();
        tgMessageInfo.setMessageType(5L);
        mmap.put("topicTgmessageInfoList", tgMessageInfoService.selectTgMessageInfoList(tgMessageInfo));
        return prefix + "/monitor";
    }

    /**
     * 查询监听账户入账列表
     */
    @RequiresPermissions("sms:monitor:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MonitorAddressInfo monitorAddressInfo)
    {
        startPage();
        monitorAddressInfo.setBusiType(DictUtils.getDictValue("sys_busi_type", "短信群发"));
        List<MonitorAddressInfoVO> list = monitorAddressInfoService.selectMonitorAddressInfoList(monitorAddressInfo);
        return getDataTable(list);
    }

    /**
     * 导出监听账户入账列表
     */
    @RequiresPermissions("sms:monitor:export")
    @Log(title = "监听账户入账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MonitorAddressInfo monitorAddressInfo)
    {
        monitorAddressInfo.setBusiType(DictUtils.getDictValue("sys_busi_type", "短信群发"));
        List<MonitorAddressInfoVO> list = monitorAddressInfoService.selectMonitorAddressInfoList(monitorAddressInfo);
        ExcelUtil<MonitorAddressInfoVO> util = new ExcelUtil<MonitorAddressInfoVO>(MonitorAddressInfoVO.class);
        return util.exportExcel(list, "监听账户入账数据");
    }

    /**
     * 新增监听账户入账
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        TgMessageInfo tgMessageInfo = new TgMessageInfo();
        tgMessageInfo.setMessageType(5L);
        mmap.put("topicTgmessageInfoList", tgMessageInfoService.selectTgMessageInfoList(tgMessageInfo));
        return prefix + "/add";
    }

    /**
     * 新增保存监听账户入账
     */
    @RequiresPermissions("sms:monitor:add")
    @Log(title = "监听账户入账", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestParam("file") MultipartFile file, MonitorAddressInfo monitorAddressInfo) throws IOException {   // 上传文件路径
        String filePath = RuoYiConfig.getUploadPath();
        // 上传并返回新文件名称
        String fileName = FileUploadUtils.upload(filePath, file);
        String url = serverConfig.getUrl() + fileName;
        monitorAddressInfo.setImageUrl(url);
        monitorAddressInfo.setBusiType(DictUtils.getDictValue("sys_busi_type", "短信群发"));
        return toAjax(monitorAddressInfoService.insertMonitorAddressInfo(monitorAddressInfo));
    }

    /**
     * 修改监听账户入账
     */
    @RequiresPermissions("sms:monitor:edit")
    @GetMapping("/edit/{idMonitorAddress}")
    public String edit(@PathVariable("idMonitorAddress") Long idMonitorAddress, ModelMap mmap)
    {
        MonitorAddressInfo monitorAddressInfo = monitorAddressInfoService.selectMonitorAddressInfoByIdMonitorAddress(idMonitorAddress);
        mmap.put("monitorAddressInfo", monitorAddressInfo);

        TgMessageInfo tgMessageInfo = new TgMessageInfo();
        tgMessageInfo.setMessageType(5L);
        mmap.put("topicTgmessageInfoList", tgMessageInfoService.selectTgMessageInfoList(tgMessageInfo));
        return prefix + "/edit";
    }

    /**
     * 修改保存监听账户入账
     */
    @RequiresPermissions("sms:monitor:edit")
    @Log(title = "监听账户入账", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MonitorAddressInfo monitorAddressInfo)
    {
        return toAjax(monitorAddressInfoService.updateMonitorAddressInfo(monitorAddressInfo));
    }

    /**
     * 删除监听账户入账
     */
    @RequiresPermissions("sms:monitor:remove")
    @Log(title = "监听账户入账", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(monitorAddressInfoService.deleteMonitorAddressInfoByIdMonitorAddresss(ids));
    }
}
