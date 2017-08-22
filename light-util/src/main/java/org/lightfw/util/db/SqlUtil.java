package org.lightfw.util.db;

public class SqlUtil {
    /**
     * NAME='张三'
     */
    public final static int TYPE_CHAR_EQUAL = 0;
    /**
     * NAME like '%张三%'
     */
    public final static int TYPE_CHAR_LIKE = 1;
    /**
     * NAME like '张三%'
     */
    public final static int TYPE_CHAR_LIKE_START = 6;
    /**
     * AGE = 18
     */
    public final static int TYPE_NUMBER_EQUAL = 7;
    /**
     * AGE >= 18
     */
    public final static int TYPE_NUMBER_GREATER_EQUAL = 2;
    /**
     * AGE <= 18
     */
    public final static int TYPE_NUMBER_LESS_EQUAL = 3;
    /**
     * BIRTH >= 1990-01-01
     */
    public final static int TYPE_DATE_GREATER_EQUAL = 4;
    /**
     * BIRTH <= 1990-01-01
     */
    public final static int TYPE_DATE_LESS_EQUAL = 5;
    /**
     * 自定义类型 {int type(类型) [, String prefixValue(指定的查询值前缀), String affixValue(指定的查询值后缀)]}
     */
    public final static int TYPE_CUSTOM = 99;

    /**
     * escape转义sql输入值，防止sql注入
     *
     * @param inputValue 待转移的输入值
     * @param type       类型
     * @return 转义后的sql值
     */
    public static String escapeSqlValue(Object inputValue, int type) {
        if (inputValue == null) {
            return null;
        }
        String value = inputValue.toString();
        switch (type) {
            //字符串和日期：转义'，保证输入值在''中间，就是安全的
            case TYPE_CHAR_EQUAL:
            case TYPE_CHAR_LIKE:
            case TYPE_CHAR_LIKE_START:
            case TYPE_DATE_GREATER_EQUAL:
            case TYPE_DATE_LESS_EQUAL:
                value = value.replaceAll("(['])", "'$1");
                break;
            //数字：去掉空白类符号，就是安全的
            case TYPE_NUMBER_EQUAL:
            case TYPE_NUMBER_GREATER_EQUAL:
            case TYPE_NUMBER_LESS_EQUAL:
                //空白符忽略
                value = value.replaceAll("(\\s+)", "");
                //疑似函数的一律忽略
                value = value.replaceAll("([\\w_]+\\(.*\\))", "");
                break;
            default:
                break;
        }
        return value;
    }


    /**
     * 获得SQL中日期的查询字符串
     *
     * @param value
     * @return
     */
    public static String getSqlDateStr(Object value, String dbName) {
        if (value == null || value.toString().trim().length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if ("oracle".equalsIgnoreCase(dbName)) {
            String valueStr = value.toString().trim();
            sb.append("to_date('");
            switch (valueStr.length()) {
                case 4: //2010
                    sb.append(valueStr);
                    sb.append("', 'YYYY')");
                    break;
                case 7: //2010-01
                    sb.append(valueStr);
                    sb.append("', 'YYYY-MM')");
                    break;
                case 10: //2010-01-01

                    sb.append(valueStr);
                    sb.append("', 'YYYY-MM-DD')");
                    break;
                case 19: //2010-01-01 01:01:01

                    sb.append(valueStr);
                    sb.append("', 'YYYY-MM-DD HH24:MI:SS')");
                    break;
                case 23: //2010-01-01 01:01:01 001

                    sb.append(valueStr.substring(0, 19));
                    sb.append("', 'YYYY-MM-DD HH24:MI:SS')");
                    break;
                default:
                    return "";
            }
        } else {
            sb.append("'");
            sb.append(value.toString().trim());
            sb.append("'");
        }
        return sb.toString();
    }

    /**
     * 构建完整SQL查询片段，不带where
     *
     * @param aQueryStr 类似new String[]{"NAME like '%张三%'"}, {"AGE=18"} }
     * @return
     */
    public static String appendQueryStr(String[] aQueryStr) {
        StringBuilder sb = new StringBuilder();
        for (String queryStr : aQueryStr) {
            if (queryStr == null || queryStr.trim().length() == 0) {
                continue;
            }
            if (sb.length() == 0) {
                // 第一个条件不加and


            } else {
                sb.append(" and");
            }
            sb.append(" ");
            sb.append(queryStr);
        }
        return sb.toString();
    }


    public static String getSqlPage4Mysql(String strsql, int startIndex, int size) {
        return strsql + " limit " + (startIndex - 1) + "," + size;
    }

    public static String getSqlPage4Oracle(String strsql, int startIndex, int size) {
        return "select * from (select rownum as rmrn, a.* from(" + strsql + ") a where rownum<=" + (startIndex + size - 1) + ")where rmrn >=" + startIndex;
    }

    /**
     * 153ms-176ms
     * <p/>
     * select * from(select row_number() over(order by uid asc) as rownumber, * from moa_user ) as tb where rownumber between 100000 and 100200
     * <p/>
     * <p/>
     * <p/>
     * 156ms-210ms
     * <p/>
     * select top 200 * from(select row_number() over(order by uid asc) as rownumber,* from moa_user ) as tb where rownumber>100000
     * <p/>
     * <p/>
     * <p/>
     * 135ms
     * <p/>
     * select top 200 * FROM moa_user WHERE (uid > (SELECT MAX(uid) FROM (SELECT TOP 100000 uid FROM moa_user ORDER BY uid) AS temp_moa_user)) ORDER BY uid
     * <p/>
     * <p/>
     * <p/>
     * 270ms-290ms
     * <p/>
     * select top 200 * from moa_user a  where uid  not in(select top 100000  uid  from moa_user  b order by uid)
     * <p/>
     * <p/>
     * <p/>
     * 950ms
     * <p/>
     * select * from ( select top 200 * from ( select TOP 100000 * from moa_user order by uid) as amoaUser ORDER BY uid DESC ) as bmoaUser ORDER BY uid ASC
     *
     * @param strsql
     * @param startIndex
     * @param size
     * @return
     */
    static String getSqlPage4Sqlserver(String strsql, int startIndex, int size) {
        return null;
    }

    /**
     * 常用函数名枚举
     */
    public enum Function {
        TO_NUMBER,
        TO_CHAR,
        SYSDATE,
        LENGTH,
        SUBSTR,
        NVL,
        CONCAT,
        WM_CONCAT
    }

    static String getFunctionOracle(Function func, Object... args) {
        StringBuilder result = new StringBuilder();
        if (Function.TO_NUMBER.name().equals(func.name())) {
            result.append("to_number(");
            result.append(args[0]);
            result.append(")");
            return result.toString();
        } else if (Function.TO_CHAR.name().equals(func.name())) {
            result.append("to_char(");
            result.append(args[0]);
            result.append(")");
            return result.toString();
        } else if (Function.SYSDATE.name().equals(func.name())) {
            return "sysdate";
        } else if (Function.LENGTH.name().equals(func.name())) {
            return "length";
        } else if (Function.SUBSTR.name().equals(func.name())) {
            return "substr";
        } else if (Function.NVL.name().equals(func.name())) {
            return "nvl";
        } else if (Function.CONCAT.name().equals(func.name())) {
            for (int i = 0; args != null && i < args.length; i++) {
                if (i > 0) {
                    result.append("||");
                }
                result.append(args[i]);
            }
            return result.toString();
        } else if (Function.WM_CONCAT.name().equals(func.name())) {
            return "wm_concat";
        }
        return null;
    }

    static String getFunctionMysql(Function func, Object... args) {
        StringBuilder result = new StringBuilder();
        if (Function.TO_NUMBER.name().equals(func.name())) {
            result.append("cast(");
            result.append(args[0]);
            result.append(" as signed integer)");
            return result.toString();
        } else if (Function.TO_CHAR.name().equals(func.name())) {
            result.append("cast(");
            result.append(args[0]);
            result.append(" as char)");
            return result.toString();
        } else if (Function.SYSDATE.name().equals(func.name())) {
            return "sysdate()";
        } else if (Function.LENGTH.name().equals(func.name())) {
            return "length";
        } else if (Function.SUBSTR.name().equals(func.name())) {
            return "substring";
        } else if (Function.NVL.name().equals(func.name())) {
            return "ifnull";
        } else if (Function.CONCAT.name().equals(func.name())) {
            result.append("concat(");
            for (int i = 0; args != null && i < args.length; i++) {
                if (i > 0) {
                    result.append(",");
                }
                result.append(args[i]);
            }
            result.append(")");
            return result.toString();
        } else if (Function.WM_CONCAT.name().equals(func.name())) {
            return "group_concat";
        }
        return null;
    }

    static String getFunctionSqlserver(Function func, Object... args) {
        StringBuilder result = new StringBuilder();
        if (Function.TO_NUMBER.name().equals(func.name())) {
            result.append("cast(");
            result.append(args[0]);
            result.append(" as decimal(30,2))");
            return result.toString();
        } else if (Function.TO_CHAR.name().equals(func.name())) {
            result.append("cast(");
            result.append(args[0]);
            result.append(" as char)");
            return result.toString();
        } else if (Function.SYSDATE.name().equals(func.name())) {
            return "getdate()";
        } else if (Function.LENGTH.name().equals(func.name())) {
            return "len";
        } else if (Function.SUBSTR.name().equals(func.name())) {
            return "substring";
        } else if (Function.NVL.name().equals(func.name())) {
            return "isnull";
        } else if (Function.CONCAT.name().equals(func.name())) {
            for (int i = 0; args != null && i < args.length; i++) {
                if (i > 0) {
                    result.append("+");
                }
                result.append(args[i]);
            }
            return result.toString();
        } else if (Function.WM_CONCAT.name().equals(func.name())) {
            return "group_concat";
        }
        return null;
    }
}
