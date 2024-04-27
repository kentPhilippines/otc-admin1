package com.ruoyi.system.api;

import com.ruoyi.system.dto.TronGridResponse;

public interface ITronApi {

    TronGridResponse getTronGridTrc20Response(String monitorAddress,  boolean only_to,boolean only_from,String apiKey, Long min_timestamp);
}
