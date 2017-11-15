package org.lightfw.utilx.web.filter.risk;

import lombok.extern.slf4j.Slf4j;
import org.lightfw.utilx.web.filter.risk.validater.RiskControlValidateException;

/**
 * RiskControlHelper
 *
 * @author Michael.Wang
 * @date 2017/11/8
 */
@Slf4j
public class RiskControlHelper {
    public static void validate(boolean toAssert, String message) {
        if (!toAssert) {
            log.info(message);
            throw new RiskControlValidateException(message);
        }
    }
}