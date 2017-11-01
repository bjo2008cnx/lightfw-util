package org.lightfw.utilx.web.filter.risk;

import lombok.extern.slf4j.Slf4j;
import org.lightfw.utilx.web.request.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * RiskValidater
 *
 * @author Michael.Wang
 * @date 2017/11/1
 */
@Slf4j
public class RiskValidater {
    public static final Map<String, String> RISK_CONTROL_CONFIG = new HashMap();

    public static void validate(HttpServletRequest request, HttpServletResponse resp) {
        validate(RequestUtil.isGetRequest(request), "GET Request is IGNORED.");
        validate(RequestUtil.isUploadRequest(request), "Uploading Request is IGNORED.");
        validate(isExcludedPage(request), "Excluded page is IGNORED.");
        validate(isRiskOff(), "Risk Controling switch is off..");
    }

    public static boolean isExcludedPage(HttpServletRequest request) {
        return false;
    }

    public static boolean isRiskOff() {
        return false;
    }

    private static void validate(boolean toAssert, String message) {
        if (!toAssert) {
            throw new RiskControlException(message);
        }
    }
}