package org.lightfw.util.collection;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lenovo on 2017/11/2.
 */
public class ArrayUtilTest {
    @Test
    public void equalsOneOfInt() throws Exception {
        Assert.assertTrue(ArrayUtil.equalsOneOf(1, 3, 1, 2, 5));
        Assert.assertTrue(!ArrayUtil.equalsOneOf(10, 3, 1, 2, 5));
        Assert.assertTrue(!ArrayUtil.equalsOneOf(10, null));
    }

    @Test
    public void equalsOneOfString() throws Exception {
        Assert.assertTrue(ArrayUtil.equalsOneOf("1", "3", "1", "2", "5"));
        Assert.assertTrue(!ArrayUtil.equalsOneOf("10", "3", "1", "2", "5"));
        Assert.assertTrue(!ArrayUtil.equalsOneOf("10", null));
        Assert.assertTrue(!ArrayUtil.equalsOneOf(null, null));
        Assert.assertTrue(!ArrayUtil.equalsOneOf(null, "3", "1"));
    }
}