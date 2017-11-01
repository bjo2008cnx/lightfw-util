package org.lightfw.utilx.web.request;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.lightfw.constant.HeaderConstant;
import org.lightfw.constant.RequestConstant;
import org.lightfw.util.lang.StringUtil;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@Slf4j
public class RequestUtil {

    private static final String ACCEPT = HeaderConstant.ACCEPT;
    private static final String X_FORWARDED_FOR = RequestConstant.X_FORWARDED_FOR;
    private static final String X_REAL_IP = RequestConstant.X_REAL_IP;
    private static final String MULTIPART_FORM_DATA_VALUE = RequestConstant.MULTIPART_FORM_DATA_VALUE;
    private static final String X_REQUESTED_WITH = RequestConstant.X_REQUESTED_WITH;
    private static final String XML_HTTP_REQUEST = RequestConstant.XML_HTTP_REQUEST;

    /**
     * 功能: 从request得到IP地址
     *
     * @param request
     * @return
     */
    public static String getIp(ServletRequest request) {
        String ip = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            ip = req.getHeader(X_FORWARDED_FOR);
            if (isEmptyOrUnknown(ip)) {
                ip = req.getHeader(X_REAL_IP);
            } else if (ip != null && ip.indexOf(",") > 0) {
                ip = ip.trim();
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        ip = isEmptyOrUnknown(ip) ? request.getRemoteAddr() : ip;
        return ip;
    }

    private static boolean isEmptyOrUnknown(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

    /**
     * 判断是否ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader(X_REQUESTED_WITH);
        if (header != null && header.equalsIgnoreCase(XML_HTTP_REQUEST)) {
            //如果是ajax请求响应头会有，x-requested-with
            return true;
        }
        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        if (contentType.contains(RequestConstant.APPLICATION_FORM_URLENCODED_VALUE)) {
            return false;
        } else if (contentType.contains(RequestConstant.APPLICATION_JSON_VALUE)) {
            return true;
        } else if (contentType.contains(RequestConstant.MULTIPART_FORM_DATA_VALUE)) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * 判断mediaType是否是APPLICATION_JSON
     *
     * @param request
     * @return
     */
    public static boolean isJsonRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            return false;
        }
        String acceptHeader = ((HttpServletRequest) request).getHeader(ACCEPT);
        if (StringUtil.isNotEmpty(acceptHeader)) {
            List<MediaType> mediaTypes = MediaType.parseMediaTypes(acceptHeader);
            MediaType.sortBySpecificityAndQuality(mediaTypes);
            MediaType mediaType = mediaTypes.get(0);
            if (mediaType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取请求路径
     *
     * @param request
     * @return
     */
    public static String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();
        if (request.getPathInfo() != null) {
            url += request.getPathInfo();
        }
        return url;
    }

    /**
     * 是否微信环境
     *
     * @param request
     * @return
     */
    public static boolean isInWechat(HttpServletRequest request) {
        String userAgent = request.getHeader(HeaderConstant.USER_AGENT);
        boolean isInWeChat = userAgent != null && userAgent.toLowerCase(Locale.CHINESE).contains(RequestConstant.WECHAT_AGENT);
        return isInWeChat;
    }

    /**
     * 是否GET请求
     *
     * @param req
     * @return
     */
    public static boolean isGetRequest(HttpServletRequest req) {
        return "GET".equals(req.getMethod());
    }

    /**
     * 判断是否二进制文件请求
     *
     * @param req
     * @return
     */
    public static boolean isUploadRequest(HttpServletRequest req) {
        String contentType = req.getHeader(HttpHeaders.CONTENT_TYPE);
        if (contentType != null && contentType.contains(MULTIPART_FORM_DATA_VALUE)) {
            return true;
        }
        return false;
    }
}
