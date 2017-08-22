package org.light.util.testing;

import java.util.Map;

/**
 * MapUtil
 *
 * @author Wangxm
 * @date 2016/5/5
 */
public class MapTestUtil {
    /**
     * 获取Map中的属性
     * 由于Map.toString()打印出来的参数值对,是横着一排的...参数多的时候,不便于查看各参数值
     * 故此仿照commons-lang.jar中的ReflectionToStringBuilder.toString()编写了本方法
     *
     * @return String key11=value11 \n key22=value22 \n key33=value33 \n......
     */
    public static String getStringFromMap(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(map.getClass().getName()).append("@").append(map.hashCode()).append("[");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append("\n").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.append("\n]").toString();
    }

    /**
     * 获取Map中的属性
     * 该方法的参数适用于打印Map<String, byte[]>的情况
     *
     * @return String key11=value11 \n key22=value22 \n key33=value33 \n......
     */
    public static String getStringFromMapForByte(Map<String, byte[]> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(map.getClass().getName()).append("@").append(map.hashCode()).append("[");
        for (Map.Entry<String, byte[]> entry : map.entrySet()) {
            sb.append("\n").append(entry.getKey()).append("=").append(new String(entry.getValue()));
        }
        return sb.append("\n]").toString();
    }


    /**
     * 获取Map中的属性
     * 该方法的参数适用于打印Map<String, Object>的情况
     *
     * @return String key11=value11 \n key22=value22 \n key33=value33 \n......
     */
    public static String getStringFromMapForObject(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(map.getClass().getName()).append("@").append(map.hashCode()).append("[");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append("\n").append(entry.getKey()).append("=").append(entry.getValue().toString());
        }
        return sb.append("\n]").toString();
    }
}
