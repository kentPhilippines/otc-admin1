package com.ruoyi.web.core.config;

import com.ruoyi.common.utils.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfiguration {
    @Value("${snowflake.workerId}")
    private long workerId;
    @Value("${snowflake.dataCenterId}")
    private long dataCenterId;
    @Bean
    public SnowFlakeUtil getSnowFlakeUtil(){
        return new SnowFlakeUtil(dataCenterId,workerId);
    }
}
