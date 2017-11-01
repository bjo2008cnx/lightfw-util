package org.lightfw.utilx.web.filter.risk;

import lombok.extern.log4j.Log4j2;
import org.lightfw.utilx.web.request.RequestUtil;
import org.lightfw.utilx.web.request.RequestValueUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.lightfw.utilx.web.filter.risk.RiskControlHelper.isRiskOff;

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

    /**
     * 需要排除的页面
     */
    private String excludedPages = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            excludedPages = filterConfig.getInitParameter("excludedPages");
            RiskValidater. RISK_CONTROL_CONFIG
        } catch (Throwable t) {
            log.error("fail to load exclude pages config from web.xml.", t);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            //GET请求或upload被忽略
            if (RequestUtil.isUploadRequest(req) || isExcludedPage(excludedPages) || isRiskOff()) {
                log.debug("Request is GET or multipart request.Ignored.");
                chain.doFilter(request, response);
                return;
            }

            String requestBody = RequestValueUtil.parseRequestValues(request);
            HttpServletResponse res = (HttpServletResponse) response;
            boolean validateResult = isValidateResult(req, requestBody);
            handleError(request, req, res, validateResult);
        } catch (Throwable e) {
            log.error("fail to filter", e);
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        excludedPages = null;
    }
}