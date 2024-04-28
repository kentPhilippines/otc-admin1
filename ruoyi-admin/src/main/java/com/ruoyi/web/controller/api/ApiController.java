package com.ruoyi.web.controller.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.vo.TronInfoVO;
import com.ruoyi.system.service.IApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private IApiService apiService;
    /**
     * 获取波场数据
     * @return
     */
    @GetMapping("/tronInfo")
    public R<TronInfoVO> getTronInfo() throws Exception {
        TronInfoVO tronInfoVO = apiService.getTronInfo();
        return R.ok(tronInfoVO);
    }
}
