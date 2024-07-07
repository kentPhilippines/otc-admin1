package com.ruoyi.system.api.impl;

import com.google.common.base.Preconditions;
import com.ruoyi.common.utils.http.RestTemplateUtils;
import com.ruoyi.system.api.IU02cxApi;
import com.ruoyi.system.api.entity.U02cx.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class U02cxApiImpl implements IU02cxApi {


    @Override
    public String getToken(GetTokenRequest getTokenRequest) {

        ResponseEntity<GetTokenResponse> responseEntity = RestTemplateUtils.post("http://cot5b.u02cx.com:20086/whatsApi/getToken", getTokenRequest, GetTokenResponse.class);

        Preconditions.checkState(responseEntity.getStatusCode().is2xxSuccessful(), "getToken failed");

        GetTokenResponse getTokenResponse = responseEntity.getBody();
        Preconditions.checkNotNull(getTokenResponse, "getTokenResponse is null");

        int status = getTokenResponse.getStatus();
        Preconditions.checkState(0 == status, "getToken failed:" + ApiConstants.returnCodeMap.get(status));
        return getTokenResponse.getToken();
    }

    @Override
    public Long addTask(BaseRequestBO baseRequestBO) throws IOException {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        httpHeaders.add("appId", baseRequestBO.getAppId());
        httpHeaders.add("token", baseRequestBO.getToken());

        ResponseEntity<AddTaskResponse> responseEntity = RestTemplateUtils.post("http://cot5b.u02cx.com:20086/whatsApi/api/task/addTask", httpHeaders, baseRequestBO.getEncryptedData(), AddTaskResponse.class);
        Preconditions.checkState(responseEntity.getStatusCode().is2xxSuccessful(), "addTask failed");

        AddTaskResponse addTaskResponse = responseEntity.getBody();
        Preconditions.checkNotNull(addTaskResponse, "addTaskResponse is null");

        int status = addTaskResponse.getStatus();
        Preconditions.checkState(0 == status, "addTask failed:" + ApiConstants.returnCodeMap.get(status) + ",msg:" + addTaskResponse.getMsg());

        return addTaskResponse.getTaskId();
    }

    @Override
    public void updateTaskStatus(BaseRequestBO baseRequestBO) {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        httpHeaders.add("appId", baseRequestBO.getAppId());
        httpHeaders.add("token", baseRequestBO.getToken());

        ResponseEntity<UpdateTaskStatusResponse> responseEntity = RestTemplateUtils.post("http://cot5b.u02cx.com:20086/whatsApi/api/task/updateTaskStatus", httpHeaders, baseRequestBO.getEncryptedData(), UpdateTaskStatusResponse.class);
        Preconditions.checkState(responseEntity.getStatusCode().is2xxSuccessful(), "updateTaskStatus failed");

        UpdateTaskStatusResponse updateTaskStatusResponse = responseEntity.getBody();
        Preconditions.checkNotNull(updateTaskStatusResponse, "updateTaskStatusResponse is null");

        int status = updateTaskStatusResponse.getStatus();
        Preconditions.checkState(0 == status, "updateTaskStatus failed:" + ApiConstants.returnCodeMap.get(status) + ",msg:" + updateTaskStatusResponse.getMsg());
    }

    @Override
    public void updateTask(BaseRequestBO baseRequestBO) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        httpHeaders.add("appId", baseRequestBO.getAppId());
        httpHeaders.add("token", baseRequestBO.getToken());

        ResponseEntity<UpdateTaskStatusResponse> responseEntity = RestTemplateUtils.post("http://cot5b.u02cx.com:20086/whatsApi/api/task/updateTask", httpHeaders, baseRequestBO.getEncryptedData(), UpdateTaskStatusResponse.class);
        Preconditions.checkState(responseEntity.getStatusCode().is2xxSuccessful(), "updateTask failed");

        UpdateTaskStatusResponse updateTaskStatusResponse = responseEntity.getBody();
        Preconditions.checkNotNull(updateTaskStatusResponse, "updateTaskResponse is null");

        int status = updateTaskStatusResponse.getStatus();
        Preconditions.checkState(0 == status, "updateTask failed:" + ApiConstants.returnCodeMap.get(status) + ",msg:" + updateTaskStatusResponse.getMsg());
    }

    @Override
    public void completeByBatch(BatchTaskIdsRequest batchTaskIdsRequest){
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        httpHeaders.add("appId", batchTaskIdsRequest.getAppId());
        httpHeaders.add("token", batchTaskIdsRequest.getToken());

        ResponseEntity<UpdateTaskStatusResponse> responseEntity = RestTemplateUtils.post("http://cot5b.u02cx.com:20086/whatsApi/api/report/completeByBatch", httpHeaders, batchTaskIdsRequest, UpdateTaskStatusResponse.class);
        Preconditions.checkState(responseEntity.getStatusCode().is2xxSuccessful(), "completeByBatch failed");

        UpdateTaskStatusResponse updateTaskStatusResponse = responseEntity.getBody();
        Preconditions.checkNotNull(updateTaskStatusResponse, "completeByBatchResponse is null");

        int status = updateTaskStatusResponse.getStatus();
        Preconditions.checkState(0 == status, "completeByBatch failed:" + ApiConstants.returnCodeMap.get(status) + ",msg:" + updateTaskStatusResponse.getMsg());
    }


    @Override
    public List<TaskDetailResponse> getTaskList(BatchTaskIdsRequest batchTaskIdsRequest){
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        httpHeaders.add("appId", batchTaskIdsRequest.getAppId());
        httpHeaders.add("token", batchTaskIdsRequest.getToken());

        ResponseEntity<GetTaskListResponse> responseEntity = RestTemplateUtils.post("http://cot5b.u02cx.com:20086/whatsApi/api/report/getTaskList", httpHeaders, batchTaskIdsRequest, GetTaskListResponse.class);
        Preconditions.checkState(responseEntity.getStatusCode().is2xxSuccessful(), "getTaskList failed");

        GetTaskListResponse getTaskListResponse = responseEntity.getBody();
        Preconditions.checkNotNull(getTaskListResponse, "getTaskListResponse is null");

        int status = getTaskListResponse.getStatus();
        Preconditions.checkState(0 == status, "getTaskList failed:" + ApiConstants.returnCodeMap.get(status) + ",msg:" + getTaskListResponse.getMsg());
        return getTaskListResponse.getData();
    }

    @Override
    public byte[] getReport(GetReportRequest getReportRequest){
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        httpHeaders.add("appId", getReportRequest.getAppId());
        httpHeaders.add("token", getReportRequest.getToken());

        ResponseEntity<byte[]> response = RestTemplateUtils.post("http://cot5b.u02cx.com:20086/whatsApi/api/report/getReport", httpHeaders, getReportRequest, byte[].class);
        // 处理响应
        if (response.getStatusCode() == HttpStatus.OK) {
            byte[] responseBody = response.getBody();
            return responseBody;
        } else if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
            // 假设错误信息是JSON格式
            String errorMsg = new String(response.getBody());
            log.error("请求失败: " + errorMsg);
        }
        return null;
    }


}
