package org.lightfw.util.ext.structure;

import java.util.Iterator;
import java.util.Map;

/**
 * TODO 改成TEST CASE
 */
public class SequenceMapTest {

    public static void main(String[] args) {
        Map<String, Object> testMap = new SequenceMap<String, Object>();
        testMap.put("123", "3424231");
        testMap.put("234", "3424231");
        testMap.put("943", "3424231");
        testMap.put("417", "3424231");
        testMap.put("22", "3424231");
        testMap.put("823", "3424231");
        System.out.println(testMap);
        for (Iterator itTestMap = testMap.keySet().iterator(); itTestMap.hasNext(); ) {
            String key = itTestMap.next().toString();
            System.out.println(key + "=" + testMap.get(key));
        }
    }
}