package org.lightfw.utilt;

import org.lightfw.util.text.CharsetUtil;

/**
 * StringTestUtil
 *
 * @author Wangxm
 * @date 2016/5/6
 */
public class StringTestUtil {
    /**
     * 功能: 测试各种编码之间的转化，找出乱码原因
     *
     * @param original
     * @return
     */
    public static String testAllEncode(String original) {
        return testAllEncode(original, new String[]{"GBK", "iso8859-1", "gb2312", "UTF-8"});
    }

    /**
     * 功能: 测试各种编码之间的转化，找出乱码原因
     *
     * @param original
     * @param encode
     * @return
     */
    public static String testAllEncode(String original, String[] encode) {
        StringBuilder rtValue = new StringBuilder();
        rtValue.append("original = ");
        rtValue.append(original);
        rtValue.append("\n");
        if (encode == null || encode.length < 2) {
            return rtValue.toString();
        }
        for (int i = 0; i < encode.length; i++) {
            rtValue.append("\n");
            rtValue.append(encode[i]);
            rtValue.append("-->\n");
            for (int j = 0; j < encode.length; j++) {
                rtValue.append(encode[i]);
                rtValue.append("-->");
                rtValue.append(encode[j]);
                rtValue.append(" = ");
                rtValue.append(CharsetUtil.encoding2Encoding(original, encode[i], encode[j]));
                rtValue.append("\n");
            }
        }
        return rtValue.toString();
    }
}
