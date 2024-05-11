package com.ruoyi.system.interceptor;


import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

@Slf4j
public class SystemDigestInterceptor implements MethodInterceptor {


    private static final String SEP = ",";
    private static final String NULL_REPLACEMENT = "null";

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {

        //被拦截的方法
        Method method = invocation.getMethod();
        //方法签名
        String invocationSignature = method.getDeclaringClass().getSimpleName() + SEP + method.getName();

        boolean isSuccess = true;

        long start = System.currentTimeMillis();
        Object proceed = null;
        try {
             proceed = invocation.proceed();
            return proceed;
        } catch (Exception e) {
            isSuccess = false;
            throw e;
        } finally {
            long elapseTime = System.currentTimeMillis() - start;
            if (log.isInfoEnabled()) {
                log.info(buildDigestLog(invocationSignature, isSuccess, elapseTime, invocation.getArguments(),proceed));
            }
        }
    }

    /**
     * 构造摘要日志
     *
     * @param invocationSignature 调用签名
     * @param isSuccess           是否成功
     * @param elapseTime          耗时
     * @param arguments           参数
     * @return 日志
     */
    private String buildDigestLog(String invocationSignature, boolean isSuccess, long elapseTime, Object[] arguments,Object proceed) {

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        {
            sb.append("(");
            sb.append(invocationSignature).append(SEP);
            sb.append(isSuccess ? "Y" : "N").append(SEP);
            sb.append(arguments == null ? NULL_REPLACEMENT : JSONUtil.toJsonStr(arguments)).append(SEP);
            sb.append(proceed == null ? NULL_REPLACEMENT : JSONUtil.toJsonStr(proceed)).append(SEP);
            sb.append(elapseTime).append("ms");
            sb.append(")");

        }
        sb.append("]");

        return sb.toString();
    }


}
