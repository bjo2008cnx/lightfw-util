package org.lightfw.tool;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 统计文件数量
 */
public class FileCountTool {

    public static void main(String[] args) {
        File file = new File("E:\\codes\\o2o\\o2o-mgr\\src\\main\\java\\com\\weimob\\o2o\\mgr");
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File fileTmp = files[i];
            if (fileTmp.isDirectory()) {
                AtomicInteger count = new AtomicInteger(0);
                getFile(fileTmp, count);
                System.out.println(fileTmp.toString() + ":" + count);
            }
        }
    }

    public static void getFile(File file, AtomicInteger count) {
        File[] listFile = file.listFiles();
        for (int i = 0; i < listFile.length; i++) {
            File fileTmp = listFile[i];
            if (!fileTmp.isDirectory()) {
                if (fileTmp.getName().endsWith(".java")) count.incrementAndGet();
            } else {
                getFile(fileTmp, count);
            }
        }
    }
}  