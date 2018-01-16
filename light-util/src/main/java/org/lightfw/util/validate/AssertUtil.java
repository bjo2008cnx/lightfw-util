package org.lightfw.util.validate;

import org.lightfw.util.lang.StringUtil;

/**
 * 校验参数工具类
 */
public class AssertUtil {

    public static void assertTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertTrue(boolean expression) {
        assertTrue(expression, "[Assertion failed] - this expression must be true");
    }

    public static void assertNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertNull(Object object) {
        assertNull(object, "[Assertion failed] - the object argument must be null");
    }

    public static void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object) {
        assertNotNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static void hasLength(String text, String message) {
        if (!StringUtil.isEmpty(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertAssignable(Class<?> superType, Class<?> subType) {
        assertAssignable(superType, subType, "");
    }

    public static void assertAssignable(Class<?> superType, Class<?> subType, String message) {
        assertNotNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
        }
    }

}
