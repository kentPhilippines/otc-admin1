package com.ruoyi.system.api.entity.U02cx;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BatchTaskIdsRequest extends BaseRequestBO{

    private List<Long> taskIds;
}
