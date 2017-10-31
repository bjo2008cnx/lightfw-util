package org.lightfw.util.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by lenovo on 2017/10/31.
 */
public class MapWriteUtilTest {
    @Test
    public void createHashMap() throws Exception {
        Map map = MapWriteUtil.createHashMap("1", null, 2, "2");
        Assert.assertEquals("2", map.get(2));
    }
}