package org.lightfw.utilx.dynamic;

import org.junit.Assert;
import org.junit.Test;
import org.lightfw.util.ext.dynamic.PopulateUtil;

import java.util.Map;
import java.util.Properties;

/**
 * Created by lenovo on 2017/8/21.
 */
public class PopulateUtilTest {
    @Test
    public void obj2Map() throws Exception {

    }

    @Test
    public void map2Obj() throws Exception {
        Map map = new Properties();
        map.put("app_id", "1234");
        map.put("to_auth_url", "http://123.com");
        map.put("is_closable", "true");
        map.put("is_switch_on", "true");
        map.put("count", "100");
        AuthConfig config = PopulateUtil.map2Obj(map, AuthConfig.class);
        Assert.assertEquals(1234, config.getAppId());
        Assert.assertEquals("http://123.com", config.getToAuthUrl());
        Assert.assertEquals(Boolean.TRUE, config.getIsClosable());
        Assert.assertEquals(Boolean.TRUE, config.isSwitchOn());
    }

    @Test
    public void map2Obj_prefix() throws Exception {
        Map map = new Properties();
        map.put("common.app_id", "1234");
        map.put("common.to_auth_url", "http://123.com");
        map.put("common.is_closable", "true");
        map.put("common.is_switch_on", "true");
        map.put("common.count", "100");
        AuthConfig config = PopulateUtil.map2Obj(map, AuthConfig.class,"common.");
        Assert.assertEquals(1234, config.getAppId());
        Assert.assertEquals("http://123.com", config.getToAuthUrl());
        Assert.assertEquals(Boolean.TRUE, config.getIsClosable());
        Assert.assertEquals(Boolean.TRUE, config.isSwitchOn());
    }
}