package org.lightfw;

import org.lightfw.util.lang.Valid;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class validTest extends TestCase {

    public void testIsValid() throws Exception {
        assertEquals(false, Valid.valid(""));
        assertEquals(true, Valid.valid("111"));
        assertEquals(true, Valid.valid("111", "2222"));
        assertEquals(false, Valid.valid("111", ""));
    }

    public void testIsValid1() throws Exception {
        assertEquals(true, Valid.valid(new String("11")));

    }

    public void testIsValid2() throws Exception {
        Map map = new HashMap();
        assertEquals(false, Valid.valid(map));
        map.put("1", "1");
        assertEquals(true, Valid.valid(map));
        Map map1 = new HashMap();
        assertEquals(false, Valid.valid(map, map1));
        map1.put("2", "2");
        assertEquals(true, Valid.valid(map, map1));
    }

    public void testIsDate() {
        assertEquals(true, Valid.isDate("2016-03-16 00:07:02",
                "yyyy-MM-dd HH:mm:ss"));
    }

    public void testSwith() {
        System.out.println(Valid.valid(new Object()));
        System.out.println(Valid.valid(""));
    }

}