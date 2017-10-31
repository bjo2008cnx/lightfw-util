package org.lightfw.utilx.web;

import org.lightfw.util.lang.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * URL相关的工具类
 */
public class UrlUtil {
    /**
     * 替换路径中的数字为 toReplace 字符,主要用于统计
     * 例：/a/bc/-1/11112222333344455556666/test/-1/c =>/a/bc/X/X/test/X/c
     *
     * @param uri
     * @return
     */
    public static String replaceNumber(String uri, char toReplace) {
        StringBuilder builder = new StringBuilder();
        String[] uriParts = uri.split("/");
        System.out.println(uriParts.length);
        for (String uriPart : uriParts) {
            if (!"".equals(uriPart)) {
                builder.append("/").append(isNumeric(uriPart) ? toReplace : uriPart);
            }
        }
        return builder.toString();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 解析字符串返回map键值对(例：a=1&b=2 => a=1,b=2)
     *
     * @param query
     * @return
     */
    public static Map<String, String> parseQuery(String query) {
        return parseQuery(query, '&', '=', null);
    }

    /**
     * 解析字符串返回map键值对(例：a=1&b=2 => a=1,b=2)
     *
     * @param query   源参数字符串
     * @param split1  键值对之间的分隔符（例：&）
     * @param split2  key与value之间的分隔符（例：=）
     * @param dupLink 重复参数名的参数值之间的连接符，连接后的字符串作为该参数的参数值，可为null
     *                null：不允许重复参数名出现，则靠后的参数值会覆盖掉靠前的参数值。
     * @return map
     */
    public static Map<String, String> parseQuery(String query, char split1, char split2, String dupLink) {
        if (!StringUtil.isEmpty(query) && query.indexOf(split2) > 0) {
            Map<String, String> result = new HashMap<>();
            String name = null;
            String value = null;
            int len = query.length();
            for (int i = 0; i < len; i++) {
                char c = query.charAt(i);
                if (c == split2) {
                    value = "";
                } else if (c == split1) {
                    handleDupLink(dupLink, result, name, value);
                    name = null;
                    value = null;
                } else if (value != null) {
                    value += c;
                } else {
                    name = (name != null) ? (name + c) : "" + c;
                }
            }
            handleDupLink(dupLink, result, name, value);
            return result;
        }
        return null;
    }

    private static void handleDupLink(String dupLink, Map<String, String> result, String name, String value) {
        String tempValue;
        if (!StringUtil.isEmpty(name) && value != null) {
            if (dupLink != null) {
                tempValue = result.get(name);
                if (tempValue != null) {
                    value += dupLink + tempValue;
                }
            }
            result.put(name, value);
        }
    }

    /**
     * 解析http请求URI
     *
     * @param queryUri http请求的uri
     */
    public static Map<String, String> httpParseQuery(String queryUri) {
        Map<String, String> result = new HashMap<>();
        if (!StringUtil.isEmpty(queryUri)) {
            result = parseQuery(queryUri, '&', '=', ",");
        }
        return result;
    }
}
