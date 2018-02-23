package org.lightfw.tool;

import org.lightfw.util.date.DateFormat;
import org.lightfw.util.date.DateUtil;
import org.lightfw.util.io.common.FileExtUtil;
import org.lightfw.util.lang.RandomUtil;

import java.io.File;
import java.util.Date;

/**
 * 将相片名称重命名为创建时间
 *
 * @author Michael.Wang
 * @date 2018/2/23
 */
public class RenameTool {
    public static void main(String[] args) {
        String path = "E:\\00-baiduclouds\\tmp";
        renameAllInDir(path);
    }

    private static void renameAllInDir(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (File file : files) {
            renameFile(path, file.getName());
        }
    }

    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldName 原来的文件名
     */
    public static void renameFile(String path, String oldName) {
        String fullPath = path + "/" + oldName;
        File oldFile = new File(fullPath);
        Date createTime = FileExtUtil.getCreateTime(fullPath);
        String createTimeStr = DateUtil.getDate(createTime, DateFormat.YYYYMMDDHHMMSS);
        String dateTime = createTimeStr + RandomUtil.randomString(3, true);
        String newName = dateTime + "_" + oldName;
        File newFile = new File(path + "/" + newName);
        oldFile.renameTo(newFile);
    }
}