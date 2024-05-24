package com.ruoyi.system.api.entity.tronscan;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ActivePermissions implements Serializable {


    private static final long serialVersionUID = 1L;
    private String operations;
    private List<Keys> keys;
    private int threshold;
    private int id;
    private String type;
    private String permission_name;
}
