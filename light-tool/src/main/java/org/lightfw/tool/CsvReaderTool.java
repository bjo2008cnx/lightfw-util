package org.lightfw.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 从DB中导出CSV格式的数据，本类用于解析该CSV数据，第一行为表头，其它行为实际数据
 */
public class CsvReaderTool {

    /**
     * 读CSV文件
     *
     * @param strFile     文件名
     * @param columnCount 每行用逗号分割后的列数
     * @return
     */
    public static List<String[]> readLines(String strFile, int columnCount) {
        File file = new File(strFile);
        List<String[]> list = new ArrayList<>();
        boolean isFirst = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                String[] strs = line.replace("\"", "").split(",");
                if (strs == null || strs.length < columnCount) continue;
                list.add(strs);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return list;
    }
}
