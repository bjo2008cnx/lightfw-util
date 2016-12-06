package org.lightfw.utilx.web.request;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
    /**
     * 功能: 从request得到IP地址
     *
     * @param request
     * @return
     */
    public static String getIp(ServletRequest request) {
        String ip = null;
        if (request instanceof HttpServletRequest) {
            ip = ((HttpServletRequest) request).getHeader("X-Forwarded-For");
            if (ip != null && ip.trim().indexOf(",") > 0) {
                ip = ip.trim().substring(0, ip.trim().indexOf(","));
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = ((HttpServletRequest) request).getHeader("X-Real-IP");
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 判断是否ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            //如果是ajax请求响应头会有，x-requested-with
            return true;
        }
        return false;
    }
}
