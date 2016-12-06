package org.lightfw.util.io.common;

import com.google.common.io.Files;
import org.lightfw.util.ext.dynamic.RegUtil;

import java.io.File;

/**
 * FilePathUtil
 *
 * @author Wangxm
 * @date 2016/5/10
 */
public class FilePathUtil {

    private static final String FOLDER_SEPARATOR = "/";
    private static final char EXTENSION_SEPARATOR = '.';

    /**
     * 从文件路径中抽取文件名, 例如： "mypath/myfile.txt" -> "myfile.txt"。 *
     *
     * @param path 文件路径。
     * @return 抽取出来的文件名, 如果path为null，直接返回null。
     */
    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }

    /**
     * 从文件路径中抽取文件的扩展名, 例如. "mypath/myfile.txt" -> "txt". *
     *
     * @param path 文件路径
     * @return 如果path为null，直接返回null。
     */
    public static String getFilenameExtension(String path) {
        if (path == null) {
            return null;
        }
        int extIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
        if (extIndex == -1) {
            return null;
        }
        int folderIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        if (folderIndex > extIndex) {
            return null;
        }
        return path.substring(extIndex + 1);
    }


    /**
     * 返回去除了扩展名的文件名
     *
     * @param file
     * @return
     */
    public static String getNameWithoutExtension(String file) {
        return Files.getNameWithoutExtension(file);
    }

    /**
     * 默认的临时文件夹
     */
    public static File defaultTempDir() {
        return new File(System.getProperty("java.io.tmpdir") + File.separator + "javas");
    }

    /**
     * 判断是否符是合法的文件路径
     *
     * @param path 需要处理的文件路径
     */
    public static boolean legalFile(String path) {
        //下面的正则表达式有问题
        String regex = "[a-zA-Z]:(?:[/][^/:*?\"<>|.][^/:*?\"<>|]{0,254})+";
        //String regex ="^([a-zA-z]:)|(^\\.{0,2}/)|(^\\w*)\\w([^:?*\"><|]){0,250}";
        return RegUtil.isMatche(commandPath(path), regex);
    }

    /**
     * 返回一个通用的文件路径
     *
     * @param file 需要处理的文件路径
     * @return Summary windows中路径分隔符是\在linux中是/但windows也支持/方式 故全部使用/
     */
    public static String commandPath(String file) {
        return file.replaceAll("\\\\{1,}", "/").replaceAll("\\/{2,}", "/");
    }
}
