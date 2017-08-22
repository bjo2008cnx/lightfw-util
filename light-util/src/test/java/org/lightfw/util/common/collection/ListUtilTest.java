package org.lightfw.util.common.collection;

import org.junit.Test;
import org.lightfw.util.collection.ListUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ListUtilTest {

    @Test
    public void testSort() throws Exception {
        List list = new ArrayList<>();
        int array[] = {12, 11, 23, 45, 2};
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        ListUtil.sort(list);
        assertEquals(2, list.get(0));
        assertEquals(11, list.get(1));
        assertEquals(12, list.get(2));
    }

    @Test
    public void testShuffling() throws Exception {
        List list = new ArrayList<>();
        int array[] = {12, 11, 23, 45, 2, 1, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        //List list2 =
        ListUtil.shuffling(list);
        System.out.print(list);
    }

    @Test
    public void testReverse() throws Exception {

    }

    @Test
    public void testRandomOne() throws Exception {

    }

    @Test
    public void testToArray() throws Exception {

    }

    @Test
    public void testAddArrayToList() throws Exception {

    }

    @Test
    public void testEqual() {
        //测试方法如下：
        List<Integer> a = Arrays.asList(1, 2, 3, 4);
        List<Integer> b = Arrays.asList(4, 3, 2, 1);
        assertTrue(ListUtil.equal(a, b));
    }
}