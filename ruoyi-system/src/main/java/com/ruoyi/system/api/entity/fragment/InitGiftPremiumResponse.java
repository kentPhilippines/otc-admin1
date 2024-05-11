package com.ruoyi.system.api.entity.fragment;

import lombok.Data;

import java.io.Serializable;

@Data
public class InitGiftPremiumResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String req_id;
    private boolean myself;
    private String amount;
    private String item_title;
    private String content;
    private String button;
}
