package com.ruoyi.system.api.entity.tronscan;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class OwnerPermission implements Serializable {


    private static final long serialVersionUID = 1L;

    private List<Keys> keys;
    private int threshold;
    private String permission_name;
}
