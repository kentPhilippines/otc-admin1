package com.ruoyi.system.api;

import com.ruoyi.system.dto.TronGridResponse;

public interface ITronApi {

    TronGridResponse getTronGridResponse(String monitorAddress, String apiKey);
}
