package com.ruoyi.system.api.impl;

import cn.hutool.json.JSONUtil;
import com.ruoyi.common.utils.http.RestTemplateUtils;
import com.ruoyi.system.api.IOkxApi;
import com.ruoyi.system.api.entity.okx.OkxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
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
        String apiKey = "eb3163ad-5221-491a-9ae9-e74e9637574c";
        headers.add("OK-ACCESS-KEY", apiKey);
        String passphrase = "CHenduoqi0101!";
        headers.add("OK-ACCESS-PASSPHRASE", passphrase);
        String method = "GET";
        String sign = generateSignature(timestamp, "get", "/api/v5/market/ticker?instId=TRX-USDT", "", "4CAAAA212D525D14AA1012E24B5FABDD");
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
}
