package org.lightfw.utilx.web.filter.risk;

import lombok.extern.slf4j.Slf4j;
import org.lightfw.util.lang.StringUtil;
import org.lightfw.utilx.web.request.RequestUtil;
import org.lightfw.utilx.web.request.ResponseUtil;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * RiskControlHelper
 *
 * @author Michael.Wang
 * @date 2017/11/1
 */
@Slf4j
public class RiskControlHelper {

    /**
     * 处理异常，如果是ajax,输出json;否则，跳转到错误页
     *
     * @param request
     * @param resp
     * @param validateResult
     * @throws IOException
     */
    public static void handleError(HttpServletRequest request, HttpServletResponse resp, boolean validateResult, String errorPage) throws IOException {
        if (validateResult) {
            return;
        }
        if (RequestUtil.isJsonRequest(request)) {
            log.debug("output error message.........");
            handleAjax(request, resp);
        } else {
            log.debug("redirecting.........");
            resp.sendRedirect(request.getContextPath() + errorPage);
        }
    }

    /**
     * 获取风控结果
     *
     * @param req
     * @param requestBody
     * @return
     */
    public static boolean isValidateResult(HttpServletRequest req, String requestBody) {
        log.info("uri is : {}, requestBody to filter is : {}", req.getRequestURI(), requestBody);
        boolean result;
        if (StringUtil.isEmpty(requestBody)) {
            result = true;
        } else {
            result = true;  //实际场景下需要改成下句: result= qualityRiskControl(requestBody, getRealClientIp(req));
        }
        log.info("risk control result is: " + result);
        return result;
    }

    public static void handleAjax(HttpServletRequest request, ServletResponse response) throws IOException {
        Map json = new HashMap<>();
        json.put("status", "1");
        json.put("errCode", "999");
        json.put("msg", "您的输入可能包含敏感信息，请修改后重试。");
        ResponseUtil.writeAjaxResponse(response, json, "UTF-8");
    }


}