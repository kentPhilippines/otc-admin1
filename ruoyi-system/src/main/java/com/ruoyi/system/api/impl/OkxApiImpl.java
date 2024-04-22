package com.ruoyi.system.api.impl;

import cn.hutool.json.JSONUtil;
import com.ruoyi.common.utils.http.RestTemplateUtils;
import com.ruoyi.system.api.IOkxApi;
import com.ruoyi.system.api.entity.okx.OkxResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;

@Component
@Slf4j
public class OkxApiImpl implements IOkxApi {
    @Override
    public OkxResponse getSingleTickerOkxResponse() throws NoSuchAlgorithmException, InvalidKeyException {

        //apikey = "b5937540-0817-41f8-bff3-3fda1262c744"
        //secretkey = "4CAAAA212D525D14AA1012E24B5FABDD"
        //IP = ""
        //备注名 = "实时查询费率"
        //权限 = "读取"
        HttpHeaders headers = new HttpHeaders();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timestamp = dateFormat.format(new Date());
        headers.add("OK-ACCESS-TIMESTAMP", timestamp);

        String apiKey = "b5937540-0817-41f8-bff3-3fda1262c744";
        headers.add("OK-ACCESS-KEY", apiKey);

        String passphrase = "CHenduoqi0101!";
        headers.add("OK-ACCESS-PASSPHRASE", passphrase);

        String method = "GET";
        String sign = generateSignature(timestamp, method, "/api/v5/market/ticker?instId=TRX-USDT", "", "4CAAAA212D525D14AA1012E24B5FABDD");
        headers.add("OK-ACCESS-SIGN", sign);
        //监听
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://www.okx.com/api/v5/market/ticker?instId=TRX-USDT");
        String uriString = builder.toUriString();
        ResponseEntity   responseEntity = RestTemplateUtils.get(uriString, headers, String.class);
        if (responseEntity == null) {
            log.error("getSingleTickerOkxResponse:responseEntity is null");
            return null;
        }
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            log.error("getSingleTickerOkxResponse响应失败:{}", JSONUtil.toJsonStr(responseEntity));
            return null;
        }
        Object responseEntityBody = responseEntity.getBody();
        if (responseEntityBody == null) {
            log.warn("getSingleTickerOkxResponse:responseEntityBody is null");
            return null;
        }

        OkxResponse okxResponse = JSONUtil.toBean((String) responseEntityBody, OkxResponse.class);
        return okxResponse;
    }

    private static String generateSignature(String timestamp, String method, String requestPath, String body, String secretKey)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String preHash = timestamp + method + requestPath + body;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacSha256 = mac.doFinal(preHash.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hmacSha256);
    }

    @Override
    public OkxResponse getSingleTickerOkxResponse2() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        String requestURI = "/api/v5/market/ticker?instId=TRX-USDT";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://www.okx.com";
//        HttpGet httpGet = new HttpGet(url + "/api/v5/market/tickers?instType=SWAP");
        HttpGet httpGet = new HttpGet(url + requestURI);

        // 设置OK-ACCESS-TIMESTAMP
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timestamp = dateFormat.format(new Date());
        httpGet.setHeader("OK-ACCESS-TIMESTAMP", timestamp);

        // 设置API密钥
        String apiKey = "b5937540-0817-41f8-bff3-3fda1262c744";
        httpGet.setHeader("OK-ACCESS-KEY", apiKey);

        // 设置Passphrase
        String passphrase = "CHenduoqi0101!";
        httpGet.setHeader("OK-ACCESS-PASSPHRASE", passphrase);

        // 设置Content-Type
        httpGet.setHeader(org.apache.http.HttpHeaders.CONTENT_TYPE, "application/json");

        // 设置OK-ACCESS-SIGN
        String method = "GET";
//        String requestPath = "/api/v5/market/tickers?instType=SWAP";
        String sign = generateSignature(timestamp, method, requestURI, "", "4CAAAA212D525D14AA1012E24B5FABDD");
        httpGet.setHeader("OK-ACCESS-SIGN", sign);

        // 发起请求
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            log.warn("getSingleTickerOkxResponse:responseEntityBody is null");
            return null;
        }
        String responseBody = EntityUtils.toString(response.getEntity());

        OkxResponse okxResponse = JSONUtil.toBean( responseBody, OkxResponse.class);
        // 处理响应
/*        String responseBody = EntityUtils.toString(response.getEntity());
        log.info("responseBody:{}", responseBody);*/
        return okxResponse;
    }


}
