package org.lightfw.utilx.text.transfer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * JSON 工具类
 */
public class JSONUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * JSON 输出格式,WriteNullListAsEmpty:list字段如果为null，输出为[]，而不是null , 默认不是SerializerFeature.PrettyFormat
     */
    public static final SerializerFeature[] jsonFeatures = {SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty};
    public static final SerializerFeature[] jsonFeaturesPretty = {SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.PrettyFormat};

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 不抛出未知属性异常
    }

    public static String toJSONString(Object content) {
        return JSON.toJSONString(content, jsonFeatures);
    }

    public static String toPrettyJSON(Object content) {
        return JSON.toJSONString(content, jsonFeaturesPretty);
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

    /**
     * 格式化json为Pretty格式
     *
     * @param content
     * @return
     */
    public static String toPretty(String content) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        int count = 0;
        while (index < content.length()) {
            char ch = content.charAt(index);
            if (ch == '{' || ch == '[') {
                sb.append(ch).append('\n');
                count++;
                for (int i = 0; i < count; i++) {
                    sb.append('\t');
                }
            } else if (ch == '}' || ch == ']') {
                sb.append('\n');
                count--;
                for (int i = 0; i < count; i++) {
                    sb.append('\t');
                }
                sb.append(ch);
            } else if (ch == ',') {
                sb.append(ch).append('\n');
                for (int i = 0; i < count; i++) {
                    sb.append('\t');
                }
            } else {
                sb.append(ch);
            }
            index++;
        }
        return sb.toString();
    }

    /**
     * 把格式化的json紧凑
     *
     * @param content
     * @return
     */
    public static String toCompact(String content) {
        return Pattern.compile("[\t\n]").matcher(content).replaceAll("").trim();
    }
}
