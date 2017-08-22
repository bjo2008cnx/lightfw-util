package org.lightfw.util.sercurity.text;


import org.lightfw.util.db.SqlUtil;

public class SqlSecureUtil {

    /**
     * escape转义sql输入值，防止sql注入
     *
     * @param inputValue 待转移的输入值
     * @param type       类型
     * @return 转义后的sql值
     */
    public static String escapeSqlValue(Object inputValue, int type) {
        return SqlUtil.escapeSqlValue(inputValue, type);
    }
}