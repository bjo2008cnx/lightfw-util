package org.lightfw.util.lang;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ObjectUtilTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void test() {
        int result = ObjectUtil.compare(1L, 2L, true);
        Assert.assertEquals(-1, result);

        result = ObjectUtil.compare(null, 2L, true);
        Assert.assertEquals(1, result);

        result = ObjectUtil.compare(1L, null, true);
        Assert.assertEquals(-1, result);
    }
}
