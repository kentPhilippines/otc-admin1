package com.ruoyi.system.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class MonitorAddressAccount  implements Serializable {
    /**
     * 监听地址
     */
    private String monitorAddress;

    /**
     * 出账地址
     */
    private String accountAddress;

    /**
     * 单位
     */
    private Integer price;

    /**
     * trx或者usdt
     */
    private String monitorType;

    /**
     * 是否有效
     */
    private String isValid;

    /**
     * 加密之后的账户秘钥
     */
    private String encryptPrivateKey;

    /**
     * 用于解密加密之后的账户秘钥的秘钥
     */
    private String encryptKey;

    private String apiKey;


}
