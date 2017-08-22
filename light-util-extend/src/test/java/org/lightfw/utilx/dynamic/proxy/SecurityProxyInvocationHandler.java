package org.lightfw.utilx.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SecurityProxyInvocationHandler implements InvocationHandler {
    private Object proxyedObject;

    public SecurityProxyInvocationHandler(Object o) {
        proxyedObject = o;
    }

    public Object invoke(Object object, Method method, Object[] arguments) throws Throwable {
        String result = null;
        if (object instanceof Account && method.getName().equals("operation")) {
            result = "X";
        }
        return result + method.invoke(proxyedObject, arguments);
    }
}