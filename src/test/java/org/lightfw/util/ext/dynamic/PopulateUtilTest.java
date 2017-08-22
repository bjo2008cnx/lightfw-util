package org.lightfw.util.ext.dynamic;

import org.junit.Assert;
import org.junit.Test;

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
        map.put("appId", "1234");
        map.put("toAuthUrl", "http://123.com");
        AuthConfig config = PopulateUtil.map2Obj(map, AuthConfig.class);
        Assert.assertEquals("1234", config.getAppId());
        Assert.assertEquals("http://123.com", config.getToAuthUrl());
    }
}