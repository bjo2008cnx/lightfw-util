package org.lightfw.tool;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.lightfw.util.date.DateFormat;
import org.lightfw.util.date.DateUtil;
import org.lightfw.util.io.common.FileExtUtil;
import org.lightfw.util.io.common.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * 将相片名称重命名为创建时间
 *
 * @author Michael.Wang
 * @date 2018/2/23
 */
public class RenameTool {
    static String path = "E:\\00-baiduclouds\\DCIM\\相片\\2017嘟嘟";
    static String pathNew = "E:\\00-baiduclouds\\DCIM\\相片\\2017嘟嘟";

    public static void main(String[] args) {
        try {
            File file = new File(pathNew);
            FileUtil.createParentDirs(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        renameAllInDir(path);
    }

    private static void renameAllInDir(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (File file : files) {
            try {
                copyFile(path, file.getName());
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldName 原来的文件名
     */
    public static void copyFile(String path, String oldName) throws IOException {
        String fullPath = path + "/" + oldName;
        File oldFile = new File(fullPath);
        String dateTime = null;
        try {
            dateTime = findTakePhotoTime(oldFile, dateTime);
        } catch (Exception e) {
        }
        if (dateTime == null) {
            //取创建时间
            Date createTime = FileExtUtil.getCreateTime(fullPath);
            String createTimeStr = DateUtil.getDate(createTime, DateFormat.YYYYMMDDHHMMSS);
            dateTime = createTimeStr;
        }

        String newName = dateTime == null ? oldName : dateTime + "_" + oldName;
        File newFile = new File(pathNew + "/" + newName);
        FileUtil.copy(oldFile, newFile, true);
        //oldFile.renameTo(newFile);
    }

    private static String findTakePhotoTime(File oldFile, String dateTime) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(oldFile);
        Iterator<Directory> it = metadata.getDirectories().iterator();
        while (it.hasNext()) {
            Directory exif = it.next();
            Iterator<Tag> tags = exif.getTags().iterator();
            while (tags.hasNext()) {
                Tag tag = tags.next();
                if ("Date/Time Original".equals(tag.getTagName())) {
                    //找到拍摄日期并格式化
                    dateTime = tag.getDescription().replaceAll(":", "").replaceAll(" ", "");
                    break;
                }
            }
            if (dateTime != null) break;
        }
        return dateTime;
    }
}