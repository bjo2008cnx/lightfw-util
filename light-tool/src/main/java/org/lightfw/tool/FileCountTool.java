package org.lightfw.tool;

import java.io.*;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 统计文件数量
 */
public class FileCountTool {

    public static void main(String[] args) {
        File file = new File("E:\\codes-inf\\soa\\");
        File[] files = file.listFiles();
        Map<AtomicInteger, String> map = new TreeMap(new Comparator<AtomicInteger>() {
            public int compare(AtomicInteger obj1, AtomicInteger obj2) {
                // 降序排序
                return obj2.longValue() > obj1.longValue() ? 1 : -1;
            }
        });

        for (int i = 0; i < files.length; i++) {
            File fileTmp = files[i];
            if (fileTmp.isDirectory()) {
                AtomicInteger count = new AtomicInteger(0);
                AtomicInteger lineCount = new AtomicInteger(0);
                getFile(fileTmp, count, lineCount);
                //System.out.println(fileTmp.toString() + ":" + count + ":" + lineCount);
                map.put(lineCount, fileTmp.toString() + ":" + count);
            }
        }
        Set<Map.Entry<AtomicInteger, String>> set = map.entrySet();
        for (Map.Entry<AtomicInteger, String> entry : set) {
            System.out.println(entry.getKey() + " :: " + entry.getValue());
        }
    }

    public static void getFile(File file, AtomicInteger count, AtomicInteger lineCount) {
        File[] listFile = file.listFiles();
        for (int i = 0; i < listFile.length; i++) {
            File fileTmp = listFile[i];
            if (!fileTmp.isDirectory()) {
                if (fileTmp.getName().endsWith(".java")) {
                    count.incrementAndGet();
                    int fileLineCount = countFile(fileTmp);
                    // System.out.println(fileTmp.getName() + ":" + fileLineCount);
                    lineCount.addAndGet(fileLineCount);
                }
            } else {
                getFile(fileTmp, count, lineCount);
            }
        }
    }

    private static int countFile(File test) {
        long fileLength = test.length();
        LineNumberReader rf = null;
        int lines = 0;
        try {
            rf = new LineNumberReader(new FileReader(test));
            if (rf != null) {
                rf.skip(fileLength);
                lines = rf.getLineNumber();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(rf);
        }
        return lines;
    }

    /**
     * 通用的关闭方法
     *
     * @param closeables
     */
    public static void close(final Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                if (null != closeable) {
                    closeable.close();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}  