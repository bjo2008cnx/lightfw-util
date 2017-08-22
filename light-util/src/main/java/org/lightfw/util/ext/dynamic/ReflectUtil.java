package org.lightfw.util.ext.dynamic;

import lombok.extern.log4j.Log4j2;
import org.lightfw.util.lang.ExceptionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * 反射工具类
 */
@Log4j2
public class ReflectUtil {
    private static Object operate(Object obj, String fieldName, Object fieldVal, String type) {
        Object ret = null;
        try {
            // 获得对象类型
            Class<? extends Object> classType = obj.getClass();
            // 获得对象的所有属性
            Field fields[] = classType.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.getName().equals(fieldName)) {

                    String firstLetter = fieldName.substring(0, 1).toUpperCase(); // 获得和属性对应的getXXX()方法的名字
                    if ("set".equals(type)) {
                        String setMethodName = "set" + firstLetter + fieldName.substring(1); // 获得和属性对应的getXXX()方法
                        Method setMethod = classType.getMethod(setMethodName, new Class[]{field.getType()}); // 调用原对象的getXXX()方法
                        ret = setMethod.invoke(obj, new Object[]{fieldVal});
                    }
                    if ("get".equals(type)) {
                        String getMethodName = "get" + firstLetter + fieldName.substring(1); // 获得和属性对应的setXXX()方法的名字
                        Method getMethod = classType.getMethod(getMethodName, new Class[]{});
                        ret = getMethod.invoke(obj, new Object[]{});
                    }
                    return ret;
                }
            }
        } catch (Exception e) {
            log.warn("reflect error:" + fieldName, e);
        }
        return ret;
    }

    public static Object getVal(Object obj, String fieldName) {
        return operate(obj, fieldName, null, "get");
    }

    public static void setVal(Object obj, String fieldName, Object fieldVal) {
        operate(obj, fieldName, fieldVal, "set");
    }

    private static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                // superClass.getMethod(methodName, parameterTypes);
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                // Method 不在当前类定义, 继续向上转型
            }
        }

        return null;
    }

    private static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
    }

    private static Field getDeclaredField(Object object, String filedName) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(filedName);
            } catch (NoSuchFieldException e) {
                // Field 不在当前类定义, 继续向上转型
            }
        }
        return null;
    }

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters)
            throws InvocationTargetException {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
        }
        method.setAccessible(true);
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            throw ExceptionUtil.transform(e);
        }
    }

    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Object copy(Object obj) {
        // 获得对象类型
        Class classType = obj.getClass();

        // 通过默认构造器创建新对象
        Object objectCopy = null;
        try {
            objectCopy = classType.getConstructor(new Class[]{}).newInstance(new Object[]{});

            // 获得对象的所有属性
            Field[] fields = classType.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String fieldName = field.getName();
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + fieldName.substring(1);
                String setMethodName = "set" + firstLetter + fieldName.substring(1);

                // 获得get方法
                Method getMethod = classType.getMethod(getMethodName, new Class[]{});
                Method setMethod = classType.getMethod(setMethodName, new Class[]{field.getType()});
                System.out.println("field.getType():" + field.getType());

                // 调用原对象的getXxx()方法
                Object value = getMethod.invoke(obj, new Object[]{});

                setMethod.invoke(objectCopy, new Object[]{value});
            }
        } catch (Exception e) {
            throw ExceptionUtil.transform(e);
        }
        return objectCopy;
    }

    /**
     * 调用私有方法
     *
     * @param obj
     * @param methodName
     */
    public static Object invokePrivate(Object obj, String methodName) {
        //测试没有参数的echoRequest()方法
        Method method = null;
        try {
            method = obj.getClass().getDeclaredMethod(methodName, null);
        } catch (NoSuchMethodException e) {
            throw ExceptionUtil.transform(e);
        }
        /*Method对象继承自java.lang.reflect.AccessibleObject，父类方法setAccessible可调,将此对象的 accessible 标志设置为指示的布尔值。
        值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
        要访问私有方法必须将accessible设置为true，否则抛java.lang.IllegalAccessException  */
        method.setAccessible(true);
        Object result = null;

        //调用
        try {
            result = method.invoke(obj, null);
        } catch (IllegalAccessException e) {
            throw ExceptionUtil.transform(e);
        } catch (InvocationTargetException e) {
            throw ExceptionUtil.transform(e);
        }
        return result;
    }

    /**
     * 调用私有方法, TODO 加参数
     *
     * @param obj
     * @param methodName
     */
    public static Object invokePrivate(Object obj, String methodName, Map<Class, Object> params) {
        //测试没有参数的echoRequest()方法
        Method method = null;
        try {
            method = obj.getClass().getDeclaredMethod(methodName, null);
        } catch (NoSuchMethodException e) {
            throw ExceptionUtil.transform(e);
        }
        /*Method对象继承自java.lang.reflect.AccessibleObject，父类方法setAccessible可调,将此对象的 accessible 标志设置为指示的布尔值。
        值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
        要访问私有方法必须将accessible设置为true，否则抛java.lang.IllegalAccessException  */
        method.setAccessible(true);
        Object result = null;

        //调用
        try {
            result = method.invoke(obj, null);
        } catch (IllegalAccessException e) {
            throw ExceptionUtil.transform(e);
        } catch (InvocationTargetException e) {
            throw ExceptionUtil.transform(e);
        }
        return result;
    }
}