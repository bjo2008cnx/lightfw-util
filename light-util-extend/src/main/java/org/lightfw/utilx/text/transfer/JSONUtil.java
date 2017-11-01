package org.lightfw.utilx.text.transfer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * JSON 工具类
 */
public class JSONUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * JSON 输出格式,WriteNullListAsEmpty:list字段如果为null，输出为[]，而不是null
     */
    public static final SerializerFeature[] jsonFeatures = {SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature
            .PrettyFormat};

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 不抛出未知属性异常
    }

    public static String toJSONString(Object content) {
        return JSON.toJSONString(content, jsonFeatures);
    }

    public static String toJSONString(Map content) {
        return JSON.toJSONString(content, jsonFeatures);
    }

    public static <T> T fromJson(String json, Class<?> clazz) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        try {
            return (T) objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
