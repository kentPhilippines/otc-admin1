package com.ruoyi.system.api.entity.U02cx;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetTokenRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 每个用户的各自独立Id
     */
    private String appId;

    /**
     * 配套appId的口令
     */
    private String secret;
}
