package org.lightfw.util.lang;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

public class LogUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLog() {
        try {
            Logger log = LogUtil.getLogger(LogUtilTest.class);
            log.info("log infomation");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
