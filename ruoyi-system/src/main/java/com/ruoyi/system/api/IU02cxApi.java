package com.ruoyi.system.api;

import com.ruoyi.system.api.entity.U02cx.*;

import java.io.IOException;
import java.util.List;

public interface IU02cxApi {

    String getToken(GetTokenRequest getTokenRequest);


    Long addTask(BaseRequestBO baseRequestBO) throws IOException;


    void updateTaskStatus(BaseRequestBO baseRequestBO);

    void updateTask(BaseRequestBO baseRequestBO);

    void completeByBatch(BatchTaskIdsRequest batchTaskIdsRequest);

    List<TaskDetailResponse> getTaskList(BatchTaskIdsRequest batchTaskIdsRequest);

    byte[] getReport(GetReportRequest getReportRequest);
}
