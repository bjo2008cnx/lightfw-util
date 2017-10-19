package org.lightfw.tool;

/**
 * StringUtil
 *
 * @author Michael.Wang
 * @date 2017/9/15
 */
public class StringUtil {
    /**
     *  下划线转化为驼峰风格
     *
     * @param param
     * @return
     */
    public static final char UNDERLINE = '_';

    public static String toUpperFirst(String str) {
        if (str == null || "".equals(str)) {
            throw new RuntimeException("String is empty.");
        }
        return str.replace(str.substring(0, 1), str.substring(0, 1).toUpperCase());
    }

    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}