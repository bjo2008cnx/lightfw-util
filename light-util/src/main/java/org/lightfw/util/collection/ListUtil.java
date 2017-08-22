package org.lightfw.util.collection;

import com.google.common.collect.Lists;
import org.lightfw.constant.GlobalConstant;
import org.lightfw.util.math.MathUtil;

import java.util.*;
//import java.util.stream.Collectors;

/**
 * ListUtil
 *
 * @author Wangxm
 * @date 2016/5/6
 */
public class ListUtil extends CollectionUtil {

    /**
     * 创建ArrayList
     *
     * @param elements
     * @param <E>
     * @return
     */
    public static <E> ArrayList<E> newArrayList(E... elements) {
        return Lists.newArrayList(elements);
    }

    /**
     * 创建ArrayList
     *
     * @param elements
     * @param <E>
     * @return
     */
    public static <E> LinkedList<E> newLinkedList(E... elements) {
        LinkedList<E> list = new LinkedList<>();
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                list.add(elements[i]);
            }
        }
        return list;
    }

    /**
     * 获取一个空列表,该列表不可变
     *
     * @param <T>
     * @return
     */
    public static final <T> List<T> getEmptyList() {
        return (List<T>) GlobalConstant.Collections.EMPTY_LIST;
    }

    /**
     * 向已有的list中添加新元素
     *
     * @param list
     * @param elements
     * @param <E>
     */
    public static <E> void addAll(List<E> list, E... elements) {
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                list.add(elements[i]);
            }
        }
    }

    /**
     * 根据元素的自然顺序 对指定列表按升序进行排序。列表中的所有元素都必须实现 Comparable接口
     *
     * @param list 排序后的列表
     */
    public static void sort(List list) {
        if (isEmpty(list)) {
            return;
        }
        Collections.sort(list);
    }

    /**
     * 混排算法所做的正好与 sort 相反: 它打乱在一个 List 中可能有的任何排列的踪迹。
     * 也就是说，基于随机源的输入重排该 List,这样的排列具有相同的可能性（假设随机源是公正的）。这个算法在实现一个碰运气的游戏中是非常有用的。
     * 例如，它可被用来混排代表一副牌的Card 对象的一个 List 。另外，在生成测试案例时，它也是十分有用的。
     *
     * @param list 混排后的列表
     */
    public static void shuffling(List list) {
        if (isEmpty(list)) {
            return;
        }
        Collections.shuffle(list);
    }

    /**
     * 使用Reverse方法可以根据元素的自然顺序 对指定列表按降序进行排序
     *
     * @param list 反转后的列表
     */
    public static void reverse(List list) {
        if (isEmpty(list)) {
            return;
        }
        Collections.reverse(list);
    }


    /**
     * 功能：从List中随机取出一个元素。
     *
     * @param list 源List
     * @return T List的一个元素
     */
    public static <T> T random(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(MathUtil.randomNumber(0, list.size()));
    }

    /**
     * 列表比较
     *
     * @param <T>
     * @param a
     * @param b
     * @return
     */
    public static <T extends Comparable<T>> boolean equal(List<T> a, List<T> b) {
        if (a == null && b == null) {
            return true;
        } else if ((a == null && b != null) || (a != null && b == null)) {
            return false;
        }

        if (a.size() != b.size()) {
            return false;
        }
        Collections.sort(a);
        Collections.sort(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }

    /**
     * 去重
     */
    public static List distinct(List list) {
        // return (List) list.stream().distinct().collect(Collectors.toList());
        throw new RuntimeException();
    }
}