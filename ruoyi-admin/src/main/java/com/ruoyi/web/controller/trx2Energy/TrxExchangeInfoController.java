package com.ruoyi.web.controller.trx2Energy;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.TrxExchangeInfo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TrxExchange;
import com.ruoyi.system.domain.vo.TrxExchangeInfoVO;
import com.ruoyi.system.service.IAccountAddressInfoService;
import com.ruoyi.system.service.ITrxExchangeInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * trx兑能量记录Controller
 *
 * @author dorion
 * @date 2024-04-13
 */
@Controller
@RequestMapping("/trx2Energy/exchange")
public class TrxExchangeInfoController extends BaseController
{
    private String prefix = "trx2Energy/exchange";

    @Autowired
    private ITrxExchangeInfoService trxExchangeInfoService;

    @Autowired
    private IAccountAddressInfoService accountAddressInfoService;

    @RequiresPermissions("trx2Energy:exchange:view")
    @GetMapping()
    public String trx()
    {
        return prefix + "/trx";
    }

    /**
     * 查询trx兑能量记录列表
     */
    @RequiresPermissions("trx2Energy:exchange:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TrxExchangeInfo trxExchangeInfo)
    {
        startPage();
        List<TrxExchangeInfoVO> list = trxExchangeInfoService.selectTrxExchangeInfoList(trxExchangeInfo);
        return getDataTable(list);
    }

    /**
     * 导出trx兑能量记录列表
     */
    @RequiresPermissions("trx2Energy:exchange:export")
    @Log(title = "trx兑能量记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TrxExchangeInfo trxExchangeInfo)
    {
        List<TrxExchangeInfoVO> list = trxExchangeInfoService.selectTrxExchangeInfoList(trxExchangeInfo);
        ExcelUtil<TrxExchangeInfoVO> util = new ExcelUtil<TrxExchangeInfoVO>(TrxExchangeInfoVO.class);
        return util.exportExcel(list, "trx兑能量记录数据");
    }

    /**
     * 新增trx兑能量记录
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        String busiType = DictUtils.getDictValue("sys_busi_type", "TRX兑能量");
        mmap.put("accountAddressList", accountAddressInfoService.selectAccountAddressInfoAll(busiType));
        return prefix + "/add";
    }

    /**
     * 新增保存trx兑能量记录
     */
    @RequiresPermissions("trx2Energy:exchange:add")
    @Log(title = "trx兑能量记录", businessType = BusinessType.INSERT)
    @PostMapping("/delegate")
    @ResponseBody
    public AjaxResult delegate(TrxExchange trxExchange) throws Exception {
        return toAjax(trxExchangeInfoService.delegate(trxExchange,false,null));
    }

    /**
     * 修改trx兑能量记录
     */
    @RequiresPermissions("trx2Energy:exchange:edit")
    @GetMapping("/edit/{idTrxExchangeInfo}")
    public String edit(@PathVariable("idTrxExchangeInfo") Long idTrxExchangeInfo, ModelMap mmap)
    {
        TrxExchangeInfo trxExchangeInfo = trxExchangeInfoService.selectTrxExchangeInfoByIdTrxExchangeInfo(idTrxExchangeInfo);
        mmap.put("trxExchangeInfo", trxExchangeInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存trx兑能量记录
     */
    @RequiresPermissions("trx2Energy:exchange:edit")
    @Log(title = "trx兑能量记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TrxExchangeInfo trxExchangeInfo)
    {
        return toAjax(trxExchangeInfoService.updateTrxExchangeInfo(trxExchangeInfo));
    }

    /**
     * 删除trx兑能量记录
     */
    @RequiresPermissions("trx2Energy:exchange:remove")
    @Log(title = "trx兑能量记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(trxExchangeInfoService.deleteTrxExchangeInfoByIdTrxExchangeInfos(ids));
    }
}
