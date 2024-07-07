package com.ruoyi.system.api.entity.U02cx;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetTaskListResponse extends U02cxCommonResponse{

    private List<TaskDetailResponse> data;
}
