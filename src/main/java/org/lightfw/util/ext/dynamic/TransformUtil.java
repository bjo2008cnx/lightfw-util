package org.lightfw.util.ext.dynamic;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import org.lightfw.util.lang.DateUtil;
import org.lightfw.util.math.NumberUtil;

public class TransformUtil {

    /**
     * 根据处理指定类型的值,TODO 考虑泛型
     * 示例：transform(BigDecimal.class,"123,456,000")
     *
     * @param clazz
     * @param value
     * @return
     */
    public static Object transform(Class clazz, Object value) {
        if (value == null) {
            return null;
        }
        Object result = value;
        if (clazz.equals(value.getClass())) {
            return result;
        }
        if (Timestamp.class.equals(clazz)) { // 时间戳创建对象
            result = DateUtil.getTimestamp(value.toString());
        } else if (Date.class.equals(clazz)) { // SQL日期创建对象
            result = DateUtil.getSqlDate(value.toString());
        } else if (BigDecimal.class.equals(clazz)) { // BigDecimal
            /* 将千分位的","全部替换为"" */
            result = NumberUtil.formatFromThousandth(value.toString());
        } else if (String.class.equals(clazz)) { // 字符串
            result = value.toString();
        } else if (long.class.equals(clazz) && value.toString()
                .matches("^\\d{4}-\\d{2}-\\d{2}( \\d{2}:\\d{2}:\\d{2})?(\\.\\d+)?$")) {
            result = DateUtil.getTimestamp(value.toString()).getTime();
        } else if (short.class.equals(clazz) || int.class.equals(clazz) || long.class.equals(clazz)
                || float.class.equals(clazz) || double.class.equals(clazz)) {
            // 如果空字符串-->基本类型,忽略
            if (value.toString().length() == 0) {
                result = null;
            }
        }
        return result;
    }

}
