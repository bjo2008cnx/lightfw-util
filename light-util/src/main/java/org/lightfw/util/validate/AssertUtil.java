package org.lightfw.util.validate;

import org.lightfw.util.lang.StringUtil;

import java.util.Collection;
import java.util.Map;

/**
 * 校验参数工具类
 */
public class AssertUtil {

    /**
     * 表达式必须不为null,否则抛出异常
     */
    public static void assertNotNullOrEmpty(Object object, String messageKey) {
        StringBuilder builder = new StringBuilder();
        append(object, messageKey, builder);
        if (builder.length() > 0) {
            throw new IllegalArgumentException(builder.toString() + " cannot be null or empty");
        }
    }

    /**
     * 表达式必须不为null,否则抛出异常
     * 如果两个参数都为空，输出异常的message示例：name&age cannot be null or empty
     */
    public static void assertNotNullOrEmpty(Object object, String messageKey, Object object2, String messageKey2) {
        StringBuilder builder = new StringBuilder();
        append(object, messageKey, builder);
        append(object2, messageKey2, builder);
        if (builder.length() > 0) {
            throw new IllegalArgumentException(builder.toString() + " cannot be null or empty");
        }
    }

    /**
     * 表达式必须不为null,否则抛出异常
     */
    public static void assertNotNullOrEmpty(Object object, String messageKey, Object object2, String messageKey2, Object object3, String messageKey3) {
        StringBuilder builder = new StringBuilder();
        append(object, messageKey, builder);
        append(object2, messageKey2, builder);
        append(object3, messageKey3, builder);
        if (builder.length() > 0) {
            throw new IllegalArgumentException(builder.toString() + " cannot be null or empty");
        }
    }

    /**
     * 组装消息
     *
     * @param object
     * @param messageKey
     * @param builder
     */
    private static void append(Object object, String messageKey, StringBuilder builder) {
        //是否验证不通过
        boolean isNotValid = false;
        if (object == null) {
            isNotValid = true;
        } else if (object instanceof String) {
            isNotValid = StringUtil.isNullOrEmpty((String) object);
        } else if (object instanceof Map) {
            isNotValid = (((Map) object).size() == 0);
        } else if (object instanceof Collection) {
            isNotValid = (((Collection) object).size() == 0);
        }

        if (isNotValid) {
            builder.append(builder.length() > 0 ? "&" : "");
            builder.append(messageKey);
        }
    }

    /**
     * 表达式必须为true,否则抛出异常
     *
     * @param expression
     * @param message
     */
    public static void assertTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertAssignable(Class<?> superType, Class<?> subType) {
        assertAssignable(superType, subType, "");
    }

    public static void assertAssignable(Class<?> superType, Class<?> subType, String message) {
        assertNotNullOrEmpty(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
        }
    }
}