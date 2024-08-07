package com.ruoyi.quartz.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.google.common.base.Preconditions;
import com.ruoyi.common.core.domain.entity.AccountAddressInfo;
import com.ruoyi.common.core.domain.entity.ErrorLog;
import com.ruoyi.common.core.domain.entity.TenantInfo;
import com.ruoyi.common.core.domain.entity.TrxExchangeFail;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.quartz.domain.SysJob;
import com.ruoyi.quartz.domain.SysJobLog;
import com.ruoyi.quartz.service.ISysJobLogService;
import com.ruoyi.quartz.service.ISysJobService;
import com.ruoyi.system.handler.EnergyTenantTransferHandler;
import com.ruoyi.system.mapper.AccountAddressInfoMapper;
import com.ruoyi.system.mapper.TenantInfoMapper;
import com.ruoyi.system.mapper.TrxExchangeFailMapper;
import com.ruoyi.system.service.IErrorLogService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ITenantInfoService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component(value = "monitorTimerTaskStatusTask")
public class MonitorTimerTaskStatusTask {

    @Autowired
    private ISysJobService sysJobService;
    @Autowired
    private ISysJobLogService sysJobLogService;
    @Autowired
    private IErrorLogService errorLogService;
    @Autowired
    private ITenantInfoService tenantInfoService;
    @Autowired
    private TenantInfoMapper tenantInfoMapper;
    @Autowired
    private ISysConfigService configService;

    @Autowired
    private EnergyTenantTransferHandler energyTenantTransferHandler;
    @Autowired
    private AccountAddressInfoMapper accountAddressInfoMapper;
    @Autowired
    private TrxExchangeFailMapper trxExchangeFailMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;


    public void doMonitorTimerTaskStatus() {

        doMonitorSysJob();

        TenantInfo tenantInfoExample = new TenantInfo();
        tenantInfoExample.setStatus(DictUtils.getDictValue("sys_tenant_status", "生效中"));

        String sysTransferBetween = configService.selectConfigByKey("sys.transfer.between");
        DateTime fcd = DateUtil.offset(new Date(), DateField.MINUTE, Integer.parseInt(sysTransferBetween));
        tenantInfoExample.setFcd(fcd);
        List<TenantInfo> tenantInfoList = tenantInfoService.selectTenantInfoListNotExistsInExchange(tenantInfoExample);

        AccountAddressInfo accountAddressInfo = new AccountAddressInfo();
        accountAddressInfo.setIsValid("Y");
        List<AccountAddressInfo> accountAddressInfoList = accountAddressInfoMapper.selectAccountAddressInfoList(accountAddressInfo);
        Preconditions.checkState(CollectionUtil.isNotEmpty(accountAddressInfoList), "无有效的出账地址无法委托能量");

        String accountAddress = accountAddressInfoList.get(0).getAddress();

        for (TenantInfo tenantInfo : tenantInfoList) {
            try {
                tenantInfoService.doDelegateEnergy(tenantInfo, "system", accountAddress, null);
                tenantInfo.setTotalCountUsed(tenantInfo.getTotalCountUsed() + 1);
                tenantInfo.setLcd(new Date());
                tenantInfo.setLcu("compensate");
                tenantInfoMapper.updateTenantInfo(tenantInfo);


                TrxExchangeFail trxExchangeFailExample = new TrxExchangeFail();
                trxExchangeFailExample.setDelegateStatus("2");
                List<TrxExchangeFail> trxExchangeFails = trxExchangeFailMapper.selectTrxExchangeFailList(trxExchangeFailExample);

                for (TrxExchangeFail trxExchangeFail : trxExchangeFails) {
                    trxExchangeFailExample.setDelegateStatus("0");
                    trxExchangeFailMapper.updateTrxExchangeFail(trxExchangeFailExample);
                    //持久化之后放redis
                    redisTemplate.delete("transfer_trx_fail_" + trxExchangeFail.getFromAddress() + "_" + trxExchangeFail.getTrxTxId());
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }


    private void doMonitorSysJob() {
        SysJob sysJobExample = new SysJob();
        List<SysJob> sysJobs = sysJobService.selectJobList(sysJobExample);

        for (SysJob sysJob : sysJobs) {
            //0=正常,1=暂停
            if ("1".equals(sysJob.getStatus())) {
                continue;
            }
            if (104 == sysJob.getJobId() || 103 == sysJob.getJobId() || 106 == sysJob.getJobId()) {
                continue;
            }


            SysJobLog sysJobLogExample = new SysJobLog();
            sysJobLogExample.setJobName(sysJob.getJobName());
            sysJobLogExample.setCreateTime(DateUtil.offsetMinute(new Date(), -5));
            Integer count = sysJobLogService.countJobLog(sysJobLogExample);

            if (count == 0) {
                ErrorLog errorLog = ErrorLog.builder()
                        .address(sysJob.getJobName())
                        .errorCode("定时任务调度异常")
                        .errorMsg("定时任务调度异常")
                        .fcu("system")
                        .lcu("system").build();

                errorLogService.insertErrorLog(errorLog);
            }

        }
    }

}
