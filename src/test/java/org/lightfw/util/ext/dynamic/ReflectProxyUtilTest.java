package org.lightfw.util.ext.dynamic;

import junit.framework.Assert;
import org.junit.Test;
import org.lightfw.util.ext.dynamic.proxy.Account;
import org.lightfw.util.ext.dynamic.proxy.AccountImpl;
import org.lightfw.util.ext.dynamic.proxy.SecurityProxyInvocationHandler;

public class ReflectProxyUtilTest {

    @Test
    public void testCreateProxy() {
        Account toProxy = new AccountImpl();
        Account account = ReflectProxyUtil.createProxy(toProxy, new SecurityProxyInvocationHandler(toProxy));
        String result = account.operation();
        Assert.assertEquals("Xx",result);
    }
}