package org.lightfw.util.dynamic;

import org.lightfw.util.ext.dynamic.ReflectProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class ReflectProxyUtilTest {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        final BigDecimal bd = new BigDecimal(333);
        Object obj = ReflectProxyUtil.createProxy(bd, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(bd, args);
            }
        });
        System.out.println("obj.getClass():" + obj.getClass() + "\ngetInterfaces(obj):" + ReflectProxyUtil.getInterfaces(obj));
        System.out.println(((Comparable<BigDecimal>) obj).compareTo(new BigDecimal(334)));

        System.out.println("obj.getClass():" + bd.getClass() + "\ngetInterfaces(bd):" + ReflectProxyUtil.getInterfaces(bd));
        System.out.println((bd).compareTo(new BigDecimal(334)));
    }

}