package com.ruoyi.common.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 错误信息处理类。
 *
 * @author ruoyi
 */
public class ExceptionUtil
{
    /**
     * 获取exception的详细错误信息。
     */
    public static String getExceptionMessage(Throwable e)
    {
        // StringWriter#close()关闭是否都可以 但放入try-with-resources还需要处理抛出的IO异常
        StringWriter sw = new StringWriter();
        // PrintWriter#close()建议关闭 使用try-with-resources简洁关闭pw
        try(PrintWriter pw = new PrintWriter(sw, true)){
            e.printStackTrace(pw);
            return sw.toString();
        }
    }

    public static String getRootErrorMessage(Exception e)
    {
        Throwable root = ExceptionUtils.getRootCause(e);
        root = (root == null ? e : root);
        if (root == null)
        {
            return "";
        }
        String msg = root.getMessage();
        if (msg == null)
        {
            return "null";
        }
        return StringUtils.defaultString(msg);
    }
}
