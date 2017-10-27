package org.lightfw.tool;

import org.apache.commons.beanutils.BeanUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReaderTool {

    public <T> List<T> read(String filePath, Class<T> clazz) {
        String[] headers = readHeader(filePath);
        List<Map<String, Object>> objects = readLines(filePath, headers.length, headers);
        List<T> list = new ArrayList<>();
        for (Map<String, Object> map : objects) {
            // T object = PopulateUtil.map2Obj(map, clazz);
            try {
                T object = clazz.newInstance();
                BeanUtils.populate(object, map);
                list.add(object);
            } catch (Throwable e) {
                e.printStackTrace();
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

    public static List<Map<String, Object>> readLines(String strFile, int columnCount, String[] headers) {
        File file = new File(strFile);
        List<Map<String, Object>> list = new ArrayList<>();
        boolean isFirst = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                String[] strs = line.replace("\"", "").split(",");
                if (!(strs == null || strs.length < headers.length)) {
                    Map<String, Object> objects = arrayToMap(strs, headers);
                    list.add(objects);
                }
            }
        } catch (Throwable e) {
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
