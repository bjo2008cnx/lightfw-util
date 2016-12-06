package org.lightfw.util.ext.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 实现一个标准的java.util.Set，按照add的先后顺序读取
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class SequenceSet<E> extends HashSet<E> {
    private static final long serialVersionUID = 1L;

    List<E> lKeyIndex = null;

    /**
     * 构造函数:
     */
    public SequenceSet() {
        super();
        lKeyIndex = new ArrayList<E>();
    }

    public boolean add(E key) {
        if (!lKeyIndex.contains(key)) {
            this.lKeyIndex.add(key);
        }
        return super.add(key);
    }

    public boolean remove(Object key) {
        lKeyIndex.remove(key);
        return super.remove(key);
    }

    public boolean removeAll(Collection<?> coll) {
        lKeyIndex.removeAll(coll);
        return super.removeAll(coll);
    }

    public Iterator<E> iterator() {
        return lKeyIndex.iterator();
    }

    public String toString() {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("[");
        Iterator iterator1 = iterator();
        boolean flag = iterator1.hasNext();
        do {
            if (!flag) {
                break;
            }
            Object obj = iterator1.next();
            stringbuffer.append(obj != this ? String.valueOf(obj) : "(this Collection)");
            flag = iterator1.hasNext();
            if (flag) {
                stringbuffer.append(", ");
            }
        } while (true);
        stringbuffer.append("]");
        return stringbuffer.toString();
    }

    final static class TestRmSequenceSet {
        public static void main(String[] args) {
            Set<String> testSet = new SequenceSet<String>();
            testSet.add("123");
            testSet.add("234");
            testSet.add("943");
            testSet.add("417");
            testSet.add("22");
            testSet.add("823");
            System.out.println(testSet);
            for (Iterator itTestMap = testSet.iterator(); itTestMap.hasNext(); ) {
                System.out.println(itTestMap.next());
            }
        }
    }


}