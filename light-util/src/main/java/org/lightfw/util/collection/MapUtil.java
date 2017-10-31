package org.lightfw.util.collection;

import com.google.common.base.Function;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

public class MapUtil {
    /**
     * Map是否为空。如果传入的值为null或者集合不包含元素都认为为空。
     *
     * @param map Map
     * @return boolean 为空返回true，否则返回false。
     */
    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 取两个Map不同的部分,例：
     * Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
     * Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
     * MapDifference<String, Integer> diff = Maps.difference(left, right);
     * diff.entriesInCommon(); // {"b" => 2}
     * diff.entriesInCommon(); // {"b" => 2}
     * diff.entriesOnlyOnLeft(); // {"a" => 1}
     * diff.entriesOnlyOnRight(); // {"d" => 5}
     *
     * @param left
     * @param right
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right) {
        return Maps.difference(left, right);
    }

    /**
     * 针对的场景是：有一组对象，它们在某个属性上分别有独一无二的值，而我们希望能够按照这个属性值查找对象
     * 这个方法返回一个Map，键为Function返回的属性值，值为Iterable中相应的元素，因此我们可以反复用这个Map进行查找操作
     * <p>
     * 比方说，我们有一堆字符串，这些字符串的长度都是独一无二的，而我们希望能够按照特定长度查找字符串：
     * ImmutableMap<Integer, String> stringsByIndex = Maps.uniqueIndex(strings, new Function<String, Integer> () {
     * public Integer apply(String string) {
     * return string.length();
     * }
     * });
     *
     * @param values
     * @param keyFunction
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> uniqueIndex(Iterable<V> values, Function<? super V, K> keyFunction) {
        return Maps.uniqueIndex(values, keyFunction);
    }

    /**
     * 取唯一的一个条目
     *
     * @param map
     * @return
     */
    public static Map.Entry getOnlyEntry(Map map) {
        if (isEmpty(map)) {
            return null;
        }
        Set<Map.Entry> set = map.entrySet();
        return CollectionUtil.getOnlyElement(set);
    }

    /**
     * 将map所有元素打印出来
     *
     * @param map
     * @return
     */
    public static String print(Map map) {
        StringBuffer buffer = new StringBuffer();
        Set<Map.Entry> set = map.entrySet();
        for (Map.Entry entry : set) {
            buffer.append(entry.getKey() + "::" + entry.getValue());
        }
        return buffer.toString();
    }

}
