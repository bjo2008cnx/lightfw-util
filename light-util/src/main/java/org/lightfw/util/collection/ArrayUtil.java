package org.lightfw.util.collection;

import org.lightfw.util.math.MathUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * ArrayUtil
 *
 * @author Wangxm
 * @date 2016/5/6
 */
public class ArrayUtil {
    /**
     * 功能：判断数组是不是空。（null或者length==0）
     *
     * @param array 数组
     * @return boolean 空返回true，否则返回false。
     */
    public static <T> boolean isEmpty(T[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * 功能：判断数组是不是空。（null或者length==0）
     *
     * @param array 数组
     * @return boolean 不为空返回true，否则返回false。
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * 将一个字符串数组的内容全部添加到另外一个数组中，并返回一个新数组。
     *
     * @param array1 第一个数组
     * @param array2 第二个数组
     * @return T[] 拼接后的新数组
     */
    public static <T> T[] concatenateArrays(T[] array1, T[] array2) {
        if (isEmpty(array1)) {
            return array2;
        }
        if (isEmpty(array2)) {
            return array1;
        }
        T[] resArray = (T[]) java.lang.reflect.Array.newInstance(array1[0].getClass(), array1.length + array2.length);
        System.arraycopy(array1, 0, resArray, 0, array1.length);
        System.arraycopy(array2, 0, resArray, array1.length, array2.length);
        return resArray;
    }

    /**
     * 将一个object添加到一个数组中，并返回一个新数组。
     *
     * @param array 被添加到的数组
     * @param obj   被添加的object
     * @return T[] 返回的新数组
     */
    public static <T> T[] addObjectToArray(T[] array, T obj) {
        //结果数组
        T[] resArray = null;
        if (ArrayUtil.isEmpty(array)) {
            resArray = (T[]) java.lang.reflect.Array.newInstance(obj.getClass(), 1);
            resArray[0] = obj;
            return resArray;
        }
        //原数组不为空时。
        resArray = (T[]) java.lang.reflect.Array.newInstance(array[0].getClass(), array.length + 1);
        System.arraycopy(array, 0, resArray, 0, array.length);
        resArray[array.length] = obj;
        return resArray;
    }

    /**
     * 功能：将数组进行反转，倒置。
     *
     * @param objs 源数组
     * @return T[] 反转后的数组
     */
    public static <T> T[] reverse(T[] objs) {
        if (isEmpty(objs)) {
            return null;
        }
        T[] res = (T[]) java.lang.reflect.Array.newInstance(objs[0].getClass(), objs.length);
        //新序号
        int k = 0;
        for (int i = objs.length - 1; i >= 0; i--) {
            res[k++] = objs[i];
        }
        return res;
    }

    /**
     * 功能：将数组转为list。
     *
     * @param objs 源数组
     * @return List
     */
    public static <T> List<T> toList(T[] objs) {
        if (isEmpty(objs)) {
            return null;
        }
        List<T> list = new LinkedList<T>();
        for (T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    /**
     * 功能：数组中是否存在这个元素。
     *
     * @param objArr  数组
     * @param compare 元素
     * @return 存在返回true，否则返回false。
     */
    public static <T> boolean contain(T[] objArr, T compare) {
        if (isEmpty(objArr)) {
            return false;
        }
        for (T obj : objArr) {
            if (obj.equals(compare)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 功能：从数组中随机取出一个元素。
     *
     * @param objs 源数组
     * @return T 数组的一个元素
     */
    public static <T> T randomOne(T[] objs) {
        if (isEmpty(objs)) {
            return null;
        }
        return objs[MathUtil.randomNumber(0, objs.length)];
    }


    /**
     * 比较两个数组都否相同
     *
     * @param left
     * @param right
     * @return
     */
    public static boolean isEqaul(Object[] left, Object[] right) {
        return Arrays.equals(left, right);
    }

    /**
     * 比较两个数组都否相同
     *
     * @param left
     * @param right
     * @return
     */
    public static boolean isEqaulIgnoreEmptyAndOrder(Object[] left, Object[] right) {
        return Arrays.equals(left, right); //TODO
    }

    /**
     * 是否等于其中的任一项
     *
     * @param toCompare
     * @param toCompares
     * @return
     */
    public static boolean equalsOneOf(int toCompare, int... toCompares) {
        if (toCompares == null) {
            return false;
        }

        for (int beCompared : toCompares) {
            if (toCompare == beCompared) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否等于其中的任一项
     *
     * @param toCompare
     * @param toCompares
     * @return
     */
    public static boolean equalsOneOf(String toCompare, String... toCompares) {
        if (toCompare == null || toCompares == null) {
            return false;
        }
        for (String beCompared : toCompares) {
            if (toCompare.equals(beCompared)) {
                return true;
            }
        }
        return false;
    }
}
