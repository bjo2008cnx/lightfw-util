package org.lightfw.util.ext.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectProxyUtil {
    /**
     * 得到对象所有的接口和父类、自身类
     *
     * @param obj
     * @return
     */
    public static List<Class> getInterfaces(Object obj) {
        final List<Class> interfaces = new ArrayList<Class>(Arrays.asList(obj.getClass().getInterfaces()));
        Class classe = obj.getClass().getSuperclass();
        while (classe != null) {
            final List<Class> superInterfaces = Arrays.asList(classe.getInterfaces());
            //删除重复的interface
            interfaces.removeAll(superInterfaces);
            interfaces.addAll(superInterfaces);
            classe = classe.getSuperclass();
        }
        return interfaces;
    }

    /**
     * 得到对象所有的接口和父类、自身类，字符串格式
     *
     * @param obj
     * @return
     */
    public static List<String> getInterfacesStr(Object obj) {
        List<Class> lInterface = getInterfaces(obj);
        List<String> lInterfaceStr = new ArrayList<String>();
        for (Class c : lInterface) {
            lInterfaceStr.add(c.getName());
        }
        return lInterfaceStr;
    }

    @SuppressWarnings("unchecked")
    /**
     * 创建类的Proxy
     */
    public static <T> T createProxy(T object, InvocationHandler invocationHandler) {
        final ClassLoader classLoader = object.getClass().getClassLoader(); // NOPMD
        return (T) Proxy.newProxyInstance(classLoader, getInterfaces(object).toArray(new Class[0]), invocationHandler);
    }
}
