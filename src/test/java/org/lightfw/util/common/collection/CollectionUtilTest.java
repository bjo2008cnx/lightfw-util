package org.lightfw.util.common.collection;

import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class CollectionUtilTest {

    @Test
    public void testGetFirst() throws Exception {
        Collection<String> c = ListUtil.newArrayList("99", "3", "1", "4");
        assertEquals("99", CollectionUtil.getFirst(c));
    }

    @Test
    public void testGet() throws Exception {
        Collection<String> c = ListUtil.newArrayList("99", "3", "1", "4");
        assertEquals("3", CollectionUtil.get(c, 1));
        assertEquals("1", CollectionUtil.get(c, 2));
    }

    @Test
    public void testGetLast() throws Exception {
        Collection<String> c = ListUtil.newArrayList("99", "3", "1", "4");
        assertEquals("4", CollectionUtil.getLast(c));
    }

    @Test
    public void testTransform() throws Exception {

    }

    @Test
    public void testMin() throws Exception {

    }

    @Test
    public void testMax() throws Exception {

    }

    @Test
    public void testToArray() throws Exception {
        Collection<String> c = ListUtil.newArrayList("a", "b");
        String[] array = CollectionUtil.toArray(c);
        assertEquals("a", (array[0]));
        assertEquals("b", (array[1]));
    }
}