package org.lightfw.utilx.web.filter.risk;

import lombok.extern.slf4j.Slf4j;
import org.lightfw.utilx.web.UrlUtil;
import org.lightfw.utilx.web.request.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

/**
 * RiskControlValidater
 *
 * @author Michael.Wang
 * @date 2017/11/1
 */
@Slf4j
public class RiskControlValidater {
    /**
     * 配置容器
     */
    public static final Map RISK_CONTROL_CONFIG = new HashMap();

    /**
     * 校验是否需要风控
     *
     * @param request
     * @param resp
     */
    public static void validate(HttpServletRequest request, HttpServletResponse resp) {
        validate(RequestUtil.isGetRequest(request), "GET Request is IGNORED.");
        validate(RequestUtil.isUploadRequest(request), "Uploading Request is IGNORED.");
        validate(isExcludedPage(request), "Excluded page is IGNORED.");
        validate(isRiskOff(), "Risk Control switch is off..");
    }

    public static boolean isExcludedPage(HttpServletRequest request) {
        String path = request.getServletPath();
        SortedSet excludedPages = (SortedSet) RISK_CONTROL_CONFIG.get("excludedPages");
        return UrlUtil.urlMatch(excludedPages, path);
    }

    public static boolean isRiskOff() {
        return false;
    }

    private static void validate(boolean toAssert, String message) {
        if (!toAssert) {
            log.info(message);
            throw new RiskControlValidateException(message);
        }
    }
}