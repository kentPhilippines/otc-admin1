package com.ruoyi.system.api;

import com.ruoyi.system.api.entity.tronscan.AccountResponse;

public interface ITronScanApi {
    AccountResponse getAccount(String monitorAddress);
}
