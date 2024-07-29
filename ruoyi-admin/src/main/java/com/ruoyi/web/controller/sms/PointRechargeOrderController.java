package com.ruoyi.web.controller.sms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.common.core.domain.entity.PointRechargeOrder;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.MonitorAddressInfoVO;
import com.ruoyi.system.service.IMonitorAddressInfoService;
import com.ruoyi.system.service.IPointRechargeOrderService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 群发充值管理Controller
 * 
 * @author dorion
 * @date 2024-07-21
 */
@Controller
@RequestMapping("/sms/point")
public class PointRechargeOrderController extends BaseController
{
    private String prefix = "sms/point";
    @Autowired
    private IMonitorAddressInfoService monitorAddressInfoService;
    @Autowired
    private IPointRechargeOrderService pointRechargeOrderService;
    @Autowired
    private ISysUserService sysUserService;

    @RequiresPermissions("sms:point:view")
    @GetMapping()
    public String point(ModelMap mmap)
    {
        List<SysUser> sysUserList = sysUserService.selectUserList(new SysUser());
        mmap.put("sysUserList",sysUserList);
        return prefix + "/point";
    }

    /**
     * 查询群发充值管理列表
     */
    @RequiresPermissions("sms:point:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PointRechargeOrder pointRechargeOrder)
    {
        startPage();
        List<PointRechargeOrder> list = pointRechargeOrderService.selectPointRechargeOrderList(pointRechargeOrder);
        return getDataTable(list);
    }

    /**
     * 导出群发充值管理列表
     */
    @RequiresPermissions("sms:point:export")
    @Log(title = "群发充值管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PointRechargeOrder pointRechargeOrder)
    {
        List<PointRechargeOrder> list = pointRechargeOrderService.selectPointRechargeOrderList(pointRechargeOrder);
        ExcelUtil<PointRechargeOrder> util = new ExcelUtil<PointRechargeOrder>(PointRechargeOrder.class);
        return util.exportExcel(list, "群发充值管理数据");
    }

    /**
     * 新增群发充值管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        List<SysUser> sysUserList = sysUserService.selectUserList(new SysUser());
        mmap.put("sysUserList",sysUserList);
        return prefix + "/add";
    }
    /**
     * 新增群发充值管理
     */
    @RequiresPermissions("sms:point:recharge")
    @GetMapping("/recharge")
    public String rechage(ModelMap mmap)
    {
        /*List<SysUser> sysUserList = sysUserService.selectUserList(new SysUser());
        mmap.put("sysUserList",sysUserList);*/
        return prefix + "/recharge";
    }

    @RequiresPermissions("sms:point:recharge")
    @GetMapping("/rechargeOrder/{idPointRechargeOrder}")
    public String rechargeOrder(ModelMap mmap,@PathVariable("idPointRechargeOrder") Long idPointRechargeOrder)
    {
        PointRechargeOrder pointRechargeOrder = pointRechargeOrderService.selectPointRechargeOrderByIdPointRechargeOrder(idPointRechargeOrder);
        BigDecimal amount = pointRechargeOrder.getAmount();
        MonitorAddressInfo monitorAddressInfo = new MonitorAddressInfo();
        monitorAddressInfo.setBusiType(DictUtils.getDictValue("sys_busi_type", "短信群发"));
        List<MonitorAddressInfoVO> list = monitorAddressInfoService.selectMonitorAddressInfoList(monitorAddressInfo);
        MonitorAddressInfoVO monitorAddressInfoVO = list.get(0);
        String monitorAddress = monitorAddressInfoVO.getMonitorAddress();
        String imageUrl = monitorAddressInfoVO.getImageUrl();

        mmap.put("amount",amount);
        mmap.put("monitorAddress",monitorAddress);
        mmap.put("imageUrl",imageUrl);
        return prefix + "/rechargeOrder";
    }

    /**
     * 新增保存群发充值管理
     */
    @RequiresPermissions("sms:point:add")
    @Log(title = "群发充值管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PointRechargeOrder pointRechargeOrder)
    {
        return toAjax(pointRechargeOrderService.insertPointRechargeOrder(pointRechargeOrder));
    }

    @RequiresPermissions("ssms:point:recharge")
    @Log(title = "群发充值管理", businessType = BusinessType.INSERT)
    @PostMapping("/rechargeOrder")
    @ResponseBody
    public Map<String, Object>  rechargeOrder(PointRechargeOrder pointRechargeOrder)
    {
        Map<String, Object> result = new HashMap<>();
        Long idPointRechargeOrder = pointRechargeOrderService.rechargeOrder(pointRechargeOrder);
        result.put("success", true);
        result.put("newPageUrl", "/sms/point/rechargeOrder/"+idPointRechargeOrder); // 返回新的页面 URL
        return result;
    }

    /**
     * 修改群发充值管理
     */
    @RequiresPermissions("sms:point:edit")
    @GetMapping("/edit/{idPointRechargeOrder}")
    public String edit(@PathVariable("idPointRechargeOrder") Long idPointRechargeOrder, ModelMap mmap)
    {
        PointRechargeOrder pointRechargeOrder = pointRechargeOrderService.selectPointRechargeOrderByIdPointRechargeOrder(idPointRechargeOrder);
        mmap.put("pointRechargeOrder", pointRechargeOrder);
        List<SysUser> sysUserList = sysUserService.selectUserList(new SysUser());
        mmap.put("sysUserList",sysUserList);
        return prefix + "/edit";
    }

    /**
     * 修改保存群发充值管理
     */
    @RequiresPermissions("sms:point:edit")
    @Log(title = "群发充值管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PointRechargeOrder pointRechargeOrder)
    {
        return toAjax(pointRechargeOrderService.updatePointRechargeOrder(pointRechargeOrder));
    }

    /**
     * 删除群发充值管理
     */
    @RequiresPermissions("sms:point:remove")
    @Log(title = "群发充值管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(pointRechargeOrderService.deletePointRechargeOrderByIdPointRechargeOrders(ids));
    }
}
