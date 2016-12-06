package org.lightfw.util.text;

import org.lightfw.util.lang.ExceptionUtil;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Description:
 * 编码相关的封装类
 */
public class CharsetUtil {
    /**
     * 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块
     */
    public static final String US_ASCII = "US-ASCII";

    /**
     * ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1
     */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * 8 位 UCS 转换格式
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序
     */
    public static final String UTF_16BE = "UTF-16BE";

    /**
     * 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序
     */
    public static final String UTF_16LE = "UTF-16LE";

    /**
     * 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识
     */
    public static final String UTF_16 = "UTF-16";

    /**
     * 中文超大字符集
     */
    public static final String GBK = "GBK";

    /**
     * 将字符编码转换成US-ASCII码
     */
    public static String toASCII(String str) throws UnsupportedEncodingException {
        return changeCharset(str, US_ASCII);
    }

    /**
     * 将字符编码转换成ISO-8859-1码
     */
    public static String toISO_8859_1(String str) throws UnsupportedEncodingException {
        return changeCharset(str, ISO_8859_1);
    }

    /**
     * 将字符编码转换成UTF-8码
     */
    public static String toUTF_8(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_8);
    }

    /**
     * 将字符编码转换成UTF-16BE码
     */
    public static String toUTF_16BE(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16BE);
    }

    /**
     * 将字符编码转换成UTF-16LE码
     */
    public static String toUTF_16LE(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16LE);
    }

    /**
     * 将字符编码转换成UTF-16码
     */
    public static String toUTF_16(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16);
    }

    /**
     * 将字符编码转换成GBK码
     */
    public static String toGBK(String str) throws UnsupportedEncodingException {
        return changeCharset(str, GBK);
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param newCharset 目标编码
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * 功能: 把指定字符串original 从encode1 转化到encode2
     *
     * @param original
     * @param currentEncoding
     * @param newEncoding
     * @return
     */
    public static String encoding2Encoding(String original, String currentEncoding, String newEncoding) {
        if (original != null) {
            try {
                return new String(original.getBytes(currentEncoding), newEncoding);
            } catch (UnsupportedEncodingException e) {
                throw ExceptionUtil.transform(e);
            }
        }
        return null;
    }

    public static String getDefaultCharSet() throws UnsupportedEncodingException {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream(), CharsetUtil.UTF_8);
        String enc = writer.getEncoding();
        return enc;
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param oldCharset 原编码
     * @param newCharset 目标编码
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用旧的字符编码解码字符串。解码可能会出现异常。
            byte[] bs = str.getBytes(oldCharset);
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

}