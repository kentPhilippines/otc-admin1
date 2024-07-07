package com.ruoyi.system.api.entity.U02cx;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UpdateTaskStatusRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long taskId;

    private Integer taskStatus;
}
