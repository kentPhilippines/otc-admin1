package com.ruoyi.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 错误信息处理类。
 *
 * @author ruoyi
 */
public class ExceptionUtil
{

    private ExceptionUtil() {}

    /**
     * 获取exception的详细错误信息。
     */
    public static String getExceptionMessage(Throwable e)
    {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }

    public static String getRootErrorMessage(Exception e)
    {
        Throwable root = ExceptionUtils.getRootCause(e);
        if (null == root)
        {
            return StringUtils.EMPTY;
        }
        return StringUtils.defaultString(root.getMessage(), "null");
    }

    /**
     * 检测异常e被触发的原因是不是因为异常cause。
     * 
     * @param e 捕获的异常。
     * @param cause 异常触发原因。
     * @return 如果异常e是由cause类异常触发，则返回true；否则返回false。
     */
    public static boolean isCausedBy(final Throwable e, final Class<? extends Throwable> cause)
    {
        if (cause.isAssignableFrom(e.getClass()))
        {
            return true;
        }
        else
        {
            Throwable t = e.getCause();
            while (t != null && t != e)
            {
                if (cause.isAssignableFrom(t.getClass()))
                {
                    return true;
                }
                t = t.getCause();
            }
            return false;
        }
    }
}
