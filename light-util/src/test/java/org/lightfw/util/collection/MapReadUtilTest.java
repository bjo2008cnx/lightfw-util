package org.lightfw.util.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

public class MapReadUtilTest {
    @Test
    public void get() throws Exception {
        Map map = new Properties();
        map.put("app_id", "1234");
        map.put("to_auth_url", "http://123.com");
        map.put("is_closable", "true");
        map.put("is_switch_on", "true");
        map.put("count", "100");
        Object isSwitchOn = MapReadUtil.get(map, "is_switch_on", Boolean.class);
        Assert.assertEquals(Boolean.TRUE, isSwitchOn);
    }
}