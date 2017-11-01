package org.lightfw.utilx.web.filter.risk;

import lombok.extern.log4j.Log4j2;
import org.lightfw.utilx.web.request.RequestValueUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 风控过滤器.主要功能：
 * 1，白名单页面
 * 2，总开关
 * 3，套餐开关
 * 4，GET开关
 * 5, 白名单关键词
 *
 * @author Michael.Wang
 * @date 2017/9/8
 */
@Log4j2
public class RiskControlFilter implements Filter {
    private static final String ERROR_PAGE = "error_page.html";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            String excludedPages = filterConfig.getInitParameter("excludedPages");
            RiskValidater.RISK_CONTROL_CONFIG.put("excludedPages", excludedPages);
        } catch (Throwable t) {
            log.error("fail to load exclude pages config from web.xml.", t);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
           RiskValidater.validate(req,resp);

            String requestBody = RequestValueUtil.parseRequestValues(request);

            //boolean validateResult = isValidateResult(req, requestBody);
            //handleError(request, req, resp, validateResult);
        } catch (Throwable e) {
            log.error("fail to filter", e);
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        //excludedPages = null;
    }
}