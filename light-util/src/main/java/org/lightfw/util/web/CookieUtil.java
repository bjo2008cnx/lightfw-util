package org.lightfw.util.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie util
 */
public class CookieUtil {

    private static final String SESSION_ID_COOKIE = "osessionid";

    /**
     * 取cookies
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    /**
     * 取cookie对象
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        if (cookieName == null || request == null) {
            return null;
        }
        Cookie[] cks = request.getCookies();
        if (cks == null) {
            return null;
        }
        for (Cookie cookie : cks) {
            if (cookieName.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 增加cookie
     *
     * @param response
     * @param name
     * @param value
     * @param maxAge
     * @param domain
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge, String domain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setDomain(domain);
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 获取session id
     *
     * @param request
     * @return
     */
    public static String getSessionId(HttpServletRequest request) {
        return CookieUtil.getCookieValue(request, SESSION_ID_COOKIE);
    }
}
