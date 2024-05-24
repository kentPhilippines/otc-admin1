package com.ruoyi.system.api.impl;

import com.ruoyi.common.utils.http.RestTemplateUtils;
import com.ruoyi.system.api.ITronScanApi;
import com.ruoyi.system.api.entity.tronscan.AccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class TronScanApiImpl implements ITronScanApi {

    private static List<String> apiKeyList = new ArrayList();

    static {

        apiKeyList.add("3ee210c5-6508-4868-8feb-c1199cec9599");
        apiKeyList.add("6c06ab7f-5020-4ddd-9502-1c8fb708a16b");
        apiKeyList.add("830092eb-82b3-4f75-a9d7-a75cd5ce3bec");
    }
    @Override
    public AccountResponse getAccount(String monitorAddress) {


        Random random = new Random();
        int i = random.nextInt(3);
        HttpHeaders headers = new HttpHeaders();
//        Collections.shuffle(apiKeyList);
        String headerValue = apiKeyList.get(i);
        log.info("tronscan api key:{}", headerValue);
        headers.add("TRON-PRO-API-KEY", headerValue);

        ResponseEntity<AccountResponse> responseResponseEntity = RestTemplateUtils.get("https://apilist.tronscanapi.com/api/accountv2?address=" + monitorAddress, headers, AccountResponse.class);

        if (responseResponseEntity == null) {
            return null;
        }
        HttpStatus statusCode = responseResponseEntity.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            log.error("获取trx交易信息失败:{}", monitorAddress);
            return null;
        }

        AccountResponse body = responseResponseEntity.getBody();
        if (body == null) {
            return null;
        }
        return body;

    }
}
