package org.lightfw.util.sercurity.text;

/**
 * HtmlUtil
 *
 * @author Wangxm
 * @date 2016/5/5
 */
public class HtmlFilterUtil {
    /**
     * 功能: 过滤Html页面中的敏感字符,用于在script脚本中显示
     *
     * @param obj
     * @return
     */
    public static String replaceHtmlUnsafe(Object obj) {
        return replaceHtmlUnsafe(obj == null ? "" : obj.toString());
    }

    /**
     * 功能: 过滤Html页面中的敏感字符,用于在script脚本中显示
     *
     * @param value
     * @return
     */
    public static String replaceHtmlUnsafe(String value) {
        return replaceStringByRule(value, new String[][]{{"'", "\\'"}, {"\"", "\\\""}, {"\\", "\\\\"}, {"\r", "\\r"}, {"\n", "\\n"},
                {"\t", "\\t"}, {"\f", "\\f"}, {"\b", "\\b"}});
    }

    /**
     * 过滤Html页面中的敏感字符
     *
     * @param obj
     * @return
     */
    public static String replaceStringToHtml(Object obj) {
        return replaceStringToHtml(obj == null ? "" : obj.toString());
    }

    /**
     * 过滤Html页面中的敏感字符
     *
     * @param value
     * @return
     */
    public static String replaceStringToHtml(String value) {
        return replaceStringByRule(value, new String[][]{{"<", "&lt;"}, {">", "&gt;"}, {"&", "&amp;"}, {"\"", "&quot;"}, {"'", "&#39;"},
                {"\n", "<BR>"}, {"\r", "<BR>"}});
    }

    /**
     * 把<替换成&lt;，应对编辑html代码
     *
     * @param value
     * @return
     */
    public static String replaceStringToEditHtml(String value) {
        if (value == null) {
            value = "";
        }
        return replaceStringByRule(value, new String[][]{{"<", "&lt;"}});
    }

    /**
     * 过滤Html页面中的敏感字符，接受过滤的字符列表和转化后的值
     *
     * @param value
     * @return
     */
    public static String replaceStringByRule(String value, String[][] aString) {
        if (value == null) {
            return ("");
        }
        char content[] = new char[value.length()];
        value.getChars(0, value.length(), content, 0);
        StringBuffer result = new StringBuffer(content.length + 50);

        for (int i = 0; i < content.length; i++) {
            boolean isTransct = false;
            for (int j = 0; j < aString.length; j++) {
                if (String.valueOf(content[i]).equals(aString[j][0])) {
                    result.append(aString[j][1]);
                    isTransct = true;
                    break;
                }
            }
            if (!isTransct) {
                result.append(content[i]);
            }
        }
        return result.toString();
    }
}
