package com.ruoyi.system.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.domain.entity.SmsTaskTbl;
import com.ruoyi.common.core.domain.entity.UserPoint;
import com.ruoyi.system.api.IU02cxApi;
import com.ruoyi.system.api.entity.U02cx.BatchTaskIdsRequest;
import com.ruoyi.system.api.entity.U02cx.GetTokenRequest;
import com.ruoyi.system.api.entity.U02cx.TaskDetailResponse;
import com.ruoyi.system.domain.SmsChannelTbl;
import com.ruoyi.system.mapper.SmsChannelTblMapper;
import com.ruoyi.system.mapper.SmsTaskTblMapper;
import com.ruoyi.system.mapper.UserPointMapper;
import com.ruoyi.system.util.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GetSmsDetailTaskHandler {

    @Autowired
    private IU02cxApi iu02cxApi;
    @Autowired
    private SmsChannelTblMapper smsChannelTblMapper;

    @Autowired
    private SmsTaskTblMapper smsTaskTblMapper;

    @Autowired
    private UserPointMapper userPointMapper;

    public void doGetSmsDetailTask(List<SmsTaskTbl> smsTaskTblList) {
        List<Long> taskIds = smsTaskTblList.stream().filter(smsTaskTbl -> {
            return smsTaskTbl.getTaskId() != null;
        }).map(SmsTaskTbl::getTaskId).collect(Collectors.toList());
        SmsChannelTbl smsChannelTbl = smsChannelTblMapper.selectSmsChannelTblByIdSmsChannel(1L);

        GetTokenRequest getTokenRequest = new GetTokenRequest();
        BeanUtils.copyProperties(smsChannelTbl, getTokenRequest);
        String token = iu02cxApi.getToken(getTokenRequest);


        String jsonStr = JSONUtil.toJsonStr(taskIds);
        log.debug("jsonStr:{}", jsonStr);
        String encryptedData = RsaUtils.encryptData(jsonStr, smsChannelTbl.getPublicKey());

        BatchTaskIdsRequest batchTaskIdsRequest = new BatchTaskIdsRequest();
        batchTaskIdsRequest.setTaskIds(taskIds)
                .setToken(token)
                .setAppId(smsChannelTbl.getAppId())
                .setEncryptedData(encryptedData);

        List<TaskDetailResponse> taskList = iu02cxApi.getTaskList(batchTaskIdsRequest);
        taskList.stream()
                .forEach(taskDetailResponse -> {
                    SmsTaskTbl smsTaskTbl = new SmsTaskTbl();
                    smsTaskTbl.setTaskId(taskDetailResponse.getTaskId());
                    String completeTime = taskDetailResponse.getCompleteTime();
                    if (completeTime == null) {
                        return;
                    }

                    SmsTaskTbl smsTaskTblResult = smsTaskTblMapper.selectSmsTaskTblList(smsTaskTbl).get(0);
                    smsTaskTblResult.setCompleteTime(DateUtil.parse(taskDetailResponse.getCompleteTime()));
                    String successRate = taskDetailResponse.getSuccessRate();
                    if (successRate != null) {
                        smsTaskTbl.setSuccessRate(successRate);
                        String[] split = successRate.split("/");
                        smsTaskTbl.setSuccessCount(Long.parseLong(split[0]));
                    }

                    BigDecimal price = smsTaskTblResult.getPrice();
                    BigDecimal actSummary = price.multiply(BigDecimal.valueOf(smsTaskTbl.getSuccessCount()));
                    smsTaskTblResult.setActSummary(actSummary);
                    BigDecimal preSummary = smsTaskTblResult.getPreSummary();

                    BigDecimal subtract = preSummary.subtract(actSummary);

                    UserPoint userPointParam = new UserPoint();
                    userPointParam.setUserId(Long.valueOf(smsTaskTblResult.getUserId()));
                    UserPoint userPoint = userPointMapper.selectUserPointList(userPointParam).get(0);

                    BigDecimal pointBalance = userPoint.getPointBalance();
                    BigDecimal pointBalanceNew = pointBalance.add(subtract);
                    userPoint.setPointBalance(pointBalanceNew);
                    userPointMapper.updateUserPoint(userPoint);

//                    smsTaskTbl.setCompleteTime(DateUtil.parse(taskDetailResponse.getCompleteTime()));
//                    String successRate = taskDetailResponse.getSuccessRate();
//                    if (successRate != null) {
//                        smsTaskTbl.setSuccessRate(successRate);
//                        String[] split = successRate.split("/");
//                        smsTaskTbl.setSuccessCount(Long.parseLong(split[0]));
////                        smsTaskTbl.setTotalCount(Long.parseLong(split[1]));
//                    }
//               /*     BigDecimal preSummary = taskDetailResponse.getPreSummary();
//                    if (preSummary != null) {
//                        smsTaskTbl.setPreSummary(preSummary);
//                    }*/
////                    smsTaskTbl.setIssueCount(Long.valueOf(taskDetailResponse.getIssueCount()));
//                    smsTaskTbl.setTaskStatus(taskDetailResponse.getTaskStatus());
//                    smsTaskTbl.setUpdateBy("system");
                    smsTaskTbl.setSettleStatus("1");
                    smsTaskTblMapper.updateSmsTaskTblByTaskId(smsTaskTbl);
                });

    }
}
