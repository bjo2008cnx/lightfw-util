package org.lightfw.util.ext.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实现一个标准的java.util.Map，按照put的先后顺序读取，key值对大小写敏感
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class SequenceMap<K, V> extends HashMap<K, V> {

    List<K> keyIndexes = null;

    /**
     * 构造函数:
     */
    public SequenceMap() {
        super();
        keyIndexes = new ArrayList<K>();
    }

    public V put(K key, V value) {
        if (!keyIndexes.contains(key)) {
            this.keyIndexes.add(key);
        }

        return super.put(key, value);
    }

    public V remove(Object key) {
        keyIndexes.remove(key);
        return super.remove(key);
    }

    @Override
    public void clear() {
        keyIndexes.clear();
        super.clear();
    }

    public Set<K> keySet() {
        Set<K> sKey = new SequenceSet<K>();
        for (K key : keyIndexes) {
            sKey.add(key);
        }
        return sKey;
    }

    public String toString() {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("{");
        Iterator iterator = keySet().iterator();
        boolean flag = iterator.hasNext();
        do {
            if (!flag) {
                break;
            }
            Object key = iterator.next();
            Object obj = key;
            Object obj1 = this.get(key);
            stringbuffer.append((obj != this ? obj : "(this Map)") + "=" + (obj1 != this ? obj1 : "(this Map)"));
            flag = iterator.hasNext();
            if (flag) {
                stringbuffer.append(", ");
            }
        } while (true);
        stringbuffer.append("}");
        return stringbuffer.toString();
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        Set<K> sKey = keySet();
        Set<java.util.Map.Entry<K, V>> result = new SequenceSet<Map.Entry<K, V>>();
        final SequenceMap<K, V> thisObj = this;
        for (final K k : sKey) {
            result.add(new java.util.Map.Entry() {
                public Object getKey() {
                    return k;
                }

                public Object getValue() {
                    return thisObj.get(k);
                }

                public Object setValue(Object value) {
                    return thisObj.put(k, (V) value);
                }

            });
        }
        return result;
    }

}