package com.ruoyi.system.api.entity.U02cx;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class BaseRequestBO implements Serializable {

    private final static long serialVersionUID = 1L;

    private String encryptedData;
    private String token;
    private String appId;


}
