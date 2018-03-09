package org.lightfw.util.validate;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2018/3/8.
 */
public class AssertUtilTest {

    @Test
    public void assertNotNullOrEmpty() throws Exception {
        AssertUtil.assertNotNullOrEmpty("T", "name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertNotNullOrEmpty2() throws Exception {
        AssertUtil.assertNotNullOrEmpty(null, "name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertNotNullOrEmpty3() throws Exception {
        AssertUtil.assertNotNullOrEmpty("", "name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertNotNullOrEmpty4() throws Exception {
        AssertUtil.assertNotNullOrEmpty("   ", "name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertNotNullOrEmpty5() throws Exception {
        AssertUtil.assertNotNullOrEmpty(null, "text", null, "employ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertNotNullOrEmpty6() throws Exception {
        AssertUtil.assertNotNullOrEmpty("1", "text", new HashMap<>(), "employ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertNotNullOrEmpty7() throws Exception {
        AssertUtil.assertNotNullOrEmpty("1", "text", new ArrayList<>(), "employ");
    }

    @Test
    public void assertNotNullOrEmpty8() throws Exception {
        try {
            AssertUtil.assertNotNullOrEmpty("1", "text", Arrays.asList(1, 2), "employ");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void assertNotNullOrEmpty9() throws Exception {
        Map m = new HashMap<>();
        m.put("x", "");
        try {
            AssertUtil.assertNotNullOrEmpty("1", "text", m, "employ");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void assertNotNullOrEmpty10() throws Exception {
        try {
            AssertUtil.assertNotNullOrEmpty(null, "text", null, "employ");
        } catch (Exception e) {
            Assert.assertEquals("text&employ cannot be null or empty", e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertTrue() throws Exception {
        AssertUtil.assertTrue(false, "isValid");
    }

    @Test
    public void assertTrue2() throws Exception {
        try {
            AssertUtil.assertTrue(1 == 1, "isValid");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}