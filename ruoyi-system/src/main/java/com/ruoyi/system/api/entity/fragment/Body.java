package com.ruoyi.system.api.entity.fragment;

import com.ruoyi.system.api.entity.Params;
import lombok.Data;

import java.io.Serializable;
@Data
public class Body implements Serializable {

    private static final long serialVersionUID = 1L;
    private String type;
    private Params params;
    private Response_options response_options;
    private long expires_sec;
}
