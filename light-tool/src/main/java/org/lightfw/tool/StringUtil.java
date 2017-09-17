package org.lightfw.tool;

/**
 * StringUtil
 *
 * @author Michael.Wang
 * @date 2017/9/15
 */
public class StringUtil {
    public static String toUpperFirst(String str) {
        if (str == null || "".equals(str)) {
            throw new RuntimeException("String is empty.");
        }
        return str.replace(str.substring(0, 1), str.substring(0, 1).toUpperCase());
    }
}