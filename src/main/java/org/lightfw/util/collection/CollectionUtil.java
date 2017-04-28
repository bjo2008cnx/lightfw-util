package org.lightfw.util.collection;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import org.lightfw.constant.GlobalConstant;
import org.lightfw.util.validate.Valid;

import javax.annotation.Nullable;
import java.util.*;

/**
 * 封装一些集合相关度工具类
 * <p/>
 * Collection<--List<--Vector
 * Collection<--List<--ArrayList
 * Collection<--List<--LinkedList
 * Collection<--Set<--HashSet
 * Collection<--Set<--HashSet<--LinkedHashSet
 * Collection<--Set<--SortedSet<--TreeSet
 * Map<--SortedMap<--TreeMap
 * Map<--HashMap
 */
public class CollectionUtil {

    /**
     * 功能：集合是否为空。如果传入的值为null或者集合不包含元素都认为为空。
     *
     * @param c 集合
     * @return boolean 为空返回true，否则返回false。
     */
    public static boolean isEmpty(Collection c) {
        return (c == null || c.isEmpty());
    }


    /**
     * 功能：集合是否为空。如果传入的值为null或者集合不包含元素都认为为空。
     *
     * @param c 集合
     * @return boolean 不为空返回true，否则返回false。
     */
    public static boolean isNotEmpty(Collection c) {
        return !isEmpty(c);
    }

    /**
     * 取集合的第一个元素
     *
     * @param c   集合
     * @param <T> 元素类型
     * @return 第一个元素
     */
    public static <T> T getFirst(Collection<? extends T> c) {
        return get(c, 0);
    }

    /**
     * 取集合的第一个元素
     *
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T get(Collection<? extends T> c, int index) {
        if (c == null) {
            return null;
        }
        return Iterables.get(c, index);
    }

    /**
     * 取最后一个元素
     *
     * @param c   集合
     * @param <T> 元素类型
     * @return 最后一个元素
     */
    public static <T> T getLast(Collection<? extends T> c) {
        return get(c, c.size() - 1);
    }


    /**
     * 转换格式
     *
     * @return
     */
    public static Collection transform() {
        throw GlobalConstant.Exceptions.TODO;
    }

    /**
     * 取最小值
     *
     * @param c
     * @param <T>
     * @return
     */
    public static <T extends Object & Comparable<? super T>> T min(Collection<? extends T> c) {
        if (isEmpty(c)) {
            return null;
        }
        return Collections.min(c);
    }

    /**
     * 取最大值
     *
     * @param c   集合
     * @param <T> 元素类型
     * @return 最大值
     */
    public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> c) {
        if (isEmpty(c)) {
            return null;
        }
        return Collections.max(c);
    }

    /**
     * 把iterable按指定大小分割，得到的子集都不能进行修改操作
     *
     * @param c    集合
     * @param size 每个子集大小
     * @param <T>  元素类型
     * @return 子集的列表
     */
    public static <T> Iterable<List<T>> partition(final Collection<T> c, final int size) {
        return Iterables.partition(c, size);
    }

    /**
     * 获取集合中唯一的元素，如果iterable为空或有多个元素，则快速失败
     *
     * @param c   集合
     * @param <T> 元素类型
     * @return 集合中唯一的元素
     */
    public static <T> T getOnlyElement(Collection<T> c) {
        return Iterators.getOnlyElement(c.iterator());
    }

    /**
     * 获取集合中唯一的元素，如果iterable为空或有多个元素，则快速失败
     *
     * @param c   集合
     * @param <T> 元素类型
     * @return 集合中唯一的元素
     */
    @Nullable
    public static <T> T getOnlyElement(Collection<T> c, @Nullable T defaultValue) {
        return Iterators.getOnlyElement(c.iterator(), defaultValue);
    }

    /**
     * 返回集合的不可变视图
     *
     * @param c   集合
     * @param <T> 元素类型
     * @return 集合的不可变视图
     */
    public static <T> Iterable<T> unmodifiableIterable(Collection<T> c) {
        return Iterables.unmodifiableIterable(c);
    }

    /**
     * 将集合转化成数组
     *
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T[] toArray(Collection<T> c) {
        int size = CollectionUtil.isEmpty(c) ? 0 : c.size();
        T[] objs = (T[]) java.lang.reflect.Array.newInstance(CollectionUtil.getFirst(c).getClass(), size);
        int i = 0; //数组下标。
        for (T obj : c) {
            objs[i++] = obj;
        }
        return objs;
    }

    /**
     * 去重
     */
    public static <T> List<T> removeDuplicate(List<T> list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }

    /**
     * 使用指定的Filter过滤集合
     */
    public static <T> List<T> filter(List<T> list, Filters.ListFilter filter) {
        List result = new ArrayList();
        if (Valid.valid(list)) {
            for (T t : list) {
                if (filter.filter(t)) {
                    result.add(t);
                }
            }
        }
        return result;
    }

    public static <T> Set<T> filter(Set<T> set, Filters.SetFilter filter) {
        Set result = new HashSet();
        if (Valid.valid(set)) {
            for (T t : set) {
                if (filter.filter(t)) {
                    result.add(t);
                }
            }
        }
        return result;
    }

    public static <T> Queue filter(Queue<T> queue, Filters.QueueFilter filter) {
        Queue result = new LinkedList();
        if (Valid.valid(queue)) {
            for (T t : queue) {
                if (filter.filter(t)) {
                    result.add(t);
                }
            }
        }
        return result;
    }

    public static <K, V> Map filter(Map<K, V> map, Filters.MapFilter filter) {
        Map result = new HashMap();
        if (Valid.valid(map)) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                if (filter.filter(entry)) {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return result;
    }

    /**
     * 求俩个集合的交集
     */
    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        if (Valid.valid(list1, list2)) {
            Set<T> set = new HashSet<>(list1);
            set.retainAll(list2);
            return new ArrayList<>(set);
        }
        return new ArrayList<T>();
    }

    public static <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        if (Valid.valid(set1, set2)) {
            List<T> list = new ArrayList<T>(set1);
            list.retainAll(set2);
            return new HashSet<>(list);
        }
        return new HashSet<T>();
    }

    public static <T> Queue<T> intersection(Queue<T> queue1, Queue<T> queue2) {
        if (Valid.valid(queue1, queue2)) {
            Set<T> set = new HashSet<T>(queue1);
            set.retainAll(queue2);
            return new LinkedList<T>(set);
        }
        return new LinkedList<T>();
    }

    /**
     * Map集合的交集只提供键的交集
     *
     * @param map1
     * @param map2
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> intersection(Map<K, V> map1, Map<K, V> map2) {
        Map<K, V> map = new HashMap<>();
        if (Valid.valid(map1, map2)) {
            Set<K> setkey1 = new HashSet<>(map1.keySet());
            Set<K> setkey2 = new HashSet<>(map2.keySet());
            setkey1.retainAll(setkey2);
            for (K k : setkey1) {
                map.put(k, map1.get(k));
            }
        }
        return map;
    }

    /**
     * 求俩个集合的并集
     */
    public static <T> List<T> unicon(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList();
        list.addAll(list1);
        list.addAll(list2);
        return list;
    }

    public static <T> Set<T> unicon(Set<T> set1, Set<T> set2) {
        Set<T> set = new HashSet<>();
        set = set1;
        set.addAll(set2);
        return set;
    }

    public static <T> Queue<T> unicon(Queue<T> queue1, Queue<T> queue2) {
        Queue queue = new LinkedList();
        queue.addAll(queue1);
        queue.addAll(queue2);
        return queue;
    }

    public static <K, V> Map<K, V> unicon(Map<K, V> map1, Map<K, V> map2) {
        Map<K, V> map = new HashMap<>();
        map.putAll(map1);
        map.putAll(map2);
        return map;
    }

    /**
     * 求俩个集合的差集
     */
    public static <T> List<T> subtract(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<>();
        if (Valid.valid(list1)) {
            list.addAll(list1);
            list.removeAll(list2);
        }
        return list;
    }

    public static <T> Set<T> subtract(Set<T> set1, Set<T> set2) {
        Set<T> set = new HashSet<>();
        if (Valid.valid(set1)) {
            set.addAll(set1);
            set.removeAll(set2);
        }
        return set;
    }

    public static <T> Queue<T> subtract(Queue<T> queue1, Queue<T> queue2) {
        Queue<T> queue = new LinkedList<>();
        if (Valid.valid(queue1)) {
            queue.addAll(queue1);
            queue.removeAll(queue2);
        }
        return queue;
    }

    public static <K, V> Map<K, V> subtract(Map<K, V> map1, Map<K, V> map2) {
        Map<K, V> map = new HashMap<>();
        if (Valid.valid(map1, map2)) {
            Set<K> setkey1 = new HashSet<>(map1.keySet());
            Set<K> setkey2 = new HashSet<>(map2.keySet());
            for (K k : setkey2) {
                setkey1.remove(k);
            }
            for (K k : setkey1) {
                map.put(k, map1.get(k));
            }
        }
        return map;

    }

    /**
     * 将Integer集合转换成Long集合
     *
     * @param values
     * @return
     */
    public static Collection<Long> toLongs(Collection<Integer> values) {
        if (values == null) {
            return GlobalConstant.Collections.EMPTY_LIST;
        }
        Collection<Long> longs = new ArrayList<>(values.size());
        for (Integer value : values) {
            if (value != null) {
                longs.add(Long.valueOf(value.longValue()));
            }
        }
        return longs;
    }
}