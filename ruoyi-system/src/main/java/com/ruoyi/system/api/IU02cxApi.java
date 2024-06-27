package com.ruoyi.system.api;

import com.ruoyi.system.api.entity.U02cx.GetTokenRequest;

import java.io.IOException;

public interface IU02cxApi {

    String getToken(GetTokenRequest getTokenRequest);


    Long addTask(String requestBody, String token, String appId) throws IOException;
}
