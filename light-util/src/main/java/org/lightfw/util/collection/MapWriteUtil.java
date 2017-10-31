package org.lightfw.util.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * MapWriteUtil
 *
 * @author Michael.Wang
 * @date 2017/10/31
 */
public class MapWriteUtil {
    public static Map createHashMap(Object k, Object v) {
        return put(new HashMap(), k, v);
    }

    public static Map createHashMap(Object k1, Object v1, Object k2, Object v2) {
        return put(new HashMap(), k1, v1, k2, v2);
    }

    public static Map createHashMap(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
        return put(new HashMap(), k1, v1, k2, v2, k3, v3);
    }

    public static Map createHashMap(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4) {
        return put(new HashMap(), k1, v1, k2, v2, k3, v3, k4, v4);
    }

    /**
     * 增加属性
     */
    public static Map put(Map map, Object k, Object v) {
        if (k != null) {
            map.put(k, v);
        }
        return map;
    }

    /**
     * 增加属性
     */
    public static Map put(Map map, Object k1, Object v1, Object k2, Object v2) {
        put(map, k1, v1);
        put(map, k2, v2);
        return map;
    }

    /**
     * 增加属性
     */
    public static Map put(Map map, Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
        put(map, k1, v1, k2, v2);
        put(map, k3, v3);
        return map;
    }

    private static Map put(Map map, Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4) {
        put(map, k1, v1, k2, v2);
        put(map, k3, v3, k4, v4);
        return map;
    }

    /**
     * 添加map中的全部元素
     *
     * @param maps
     * @return
     */
    public static Map putAll(Map... maps) {
        Map m = new HashMap();
        for (Map map : maps) {
            if (map != null && !map.isEmpty()) {
                m.putAll(map);
            }
        }
        return m;
    }

}