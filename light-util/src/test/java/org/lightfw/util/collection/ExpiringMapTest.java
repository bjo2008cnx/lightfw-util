package org.lightfw.util.collection;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lenovo on 2018/3/12.
 */
public class ExpiringMapTest {
    @Test
    public void get() throws Exception {
        ExpiringMap map = new ExpiringMap(5);
        map.getExpirer().startExpiringIfNotStarted();

        map.put("age", "1");
        Assert.assertNotNull(map.get("age"));

        Thread.sleep(50);
        Assert.assertEquals("1", map.get("age"));

        Thread.sleep(100);
        Assert.assertEquals("1", map.get("age"));

        Thread.sleep(6000);
        Assert.assertNull(map.get("age"));

        Thread.sleep(150);
        Assert.assertNull(map.get("age"));
    }
}