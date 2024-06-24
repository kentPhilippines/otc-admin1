package com.ruoyi.system.api.entity.U02cx;

import lombok.Data;

@Data
public class GetTokenResponse extends U02cxCommonResponse{

    /**
     * token值 时效 1小时
     */
    private String token;
}
