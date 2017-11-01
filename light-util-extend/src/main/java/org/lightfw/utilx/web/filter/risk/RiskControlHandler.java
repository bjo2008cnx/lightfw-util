package org.lightfw.utilx.web.filter.risk;

import lombok.extern.slf4j.Slf4j;
import org.lightfw.util.lang.StringUtil;
import org.lightfw.utilx.web.request.RequestUtil;
import org.lightfw.utilx.web.request.RequestValueUtil;
import org.lightfw.utilx.web.request.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * RiskControlHandler
 *
 * @author Michael.Wang
 * @date 2017/11/1
 */
@Slf4j
public class RiskControlHandler {

    private static final String ERROR_PAGE = "error_page.html";

    public static void handle(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        RiskControlValidater.validate(request, resp);
        String requestBody = RequestValueUtil.parseRequestValues(request);
        boolean validResult = call(requestBody);
        if (!validResult) {
            handleError(request, resp);
        }
    }

    /**
     * 获取风控结果
     *
     * @param requestBody
     * @return
     */
    private static boolean call(String requestBody) {
        log.info("requestBody to filter is : {}", requestBody);
        if (StringUtil.isEmpty(requestBody)) {
            return true;
        }

        boolean result = true;  //实际场景下需要改成下句: result= qualityRiskControl(requestBody);
        log.info("risk handle result is: " + result);
        return result;
    }

    /**
     * 处理异常，如果是ajax,输出json;否则，跳转到错误页
     *
     * @param request
     * @param resp
     * @throws IOException
     */
    public static void handleError(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        if (RequestUtil.isJsonRequest(request)) {
            log.debug("output error message.........");

            Map json = new HashMap<>();
            json.put("status", "1");
            json.put("errCode", "999");
            json.put("msg", "您的输入可能包含敏感信息，请修改后重试。");
            ResponseUtil.writeAjaxResponse(resp, json, "UTF-8");
        } else {
            log.debug("redirecting.........");
            resp.sendRedirect(request.getContextPath() + ERROR_PAGE);
        }
    }
}