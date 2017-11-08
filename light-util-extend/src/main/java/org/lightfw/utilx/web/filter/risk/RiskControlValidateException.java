package org.lightfw.utilx.web.filter.risk;

/**
 * RiskControlValidateException
 *
 * @author Michael.Wang
 * @date 2017/11/1
 */
public class RiskControlValidateException extends RuntimeException {

    public RiskControlValidateException() {
        super();
    }

    public RiskControlValidateException(String message) {
        super(message);
    }
}