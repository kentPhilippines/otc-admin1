package com.ruoyi.system.api.entity.fragment;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchPremiumGiftRecipientResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean ok;
    private Found found;
}
