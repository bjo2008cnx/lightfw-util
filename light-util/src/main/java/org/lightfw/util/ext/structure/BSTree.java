package org.lightfw.util.ext.structure;

import java.util.Collection;

/**
 * 基于数组二叉查找树实现
 * 二叉查找树是一种支持动态查询的数据结构，在当数据集合内容发生改变时，集合内数据的排列组合不用重新构建
 *
 * @param <T>
 * @author
 */
public class BSTree<T extends Comparable<T>> {
    private static final int DEFAULT_SIZE = 10;
    private T[] data;
    private Integer count;

    public BSTree() {
        data = (T[]) new Object[DEFAULT_SIZE];
        count = 0;
    }


    /**
     * 判断二叉树中是否包含元素 one
     *
     * @param one
     * @return 若找到了，返回该元素在数组中的位置，否则返回-1
     */
    public int search(T one) {
        if (data[0] == null) {
            System.out.println("==> This BSTree is Empty!");
            return -1;
        } else {
            int index = 0;
            while (index < data.length && data[index] != null) {
                int f = one.compareTo(data[index]);
                if (f == 0) {                    //找到了返回其位置
                    return index;
                } else if (f < 0) {
                    index = 2 * index + 1;
                } else {
                    index = 2 * index + 2;
                }
            }
            return -1;
        }
    }

    /**
     * 添加元素
     *
     * @param t
     */
    public void add(T t) {
        if (data[0] == null) {
            data[0] = t;
            count++;
            return;
        } else {
            int index = 0;
            while (index < data.length) {
                if (t.compareTo(data[index]) < 0) {
                    int left = 2 * index + 1;
                    if (left >= data.length)
                        expandSize();
                    if (data[left] == null) {
                        data[left] = t;
                        break;
                    } else {
                        index = left;
                    }
                } else if (t.compareTo(data[index]) > 0) {
                    int right = 2 * index + 2;
                    if (right >= data.length)
                        expandSize();
                    if (data[right] == null) {
                        data[right] = t;
                        break;
                    } else {
                        index = right;
                    }
                } else {                        // 相同元素不处理，算是排重了
                    break;
                }
            }
        }
        count++;
    }

    public void addAll(Collection<T> all) {
        for (T t : all) {
            add(t);
        }
    }

    public void addAll(T[] all) {
        for (T t : all) {
            add(t);
        }
    }

    public void delete(T del) {
        int del_index = this.search(del);
        if (del_index != -1) {                    //等于-1 表示没有，便不做处理
            deleteReally(del_index);
        }
    }

    /**
     * 删除某个节点
     *
     * @param index：该节点在数组中的位置
     * @return
     */
    private void deleteReally(int index) {
        int lc = 2 * index + 1;
        int rc = 2 * index + 2;
        if (data[lc] != null && data[rc] != null) {        // 左子树、右子树同时存在的情况
            int left_max_child = findLeftMaxChild(index);
            data[index] = data[left_max_child];          //删除节点
            deleteReally(left_max_child);                 //递归删除左子树中值最大的节点
        } else if (data[lc] == null && data[rc] == null) {  // 都没有则直接删除
            data[index] = null;
        } else {
            if (data[lc] != null) {
                replaceNodeWithChild(lc, lc - index);
            } else {
                replaceNodeWithChild(rc, rc - index);
            }
        }
    }

    /**
     * 寻找某个节点的左子树中最大节点
     *
     * @param index 某个节点的位置
     * @return 最大节点位置
     */
    private int findLeftMaxChild(int index) {
        int left = 2 * index + 1;
        int bigger = 2 * left + 2;
        while (bigger < data.length && data[bigger] != null) {
            left = bigger;
            bigger = 2 * bigger + 2;
        }
        return left;
    }

    /**
     * 若子节点C替换了其父节点P，则C的所有子节点都需要被移动(由于数组的原因)，distance为C和P在数组中位置之差。
     * 其所有子节点在数组中移动的距离为 distance*(2^x)，x为这些子节点与节点C的距离(相邻节点距离为1)；
     *
     * @param node
     * @param distance
     */
    private void replaceNodeWithChild(int node, int distance) {
        int left = 2 * node + 1;
        int right = 2 * node + 2;
        int current_distance = distance * 2;                   //每次递归距离*2
        if (data[left] != null) {
            data[left - current_distance] = data[left];
            replaceNodeWithChild(left, current_distance);     //递归遍历下个节点
        }
        if (data[right] != null) {
            data[right - current_distance] = data[right];
            replaceNodeWithChild(right, current_distance);
        }
    }

    /**
     * 扩充容量
     */
    private void expandSize() {
        T[] newDate = (T[]) new Object[data.length * 2];
        System.arraycopy(data, 0, newDate, 0, data.length);
        data = newDate;
    }

    public Integer getCount() {
        return count;
    }

    public BSTree(int size) {
        data = (T[]) new Object[size];
        count = 0;
    }
}