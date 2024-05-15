package com.ruoyi.system.api.impl;


import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.domain.entity.TgPremiumOrderInfo;
import com.ruoyi.system.api.IFragmentApi;
import com.ruoyi.system.api.entity.fragment.*;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;

@Component
@Slf4j
public class FragmentApiImpl implements IFragmentApi {

    @Autowired
    private ISysConfigService configService;

    @Override
    public SearchPremiumGiftRecipientResponse searchPremiumGiftRecipient(TgPremiumOrderInfo tgPremiumOrderInfo) throws IOException {


        String hash = configService.selectConfigByKey("sys.fragment.hash");
        String cookie = configService.selectConfigByKey("sys.fragment.cookie");
        // 设置请求URL
        String url = "https://fragment.com/api?hash=" + hash;

        // 创建 HttpClient 实例
        HttpClient httpClient = HttpClients.createDefault();

        // 创建 HttpPost 请求对象
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        httpPost.setHeader("Cookie", cookie);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // 构建请求体参数
        String formBody = "query=" + tgPremiumOrderInfo.getRechargeTgUserName() + "&months=" + tgPremiumOrderInfo.getMonths() + "&method=searchPremiumGiftRecipient";
        StringEntity requestEntity = new StringEntity(formBody, ContentType.APPLICATION_FORM_URLENCODED);
        httpPost.setEntity(requestEntity);

        // 发送请求并获取响应
        HttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("searchPremiumGiftRecipient 响应码错误:" + response.getStatusLine().getStatusCode());
        }
        // 处理响应
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 将响应内容转换为字符串
            String responseString = EntityUtils.toString(entity);
            SearchPremiumGiftRecipientResponse searchPremiumGiftRecipientResponse = JSONUtil.toBean(responseString, SearchPremiumGiftRecipientResponse.class);
            return searchPremiumGiftRecipientResponse;
        }
        return null;
    }

    @Override
    public InitGiftPremiumResponse initGiftPremium(String recipient, Long months) throws IOException {
        String hash = configService.selectConfigByKey("sys.fragment.hash");
        String cookie = configService.selectConfigByKey("sys.fragment.cookie");
        // 设置请求URL
        String url = "https://fragment.com/api?hash=" + hash;

        // 创建 HttpClient 实例
        HttpClient httpClient = HttpClients.createDefault();

        // 创建 HttpPost 请求对象
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        httpPost.setHeader("Cookie", cookie);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // 构建请求体参数
        String formBody = "recipient=" + recipient + "&months=" + months + "&method=initGiftPremiumRequest";
        StringEntity requestEntity = new StringEntity(formBody, ContentType.APPLICATION_FORM_URLENCODED);
        httpPost.setEntity(requestEntity);

        // 发送请求并获取响应
        HttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("initGiftPremium 响应码错误:" + response.getStatusLine().getStatusCode());
        }
        // 处理响应
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 将响应内容转换为字符串
            String responseString = EntityUtils.toString(entity);
            InitGiftPremiumResponse initGiftPremiumResponse = JSONUtil.toBean(responseString, InitGiftPremiumResponse.class);
            return initGiftPremiumResponse;
        }


        return null;
    }

    @Override
    public GetGiftPremiumLinkResponse getGiftPremiumLink(String reqId) throws IOException {

        String hash = configService.selectConfigByKey("sys.fragment.hash");
        String cookie = configService.selectConfigByKey("sys.fragment.cookie");
        // 设置请求URL
        String url = "https://fragment.com/api?hash=" + hash;

        // 创建 HttpClient 实例
        HttpClient httpClient = HttpClients.createDefault();

        // 创建 HttpPost 请求对象
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        httpPost.setHeader("Cookie", cookie);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // 构建请求体参数
        String formBody = "id=" + reqId + "&show_sender=0&method=getGiftPremiumLink";
        StringEntity requestEntity = new StringEntity(formBody, ContentType.APPLICATION_FORM_URLENCODED);
        httpPost.setEntity(requestEntity);

        // 发送请求并获取响应
        HttpResponse response = httpClient.execute(httpPost);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("getGiftPremiumLink 响应码错误:" + response.getStatusLine().getStatusCode());
        }
        // 处理响应
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 将响应内容转换为字符串
            String responseString = EntityUtils.toString(entity);
            GetGiftPremiumLinkResponse getGiftPremiumLinkResponse = JSONUtil.toBean(responseString, GetGiftPremiumLinkResponse.class);
            return getGiftPremiumLinkResponse;
        }


        return null;
    }

    @Override
    public RawResponse rawRequest(String reqId) throws IOException {
        String cookie = configService.selectConfigByKey("sys.fragment.cookie");
        // 设置请求URL
        String url = "https://fragment.com/tonkeeper/rawRequest?id=" + reqId + "&qr=1";

        // 创建 HttpClient 实例
        HttpClient httpClient = HttpClients.createDefault();

        // 创建 HttpPost 请求对象
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        httpPost.setHeader("Cookie", cookie);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // 构建请求体参数
        String formBody = "id=" + reqId + "&show_sender=1&method=getGiftPremiumLink";
        StringEntity requestEntity = new StringEntity(formBody, ContentType.APPLICATION_FORM_URLENCODED);
        httpPost.setEntity(requestEntity);

        // 发送请求并获取响应
        HttpResponse response = httpClient.execute(httpPost);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("rawRequest 响应码错误:" + response.getStatusLine().getStatusCode());
        }
        // 处理响应
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 将响应内容转换为字符串
            String responseString = EntityUtils.toString(entity);
            RawResponse rawResponse = JSONUtil.toBean(responseString, RawResponse.class);
            return rawResponse;
        }

        return null;
    }

    @Override
    public void sendTransactions(String address, BigDecimal amount, String comment) throws IOException {

        // 设置请求URL
        String url = "http://127.0.0.1:8888/sendTransactions?send_mode=3&comment=" + URLEncoder.encode(comment);

        // 创建 HttpClient 实例
        HttpClient httpClient = HttpClients.createDefault();

        // 创建 HttpPost 请求对象
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        httpPost.setHeader("Content-Type", "application/json");

        // 构建请求体参数
        String formBody = "{\"" + address + "\":\"" + amount + "\"}";
        StringEntity requestEntity = new StringEntity(formBody, ContentType.APPLICATION_FORM_URLENCODED);
        httpPost.setEntity(requestEntity);
//        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000); // 设置连接超时时间为5秒
//        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000); // 设置读取超时时间为5秒
        // 发送请求并获取响应
        HttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != 200) {
            HttpEntity entity = response.getEntity();
            String responseString = null;
            if (entity != null) {
                 responseString = EntityUtils.toString(entity);
                log.error("responseString:{}", responseString);
            }
            throw new RuntimeException("sendTransactions 响应码错误:" + response.getStatusLine().getStatusCode()+"responseString:"+responseString);
        }

    }

    @Override
    public CheckReqRespose checkReq(String reqId) throws IOException {
        String hash = configService.selectConfigByKey("sys.fragment.hash");
        String cookie = configService.selectConfigByKey("sys.fragment.cookie");
        // 设置请求URL
        String url = "https://fragment.com/api?hash=" + hash;

        // 创建 HttpClient 实例
        HttpClient httpClient = HttpClients.createDefault();

        // 创建 HttpPost 请求对象
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        httpPost.setHeader("Cookie", cookie);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // 构建请求体参数
        String formBody = "id=" + reqId + "&method=checkReq";
        StringEntity requestEntity = new StringEntity(formBody, ContentType.APPLICATION_FORM_URLENCODED);
        httpPost.setEntity(requestEntity);

        // 发送请求并获取响应
        HttpResponse response = httpClient.execute(httpPost);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("checkReq 响应码错误:" + response.getStatusLine().getStatusCode());
        }
        // 处理响应
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 将响应内容转换为字符串
            String responseString = EntityUtils.toString(entity);
            CheckReqRespose checkReqRespose = JSONUtil.toBean(responseString, CheckReqRespose.class);
            return checkReqRespose;
        }
        return null;
    }
}
