package com.ruoyi.common.utils;

import com.ruoyi.common.json.JSON;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * 处理并记录日志文件
 *
 * @author ruoyi
 */
public class LogUtils {
    public static final Logger ERROR_LOG = LoggerFactory.getLogger("sys-error");
    public static final Logger ACCESS_LOG = LoggerFactory.getLogger("sys-access");

    /**
     * 记录访问日志 [username][jsessionid][ip][accept][UserAgent][url][params][Referer]
     *
     * @param request
     * @throws Exception
     */
    public static void logAccess(HttpServletRequest request) throws Exception {
        String username = getUsername();
        String jsessionId = request.getRequestedSessionId();
        String ip = IpUtils.getIpAddr(request);
        String accept = request.getHeader("accept");
        String userAgent = request.getHeader("User-Agent");
        String url = request.getRequestURI();
        String params = getParams(request);

        StringBuilder s = new StringBuilder();
        s.append(getBlock(username));
        s.append(getBlock(jsessionId));
        s.append(getBlock(ip));
        s.append(getBlock(accept));
        s.append(getBlock(userAgent));
        s.append(getBlock(url));
        s.append(getBlock(params));
        s.append(getBlock(request.getHeader("Referer")));
        getAccessLog().info(s.toString());
    }

    /**
     * 记录异常错误 格式 [exception]
     *
     * @param message
     * @param e
     */
    public static void logError(String message, Throwable e) {
        String username = getUsername();
        StringBuilder s = new StringBuilder();
        s.append(getBlock("exception"));
        s.append(getBlock(username));
        s.append(getBlock(message));
        ERROR_LOG.error(s.toString(), e);
    }

    /**
     * 记录页面错误 错误日志记录 [page/eception][username][statusCode][errorMessage][servletName][uri][exceptionName][ip][exception]
     *
     * @param request
     */
    public static void logPageError(HttpServletRequest request) {
        String username = getUsername();

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String message = (String) request.getAttribute("javax.servlet.error.message");
        String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
        Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");

        if (statusCode == null) {
            statusCode = 0;
        }

        StringBuilder s = new StringBuilder();
        s.append(getBlock(t == null ? "page" : "exception"));
        s.append(getBlock(username));
        s.append(getBlock(statusCode));
        s.append(getBlock(message));
        s.append(getBlock(IpUtils.getIpAddr(request)));

        s.append(getBlock(uri));
        s.append(getBlock(request.getHeader("Referer")));
        StringWriter sw = new StringWriter();

        while (t != null) {
            t.printStackTrace(new PrintWriter(sw));
            t = t.getCause();
        }
        s.append(getBlock(sw.toString()));
        getErrorLog().error(s.toString());

    }

    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }

    protected static String getParams(HttpServletRequest request) throws Exception {
        Map<String, String[]> params = request.getParameterMap();
        return JSON.marshal(params);
    }

    protected static String getUsername() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    public static Logger getAccessLog() {
        return ACCESS_LOG;
    }

    public static Logger getErrorLog() {
        return ERROR_LOG;
    }

    /**
     * 日志反转打印
     * @param t
     * @param causeDepth
     * @param counter
     * @param stackDepth
     * @return
     */
    public static String doRecursiveReversePrintStackCause(Throwable t, int causeDepth, ForwardCounter counter, int stackDepth){
        StringBuilder sb = new StringBuilder();
        LogUtils.recursiveReversePrintStackCause(t,causeDepth, counter,stackDepth,sb);
        return sb.toString();
    }

    /**
     * 递归你想打印堆栈及cause
     *
     * @param t              原始异常
     * @param causeDepth     需要递归打印的cause的最大深度
     * @param counter 当前打印cause的计数器
     * @param stackDepth     每一个异常栈的打印深度
     * @param sb             字符串构造器
     */
    public static void recursiveReversePrintStackCause
    (Throwable t, int causeDepth, ForwardCounter counter, int stackDepth, StringBuilder sb) {
        if (t == null) {
            return;
        }
        if (t.getCause() != null) {
            recursiveReversePrintStackCause(t.getCause(), causeDepth, counter, stackDepth, sb);
        }
        if (counter.count++ < causeDepth) {
            doPrintStack(t, stackDepth, sb);
        }
    }

    private static void doPrintStack(Throwable t, int stackDepth, StringBuilder sb) {
        StackTraceElement[] stackTrace = t.getStackTrace();
        if (sb.lastIndexOf("\n\t") > -1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("Caused: ");
        sb.append(t.getClass().getName()).append(": ").append(t.getMessage()).append("\n\t");

        for (int i = 0; i < stackDepth; ++i) {
            if (i >= stackTrace.length){
                break;
            }
            StackTraceElement stackTraceElement = stackTrace[i];
            sb.append(stackTraceElement.getClassName()).append("#")
                    .append(stackTraceElement.getMethodName()).append(":")
                    .append(stackTraceElement.getLineNumber()).append("\n\t");
        }
    }
}
