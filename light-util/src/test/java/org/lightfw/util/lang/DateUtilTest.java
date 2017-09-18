package org.lightfw.util.lang;

import org.junit.Test;
import org.lightfw.util.date.DateUtil;

public class DateUtilTest {

    @Test
    public void testGetDate() throws Exception {
        String date = DateUtil.getDate(6);
        System.out.println(date);
    }
}