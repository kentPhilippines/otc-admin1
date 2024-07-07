package com.ruoyi.system.api.entity.U02cx;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class GetReportRequest extends BaseRequestBO{

    private Long taskId;
}
