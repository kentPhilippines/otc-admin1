package com.ruoyi.system.api.entity;

import com.ruoyi.system.api.entity.fragment.Messages;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class Params implements Serializable {

    private static final long serialVersionUID = 1L;

    private long valid_until;
    private List<Messages> messages;
    private String source;
}
