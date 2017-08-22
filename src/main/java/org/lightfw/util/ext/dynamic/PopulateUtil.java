package org.lightfw.util.ext.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.lightfw.util.lang.ExceptionUtil;
import org.lightfw.util.text.StringExtUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PopulateUtil {
    /**
     * 将rs数据拷贝到map
     */
    public static void populate(Map map, ResultSet rs) {
        if (map == null) {
            map = new HashMap();
        }
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            for (int i = 1; i <= count; i++) {
                String key = rsmd.getColumnLabel(i);
                String value = rs.getString(i);
                map.put(key.toLowerCase(), value);
            }
        } catch (SQLException e) {
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 对象转成Map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> Obj2Map(Object obj) throws Exception {
        Map<String, Object> map = new HashMap();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    /**
     * Map 转成Object, 属性名转换为驼峰风格
     *
     * @param map
     * @param clz
     * @return
     * @throws Exception
     */
    public static <T> T map2Obj(Map<String, Object> map, Class<T> clz){
        return map2Obj(map,clz,true);
    }

    /**
     * Map 转成Object, 属性名不转换
     *
     * @param map
     * @param clz
     * @return
     * @throws Exception
     */
    public static <T> T map2Obj(Map<String, Object> map, Class<T> clz, boolean convertToCamel) {
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
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                String propName = fieldName;
                if (convertToCamel){
                    propName= StringExtUtil.camelToUnderLine(fieldName);
                }
                Object value = map.get(propName);
                field.set(obj, value);
            } catch (IllegalAccessException e) {
                log.error("fail to set field", e);
            }
            field.setAccessible(false);
        }
        return obj;
    }
}
