package com.ruoyi.quartz.task;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.domain.entity.MonitorAddressInfo;
import com.ruoyi.common.core.domain.entity.PointRechargeOrder;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.system.domain.MonitorAddressAccount;
import com.ruoyi.system.handler.Usdt2SmsPointTransferHandler;
import com.ruoyi.system.mapper.PointRechargeOrderMapper;
import com.ruoyi.system.service.IMonitorAddressInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("monitorUsdt2SmsPointTransferTask")
@Slf4j
public class MonitorUsdt2SmsPointTransferTask {

    @Autowired
    private IMonitorAddressInfoService iMonitorAddressInfoService;

    @Autowired
    private Usdt2SmsPointTransferHandler usdt2SmsPointTransferHandler;
    @Autowired
    private PointRechargeOrderMapper pointRechargeOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public void doMonitorUsdtTransferTask() throws Exception {

        MonitorAddressInfo monitorAddressInfoExample = new MonitorAddressInfo();
        monitorAddressInfoExample
                .setIsValid("Y")
                .setBusiType(DictUtils.getDictValue("sys_busi_type", "短信群发"));

        List<MonitorAddressAccount> monitorAddressAccounts = iMonitorAddressInfoService.selectMonitorAddressAccount(monitorAddressInfoExample);

        for (MonitorAddressAccount monitorAddressAccount : monitorAddressAccounts) {
            usdt2SmsPointTransferHandler.doMonitorUsdt2SmsPointTransfer(monitorAddressAccount);
        }

        PointRechargeOrder pointRechargeOrderParam = new PointRechargeOrder();
        pointRechargeOrderParam.setStatus("N");
        //查询未支付的订单
        List<PointRechargeOrder> pointRechargeOrders = pointRechargeOrderMapper.selectPointRechargeOrderList(pointRechargeOrderParam);
        for (PointRechargeOrder pointRechargeOrder : pointRechargeOrders) {
            Date createTime = pointRechargeOrder.getCreateTime();
            long between = DateUtil.between(createTime, new Date(), DateUnit.HOUR);
            if(between > 1){
                //设置为已过期
                pointRechargeOrder.setStatus("E");
                pointRechargeOrderMapper.updatePointRechargeOrder(pointRechargeOrder);
                redisTemplate.delete("sms_recharge_amount_" + pointRechargeOrder.getAmount());
            }
        }
    }
}
