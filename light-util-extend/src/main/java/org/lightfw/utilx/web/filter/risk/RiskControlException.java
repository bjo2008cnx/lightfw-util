package org.lightfw.utilx.web.filter.risk;

/**
 * RiskControlException
 *
 * @author Michael.Wang
 * @date 2017/11/1
 */
public class RiskControlException extends RuntimeException {

    public RiskControlException() {
        super();
    }

    public RiskControlException(String message) {
        super(message);
    }
}