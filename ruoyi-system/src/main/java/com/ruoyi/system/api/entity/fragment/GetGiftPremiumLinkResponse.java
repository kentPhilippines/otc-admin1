package com.ruoyi.system.api.entity.fragment;

import lombok.Data;

import java.io.Serializable;
@Data
public class GetGiftPremiumLinkResponse implements Serializable {
    private final static long serialVersionUID = 1L;

    private boolean ok;
    private String link;
    private String qr_link;
    private String check_method;
    private Check_params check_params;
    private int expire_after;
}
