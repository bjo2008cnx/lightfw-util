
package org.lightfw.util.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志帮助类
 *
 * @author jason
 */
public class LogUtil {
    public static Logger getLogger(String className) {
        return LoggerFactory.getLogger(className);
    }

    public static Logger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }
}