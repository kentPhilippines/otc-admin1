package com.ruoyi.web.controller.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.vo.TronInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    /**
     * 获取波场数据
     * @return
     */
    @GetMapping("/tronInfo")
    public R<TronInfoVO> getTronInfo() {
        return R.ok(new TronInfoVO());
    }
}
