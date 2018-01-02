package org.lightfw.tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.lightfw.util.ext.dynamic.PopulateUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 读取CSV文件，输出对象
 */
@Slf4j
public class CsvReaderTool {
    public static final String COMMENT_CHAR = "#";

    /**
     * 读数据并转换为驼峰格式field
     *
     * @param filePath
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Set<T> readWithCamal(String filePath, Class<T> clazz) {
        String[] headers = readHeader(filePath);
        Set<Map<String, Object>> objects = readLines(filePath, headers.length, headers);
        Set<T> list = new LinkedHashSet<>();
        for (Map<String, Object> map : objects) {
            try {
                //T object = clazz.newInstance();
                T object = PopulateUtil.map2Obj(map, clazz);
                list.add(object);
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }
        return list;
    }

    /**
     * 读数据但不转换为驼峰格式field
     *
     * @param filePath
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Set<T> read(String filePath, Class<T> clazz) {
        String[] headers = readHeader(filePath);
        Set<Map<String, Object>> objects = readLines(filePath, headers.length, headers);
        Set<T> list = new LinkedHashSet<>();
        for (Map<String, Object> map : objects) {
            try {
                T object = clazz.newInstance();
                BeanUtils.populate(object, map);
                //T object = PopulateUtil.map2Obj(map, clazz);
                list.add(object);
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }
        return list;
    }

    public static String[] readHeader(String strFile) {
        File file = new File(strFile);
        String[] headers = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            headers = line.replace("\"", "").split(",");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return headers;
    }

    public static Set<Map<String, Object>> readLines(String strFile, int columnCount, String[] headers) {
        File file = new File(strFile);
        Set<Map<String, Object>> list = new LinkedHashSet<>();
        boolean isFirst = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                if (line.startsWith(COMMENT_CHAR)) {
                    continue;
                }

                String[] strs = line.replace("\"", "").split(",");
                if (strs != null && strs.length == headers.length) {
                    Map<String, Object> objects = arrayToMap(strs, headers);
                    list.add(objects);
                } else {
                    continue;
                }
            }
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }

    private static Map<String, Object> arrayToMap(String[] strs, String[] headers) {
        Map<String, Object> map = new HashMap();
        for (int i = 0; i < headers.length; i++) {
            map.put(headers[i], strs[i]);
        }
        return map;
    }
}
