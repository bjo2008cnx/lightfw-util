package org.lightfw.utilx.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: NoCacheFilter
 * @Description: 禁止浏览器缓存所有动态页面
 *
 <filter>
    <filter-name>BrowerCacheFilter</filter-name>
    <filter-class>me.gacl.web.filter.NoCacheFilter</filter-class>
</filter>

<filter-mapping>
    <filter-name>BrowerCacheFilter</filter-name>
    <servlet-name>*.jsp</servlet-name>
</filter-mapping>
 */
public class BrowserDynamicCacheForbidenFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //把ServletRequest强转成HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) req;
        //把ServletResponse强转成HttpServletResponse
        HttpServletResponse response = (HttpServletResponse) resp;
        //禁止浏览器缓存所有动态页面
        response.setDateHeader("Expires", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void destroy() {

    }
}