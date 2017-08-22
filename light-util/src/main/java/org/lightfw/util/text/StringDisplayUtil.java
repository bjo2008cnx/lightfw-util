package org.lightfw.util.text;

import org.lightfw.util.lang.StringUtil;

/**
 * 用于字符串显示的工具类
 *
 * @author Wangxm
 * @date 2016/5/5
 */
public class StringDisplayUtil {
    /**
     * 显示数据前过滤掉null，截取一定位数
     *
     * @param str
     * @param displayLength 最大显示的长度
     * @return
     */
    public static String prt(String str, int displayLength) {
        if (str != null) {
            if (str.length() >= displayLength) {
                return str.substring(0, displayLength);
            } else {
                return str;
            }
        } else {
            return "";
        }
    }

    public static String prt(Object obj, int index) {
        if (obj != null) {
            return prt(obj.toString(), index);
        } else {
            return "";
        }
    }

    /**
     * 显示数据前过滤掉null，截取一定位数，并加上表示，如省略号
     *
     * @param str
     * @param index 最大显示的长度
     * @return
     */
    public static String prt(String str, int index, String replacement) {
        int accessionalLength = 0;
        if (index < 0) {
            return str;
        }
        if (StringUtil.isEmpty(replacement)) {
            replacement = "...";
        }
        accessionalLength = replacement.length();
        if (str != null) {
            if (index <= accessionalLength) {
                return str.substring(0, index);
            } else if (str.length() >= index - accessionalLength) {
                return str.substring(0, index - accessionalLength) + replacement;
            } else {
                return str;
            }
        } else {
            return "";
        }
    }

    public static String prt(Object obj, int index, String accessional) {
        if (obj != null)
            return prt(obj.toString(), index, accessional);
        else {
            return "";
        }
    }

}
