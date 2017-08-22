package org.lightfw.util.collection;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Set工具类
 *
 * @author Wangxm
 * @date 2016/5/6
 */
public class SetUtil {


    /**
     * 创建ArrayList
     *
     * @param elements
     * @param <E>
     * @return
     */
    public static <E> HashSet<E> newHashSet(E... elements) {
        return Sets.newHashSet(elements);
    }

    /**
     * 向已有的set中添加新元素
     *
     * @param set
     * @param elements
     * @param <E>
     */
    public static <E> void addAll(Set<E> set, E... elements) {
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                set.add(elements[i]);
            }
        }
    }

    /**
     * 合并
     *
     * @param a 集合a
     * @param b 集合b
     * @return 合并后的集合
     */
    public static Set union(Set a, Set b) {
        return Sets.union(a, b);
    }

    /**
     * 不同的部分
     *
     * @param a 集合a
     * @param b 集合b
     * @return 两个集合不同的部分
     */
    public static Set difference(Set a, Set b) {
        return Sets.difference(a, b);
    }

    /**
     * 交集
     *
     * @param a 集合a
     * @param b 集合b
     * @return 两个集合的交集
     */
    public static Set intersection(Set a, Set b) {
        return Sets.intersection(a, b);

    }
}
