package org.lightfw.util.lang;

import com.google.common.base.Objects;

/**
 * 对象工具
 *
 * @author jason
 */
public class ObjectUtil {
    /**
     * 比较对象
     *
     * @param valueA
     * @param valueB
     * @param isNullBigger 是否null值比其他值更大
     * @return 比较的结果
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static int compare(Object valueA, Object valueB, boolean isNullBigger) {
        int result = 0;
        if (valueA == null) {
            result = isNullBigger ? 1 : -1;
        } else {
            if (valueB == null) {
                result = isNullBigger ? -1 : 1;
            } else {
                if (valueA instanceof Comparable) {
                    result = ((Comparable) valueA).compareTo(valueB);
                } else {
                    result = 0;
                }
            }
        }
        return result;
    }

    /**
     * 比较对象是否相等
     * <p/>
     * Objects.equal("a", "a"); // returns true
     * Objects.equal(null, "a"); // returns false
     * Objects.equal("a", null); // returns false
     * Objects.equal(null, null); // returns true
     *
     * @param leftObject
     * @param rightobject
     * @return
     */
    public static boolean equal(Object leftObject, Object rightobject) {
        return Objects.equal(leftObject, rightobject);
    }

    /**
     * 计算哈希值
     * 例：如果一个对象有3表字段a,b,c ，那么可以使用hash(a,b,c)来计算该对象的Hash值
     *
     * @param objects
     * @return
     */
    public static int hash(Object... objects) {
        return java.util.Objects.hash(objects);
    }
}
