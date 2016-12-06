package org.lightfw.util.math;

import org.lightfw.util.lang.StringUtil;

import java.math.BigDecimal;

/**
 * 数学运算辅助类。
 */
public class MathUtil {

    /**
     * 判断字符串是否数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return StringUtil.isNumeric(str);
    }

    /**
     * 将字符串转换为BigDecimal，一般用于数字运算时。
     *
     * @param str 字符串
     * @return BigDecimal, str为empty时返回null。
     */
    public static BigDecimal toBigDecimal(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        return new BigDecimal(str);
    }

    /**
     * 将字符串抓换为double，如果失败返回默认值。
     *
     * @param str          字符串
     * @param defaultValue 失败时返回的默认值
     * @return double
     */
    public static double toDouble(String str, double defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 将字符串抓换为float，如果失败返回默认值。
     *
     * @param str          字符串
     * @param defaultValue 失败时返回的默认值
     * @return float
     */
    public static float toFloat(String str, float defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 将字符串抓换为long，如果失败返回默认值。
     *
     * @param str          字符串
     * @param defaultValue 失败时返回的默认值
     * @return long
     */
    public static long toLong(String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 将字符串抓换为int，如果失败返回默认值。
     *
     * @param str          字符串
     * @param defaultValue 失败时返回的默认值
     * @return int
     */
    public static int toInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * <p>
     * 得到两个 <code>double</code>值中最大的一个.
     * </p>
     *
     * @param a 值 1
     * @param b 值 2
     * @return 最大的值
     */
    public static float getMax(float a, float b) {
        if (Float.isNaN(a)) {
            return b;
        } else if (Float.isNaN(b)) {
            return a;
        } else {
            return Math.max(a, b);
        }
    }

    /**
     * <p>
     * 得到数组中最大的一个.
     * </p>
     *
     * @param array 数组不能为null，也不能为空。
     * @return 得到数组中最大的一个.
     * @throws IllegalArgumentException 如果 <code>数组</code> 是 <code>null</code>
     * @throws IllegalArgumentException 如果 <code>数组</code>是空
     */
    public static float getMax(float[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        // Finds and returns max
        float max = array[0];
        for (int i = 1; i < array.length; i++) {
            max = getMax(array[i], max);
        }
        return max;
    }

    /**
     * <p>
     * 得到数组中最大的一个.
     * </p>
     *
     * @param array 数组不能为null，也不能为空。
     * @return 得到数组中最大的一个.
     * @throws IllegalArgumentException 如果 <code>数组</code> 是 <code>null</code>
     * @throws IllegalArgumentException 如果 <code>数组</code>是空
     */
    public static double getMax(double[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        double max = array[0];
        for (int j = 1; j < array.length; j++) {
            max = getMax(array[j], max);
        }
        return max;
    }

    /**
     * <p>
     * 得到两个 <code>double</code>值中最大的一个.
     * </p>
     *
     * @param a 值 1
     * @param b 值 2
     * @return 最大的值
     */
    public static double getMax(double a, double b) {
        if (Double.isNaN(a)) {
            return b;
        } else if (Double.isNaN(b)) {
            return a;
        } else {
            return Math.max(a, b);
        }
    }

    /**
     * <p>
     * 得到两个float中最小的一个。
     * </p>
     *
     * @param a 值 1
     * @param b 值 2
     * @return double值最小的
     */
    public static float getMin(float a, float b) {
        if (Float.isNaN(a)) {
            return b;
        } else if (Float.isNaN(b)) {
            return a;
        } else {
            return Math.min(a, b);
        }
    }

    /**
     * <p>
     * 返回数组中最小的数值。
     * </p>
     *
     * @param array 数组不能为null，也不能为空。
     * @return 数组里面最小的float
     * @throws IllegalArgumentException 如果<code>数组</code>是<code>null</code>
     * @throws IllegalArgumentException 如果<code>数组</code>是空
     */
    public static float getMin(float[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("数组不能为null。");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("数组不能为空。");
        }

        // Finds and returns min
        float min = array[0];
        for (int i = 1; i < array.length; i++) {
            min = getMin(array[i], min);
        }

        return min;
    }

    /**
     * <p>
     * 返回数组中最小的double。
     * </p>
     *
     * @param array 数组不能为null，也不能为空。
     * @return 数组里面最小的double
     * @throws IllegalArgumentException 如果<code>数组</code>是<code>null</code>
     * @throws IllegalArgumentException 如果<code>数组</code>是空
     */
    public static double getMin(double[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("数组不能为null。");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("数组不能为空。");
        }
        // Finds and returns min
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            min = getMin(array[i], min);
        }
        return min;
    }

    /**
     * <p>
     * 得到两个double中最小的一个。
     * </p>
     *
     * @param a 值 1
     * @param b 值 2
     * @return double值最小的
     */
    public static double getMin(double a, double b) {
        if (Double.isNaN(a)) {
            return b;
        } else if (Double.isNaN(b)) {
            return a;
        } else {
            return Math.min(a, b);
        }
    }

    /**
     * 返回两个double的商 first除以second。
     *
     * @param first  第一个double
     * @param second 第二个double
     * @return double
     */
    public static double divide(double first, double second) {
        BigDecimal b1 = new BigDecimal(first);
        BigDecimal b2 = new BigDecimal(second);
        return b1.divide(b2).doubleValue();
    }

    /**
     * 返回两个double的乘积 first*second。
     *
     * @param first  第一个double
     * @param second 第二个double
     * @return double
     */
    public static double multiply(double first, double second) {
        BigDecimal b1 = new BigDecimal(first);
        BigDecimal b2 = new BigDecimal(second);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 返回两个double的差值 first-second。
     *
     * @param first  第一个double
     * @param second 第二个double
     * @return double
     */
    public static double subtract(double first, double second) {
        BigDecimal b1 = new BigDecimal(first);
        BigDecimal b2 = new BigDecimal(second);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 返回两个double的和值 first+second。
     *
     * @param first  第一个double
     * @param second 第二个double
     * @return double
     */
    public static double sum(double first, double second) {
        BigDecimal b1 = new BigDecimal(first);
        BigDecimal b2 = new BigDecimal(second);
        return b1.add(b2).doubleValue();
    }

    /**
     * 格式化double指定位数小数。例如将11.123格式化为11.1。
     *
     * @param value    原double数字。
     * @param decimals 小数位数。
     * @return 格式化后的double，注意为硬格式化不存在四舍五入。
     */
    public static String format(double value, int decimals) {
        String doubleStr = "" + value;
        int index = doubleStr.indexOf(".") != -1 ? doubleStr.indexOf(".") : doubleStr.indexOf(",");

        if (index == -1) {  // Decimal point can not be found...
            return doubleStr;
        }
        // Truncate all decimals
        if (decimals == 0) {
            return doubleStr.substring(0, index);
        }
        int len = index + decimals + 1;
        if (len >= doubleStr.length()) {
            len = doubleStr.length();
        }
        double d = Double.parseDouble(doubleStr.substring(0, len));
        return String.valueOf(d);
    }

    /**
     * TODO 效率待优化
     * 生成一个指定位数的随机数，并将其转换为字符串
     *
     * @param length 随机数的位数。
     * @return String
     */
    public static int random(int length) {
        // 记录生成的每一位随机数
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            // 每次生成一位,随机生成一个0-10之间的随机数,不含10。
            Double ranDouble = Math.floor(Math.random() * 10);
            if (i == 0) {
                int randomInt = ranDouble.intValue();
                if (randomInt != 0) {
                    sb.append(randomInt);
                }
            }
        }
        return Integer.valueOf(sb.toString());
    }

    /**
     * 生成一个在最大数和最小数之间的随机数。会出现最小数，但不会出现最大数。
     *
     * @param minNum 最小数
     * @param maxNum 最大数
     * @return int
     */
    public static int randomNumber(int minNum, int maxNum) {
        if (maxNum <= minNum) {
            throw new RuntimeException("maxNum必须大于minNum!");
        }
        // 计算出来差值
        int subtract = maxNum - minNum;
        Double ranDouble = Math.floor(Math.random() * subtract);
        return ranDouble.intValue() + minNum;
    }
}