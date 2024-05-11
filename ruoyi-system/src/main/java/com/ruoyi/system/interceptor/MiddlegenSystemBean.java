package com.ruoyi.system.interceptor;


import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MiddlegenSystemBean {

    public static final String DIGEST_EXECUTION = "execution( * com.ruoyi.system.api.*.*(..))";

    /**
     * 切面摘要
     * @return
     */
    @Bean(name = "systemPointcutAdvisor")
    public DefaultPointcutAdvisor defaultPointcutAdvisor(){

        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(DIGEST_EXECUTION);

        SystemDigestInterceptor systemDigestInterceptor = new SystemDigestInterceptor();

        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
        defaultPointcutAdvisor.setAdvice(systemDigestInterceptor);
        defaultPointcutAdvisor.setPointcut(aspectJExpressionPointcut);
        return defaultPointcutAdvisor;
    }
}
