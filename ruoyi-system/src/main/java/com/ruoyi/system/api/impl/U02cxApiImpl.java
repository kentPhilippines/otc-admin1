package com.ruoyi.system.api.impl;

import com.google.common.base.Preconditions;
import com.ruoyi.common.utils.http.RestTemplateUtils;
import com.ruoyi.system.api.IU02cxApi;
import com.ruoyi.system.api.entity.U02cx.AddTaskResponse;
import com.ruoyi.system.api.entity.U02cx.ApiConstants;
import com.ruoyi.system.api.entity.U02cx.GetTokenRequest;
import com.ruoyi.system.api.entity.U02cx.GetTokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
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
    public Long addTask(String requestBody, String token, String appId) throws IOException {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        httpHeaders.add("appId",appId);
        httpHeaders.add("token",token);

        ResponseEntity<AddTaskResponse> responseEntity = RestTemplateUtils.post("http://cot5b.u02cx.com:20086/whatsApi/api/task/addTask", httpHeaders, requestBody, AddTaskResponse.class);
        Preconditions.checkState(responseEntity.getStatusCode().is2xxSuccessful(), "addTask failed");

        AddTaskResponse addTaskResponse = responseEntity.getBody();
        Preconditions.checkNotNull(addTaskResponse, "addTaskResponse is null");

        int status = addTaskResponse.getStatus();
        Preconditions.checkState(0 == status, "addTask failed:" + ApiConstants.returnCodeMap.get(status)+",msg:"+addTaskResponse.getMsg());



        return addTaskResponse.getTaskId();
    }
}
