package org.lightfw.util.ext.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SecurityProxyInvocationHandler implements InvocationHandler {
    private Object proxyedObject;

    public SecurityProxyInvocationHandler(Object o) {
        proxyedObject = o;
    }

    public Object invoke(Object object, Method method, Object[] arguments) throws Throwable {
        if (object instanceof Account && method.getName().equals("operation")) {
            System.out.println("dynamically proxied operation");
        }
        return method.invoke(proxyedObject, arguments);
    }
}