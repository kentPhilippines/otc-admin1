package com.ruoyi.web.controller.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.vo.TronInfoVO;
import com.ruoyi.system.service.IApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
//@CrossOrigin
public class ApiController {

    @Autowired
    private IApiService apiService;

    /**
     * 获取波场数据
     *
     * @return
     */
    @GetMapping("/tronInfo")
    public R<TronInfoVO> getTronInfo() throws Exception {
        TronInfoVO tronInfoVO = apiService.getTronInfo();
        return R.ok(tronInfoVO);
    }

    @GetMapping("/transferusdt/{amount}")
    public R<String> transferusdt(@PathVariable("amount") String amount) throws Exception {
         String txid = apiService.transferusdt(amount);
        return R.ok(txid);
    }

}
