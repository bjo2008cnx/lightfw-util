package org.lightfw.utilx.ext;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.lightfw.util.lang.StringUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    private static ObjectMapper mapper = null;

    /**
     * 转换成Json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Json转换为对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<?> clazz) {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        try {
            return (T) mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parse(Object parent, Map origin, int level) {
        for (Object single : origin.keySet()) {
            String myParent = StringUtil.isEmpty(parent.toString()) ? single.toString() : (parent + "." + single);
            Object value = origin.get(single);
            if (value instanceof Map) {
                parse(myParent, (Map) value, level + 1);
                continue;
            }
            if (value instanceof List) {
                parse(myParent, (List) value, level + 1);
                continue;
            }
        }
    }

    public static void parse(Object parent, List origin, int level) {
        for (Object single : origin) {
            if (single instanceof Map) {
                parse(parent, (Map) single, level + 1);
                continue;
            }
            if (single instanceof List) {
                parse(parent, (List) single, level + 1);
                continue;
            }
        }
    }
}
