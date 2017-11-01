package org.lightfw.utilx.web.filter;

import lombok.extern.log4j.Log4j2;
import org.lightfw.util.lang.StringUtil;
import org.lightfw.utilx.web.request.RequestUtil;
import org.lightfw.utilx.web.request.RequestValueUtil;
import org.lightfw.utilx.web.request.ResponseUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    private String[] excludePath = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            String excludedPages = filterConfig.getInitParameter("excludedPages");
            excludePath = StringUtil.isEmpty(excludedPages) ? null : excludedPages.split(",");
        } catch (Throwable t) {
            log.error("fail to load exclude pages config from web.xml.", t);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            //GET请求或upload被忽略
            if (RequestUtil.isGetRequest(req) || RequestUtil.isUploadRequest(req)) {
                log.debug("is GET or multipart request.ignored.. ignored.");
                chain.doFilter(request, response);
                return;
            }

            String requestBody = RequestValueUtil.parseRequestValues(request);
            if (StringUtil.isNotEmpty(requestBody)) {
                log.debug("uri is : {}, requestBody to filter is : {}", req.getRequestURI(), requestBody);
                HttpServletResponse res = (HttpServletResponse) response;
                boolean validateResult = isValidateResult(req, requestBody);
                log.info("risk control result is: " + validateResult);
                if (!validateResult) {
                    if (RequestUtil.isJsonRequest(request)) {
                        handleAjax(req, response);
                    } else {
                        log.info("redirecting.........");
                        res.sendRedirect(((HttpServletRequest) request).getContextPath() + ERROR_PAGE);
                    }
                }
            }
        } catch (Throwable e) {
            log.error("fail to filter", e);
            chain.doFilter(request, response);
        }
    }

    private boolean isValidateResult(HttpServletRequest req, String requestBody) {
        //return RiskControlService.qualityRiskControl(requestBody, getRealClientIp(req));
        return true;
    }

    private void handleAjax(HttpServletRequest request, ServletResponse response) throws IOException {
        Map json = new HashMap<>();
        json.put("status", "1");
        json.put("errCode", "999");
        json.put("msg", "您的输入可能包含敏感信息，请修改后重试。");
        ResponseUtil.writeAjaxResponse(response, json, "UTF-8");
    }

    @Override
    public void destroy() {

    }
}