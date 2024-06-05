package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.TronInfoVO;

public interface IApiService {
    TronInfoVO getTronInfo() throws Exception;

    String transferusdt(String amount) throws Exception;
}
