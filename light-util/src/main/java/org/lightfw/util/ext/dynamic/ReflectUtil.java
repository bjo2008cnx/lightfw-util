package org.lightfw.util.ext.dynamic;

import lombok.extern.log4j.Log4j2;
import org.lightfw.util.lang.ExceptionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 反射工具类
 */
@Log4j2
public class ReflectUtil {
    /**
     * 调用方法
     *
     * @param object
     * @param methodName
     * @param parameterTypes
     * @param parameters
     * @return
     * @throws InvocationTargetException
     */
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        try {
            Method method = getDeclaredMethod(object, methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(object, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 调用方法
     *
     * @param object
     * @param methodName
     * @param parameters
     * @return
     * @throws InvocationTargetException
     */
    public static Object invokeMethod(Object object, String methodName, Object[] parameters) {
        Class<Object>[] parameterTypes = new Class[parameters.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameterTypes[i] = Object.class;
        }
        try {
            Method method = getDeclaredMethod(object, methodName);
            method.setAccessible(true);
            return method.invoke(object, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw ExceptionUtil.transform(e);
        }
    }


    public static void callSet(Object object, String fieldName, Object value) {
        try {
            Field field = getDeclaredField(object, fieldName);
            makeAccessible(field);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 调用get 方法
     *
     * @param object
     * @param fieldName
     * @return
     */
    public static Object invokeGet(Object object, String fieldName) {
        try {
            Field field = getDeclaredField(object, fieldName);
            makeAccessible(field);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 克隆对象
     *
     * @param obj 待拷贝的对象
     * @return
     */
    public static Object copy(Object obj) {
        Class classType = obj.getClass();  // 获得对象类型
        Object objectCopy;  // 通过默认构造器创建新对象
        try {
            objectCopy = classType.getConstructor(new Class[]{}).newInstance(new Object[]{});
            Field[] fields = classType.getDeclaredFields();  // 获得对象的所有属性
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                Object value = invokeGet(obj, field.getName());  // 调用原对象的getXxx()方法
                callSet(objectCopy, field.getName(), value); // 调用新对象的setXxx()方法
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
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName, null);
            method.setAccessible(true);
            return method.invoke(obj, null);  //调用
        } catch (Exception e) {
            throw ExceptionUtil.transform(e);
        }
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
            } catch (NoSuchFieldException ignored) {
            }
        }
        return null;
    }

    private static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException ignored) {
            }
        }
        return null;
    }

    private static Method getDeclaredMethod(Object object, String methodName) {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)){
                return method;
            }
        }
        return null;
    }
}