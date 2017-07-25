package org.lightfw.util.ext.dynamic;

import org.junit.Assert;
import org.junit.Test;
import org.lightfw.util.ext.dynamic.proxy.Account;
import org.lightfw.util.ext.dynamic.proxy.AccountImpl;
import org.lightfw.util.ext.dynamic.proxy.BaseDomain;
import org.lightfw.util.ext.dynamic.proxy.SecurityProxyInvocationHandler;

import java.util.List;

public class ReflectProxyUtilTest {
    @Test
    public void getInterfaces() throws Exception {
        List<Class> parents = ReflectProxyUtil.getInterfaces(new AccountImpl());
        Assert.assertTrue( parents.contains(BaseDomain.class));
        Assert.assertTrue( parents.contains(Account.class));
    }

    @Test
    public void testCreateProxy() {
        Account toProxy = new AccountImpl();
        Account account = ReflectProxyUtil.createProxy(toProxy, new SecurityProxyInvocationHandler(toProxy));
        String result = account.operation();
        Assert.assertEquals("Xx",result);
    }
}