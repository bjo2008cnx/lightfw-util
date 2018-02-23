package org.lightfw.tool;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.lightfw.util.date.DateFormat;
import org.lightfw.util.date.DateUtil;
import org.lightfw.util.io.common.FileExtUtil;
import org.lightfw.util.lang.RandomUtil;

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
    public static void main(String[] args) {
        String path = "E:\\00-baiduclouds\\t";
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
        String dateTime = null;
        try {
            dateTime = findTakePhotoTime(oldFile, dateTime);
        } catch (ImageProcessingException | IOException e) {
            //取创建时间
            Date createTime = FileExtUtil.getCreateTime(fullPath);
            String createTimeStr = DateUtil.getDate(createTime, DateFormat.YYYYMMDDHHMMSS);
            dateTime = createTimeStr + RandomUtil.randomString(3, true);
        }

        String newName = dateTime + "_" + oldName;
        File newFile = new File(path + "/" + newName);
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