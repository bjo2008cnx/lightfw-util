package org.lightfw.util.ext.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.lightfw.util.collection.MapReadUtil;
import org.lightfw.util.lang.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 对象拷贝工具
 */
@Slf4j
public class PopulateUtil {
    /**
     * Map 转成Object, 属性名转换为驼峰风格
     *
     * @param map
     * @param clz
     * @return
     * @throws Exception
     */
    public static <T> T map2Obj(Map<String, Object> map, Class<T> clz) {
        return map2Obj(map, clz, true, null);
    }

    /**
     * Map 转成Object, 属性名转换为驼峰风格
     *
     * @param map
     * @param clz
     * @return
     * @throws Exception
     */
    public static <T> T map2Obj(Map<String, Object> map, Class<T> clz, String prefix) {
        return map2Obj(map, clz, true, prefix);
    }

    /**
     * Map 转成Object, 属性名不转换
     *
     * @param map
     * @param clz
     * @return
     * @throws Exception
     */
    public static <T> T map2Obj(Map<String, Object> map, Class<T> clz, boolean convertToCamel, String prefix) {
        T obj;
        try {
            obj = clz.newInstance();
        } catch (Exception e) {
            return null;
        }
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            writeField(map, convertToCamel, obj, field, prefix);
        }
        return obj;
    }

    /**
     * 对象转成Map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> Obj2Map(Object obj) {
        Map<String, Object> map = new HashMap();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                log.warn("fail to convert {}", field.getName(), e);
            }
        }
        return map;
    }

    /**
     * 给field赋值，主要解决驼峰风格问题和类型转换问题
     *
     * @param <T>
     * @param map
     * @param convertToCamel
     * @param obj
     * @param field
     * @param prefix
     */
    private static <T> void writeField(Map<String, Object> map, boolean convertToCamel, T obj, Field field, String prefix) {
        field.setAccessible(true);
        try {
            String fieldName = field.getName();

            //如果是驼峰风格，转成下画线
            String propName = convertToCamel ? StringUtil.camelToUnderLine(fieldName) : fieldName;
            //如果有前缀，加前缀。如：类中的field 为paassword, map中的key为: jdbc:password
            propName = StringUtil.isNotEmpty(prefix) ? prefix + propName : propName;
            Object value = MapReadUtil.get(map, propName, field.getType());
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            log.error("fail to set field", e);
        }
        field.setAccessible(false);
    }
}
