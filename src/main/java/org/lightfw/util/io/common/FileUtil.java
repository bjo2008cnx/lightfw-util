package org.lightfw.util.io.common;

import com.google.common.base.Charsets;
import com.google.common.collect.TreeTraverser;
import com.google.common.io.Files;
import lombok.extern.log4j.Log4j2;
import org.lightfw.util.ext.dynamic.RegUtil;
import org.lightfw.util.ext.io.FileTypeImpl;
import org.lightfw.util.lang.ExceptionUtil;
import org.lightfw.util.validate.Valid;
import org.lightfw.util.sercurity.encrypt.Md5Util;
import org.lightfw.utilx.detector.EncodingDetectUtil;

import java.io.*;
import java.net.FileNameMap;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
public class FileUtil {

    /**
     * Buffer的大小
     */
    private static Integer BUFFER_SIZE = 1024 * 1024 * 10;

    /**
     * 写文件，采用UTF8字符集
     *
     * @param file
     * @param content
     */
    public static void write(File file, String content, boolean isAppend) {
        createParentDirs(file);
        write(file, content, isAppend, Charsets.UTF_8);
    }


    /**
     * 写文件
     *
     * @param file    文件对象
     * @param content 内容
     * @isAppend 是否追加
     */
    public static void write(File file, String content, boolean isAppend, Charset charset) {
        try {
            FileUtil.createParentDirs(file);
            if (!isAppend) {
                Files.write(content, file, charset);
            } else {
                Files.append(content, file, charset);
            }
        } catch (IOException e) {
            log.error("Fail to copy file.", e);
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 功能：保存文件
     *
     * @param content 字节
     * @param file    保存到的文件
     * @throws IOException
     */
    public static void write(byte[] content, File file) throws IOException {
        FileUtil.createParentDirs(file);
        if (file == null) {
            throw new RuntimeException("保存文件不能为空");
        }
        if (content == null) {
            throw new RuntimeException("文件流不能为空");
        }
        InputStream is = new ByteArrayInputStream(content);
        write(is, file);
    }

    /**
     * 功能：保存文件
     *
     * @param streamIn 文件流
     * @param file     保存到的文件
     * @throws IOException
     */
    public static void write(InputStream streamIn, File file) throws IOException {
        createParentDirs(file);
        OutputStream streamOut = null;
        try {
            if (file == null) {
                throw new RuntimeException("保存文件不能为空");
            }
            if (streamIn == null) {
                throw new RuntimeException("文件流不能为空");
            }
            // 输出流.文件夹不存在就创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            streamOut = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
                streamOut.write(buffer, 0, bytesRead);
            }
        } finally {
            StreamUtil.close(streamOut);
            StreamUtil.close(streamIn);
        }
    }

    /**
     * 读取全部行，若失败记录log
     *
     * @param file
     * @throws IOException
     */
    public static List<String> readAllLines(File file) {
        try {
            return Files.readLines(file, Charsets.UTF_8);
        } catch (IOException e) {
            log.error("Fail to copy file.", e);
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 读取全部行，若失败记录log
     *
     * @param file
     * @param charset
     * @throws IOException
     */
    public static List<String> readAllLines(File file, Charset charset) {
        try {
            return Files.readLines(file, charset);
        } catch (IOException e) {
            log.error("Fail to copy file.", e);
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 读取全部行，若失败记录log
     *
     * @param file
     * @param charset
     * @throws IOException
     */
    public static List<String> readFirstLine(File file, Charset charset) {
        try {
            return Files.readLines(file, charset);
        } catch (IOException e) {
            log.error("Fail to copy file.", e);
            throw ExceptionUtil.transform(e);
        }
    }


    /**
     * 功能：复制文件或者文件夹。
     *
     * @param inputFile   源文件
     * @param outputFile  目的文件
     * @param isOverWrite 是否覆盖(只针对文件)
     * @throws IOException
     */
    public static void copy(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
        if (!inputFile.exists()) {
            throw new RuntimeException(inputFile.getPath() + "源目录不存在!");
        }
        copyPri(inputFile, outputFile, isOverWrite);
    }

    /**
     * 拷贝文件，若失败记录log
     *
     * @param original
     * @param copy
     * @throws IOException
     */
    public static void copy(File original, File copy) {
        try {
            Files.copy(original, copy);
        } catch (IOException e) {
            log.error("Fail to copy file.", e);
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 移动文件，若失败记录log
     *
     * @param original
     * @param copy
     * @throws IOException
     */
    public static void move(File original, File copy) {
        try {
            Files.move(original, copy);
        } catch (IOException e) {
            log.error("Fail to copy file.", e);
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 功能：为copy 做递归使用。
     *
     * @param inputFile
     * @param outputFile
     * @param isOverWrite
     * @throws IOException
     */
    private static void copyPri(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
        if (inputFile.isFile()) {
            copySingleFile(inputFile, outputFile, isOverWrite);
        } else {
            // 文件夹
            if (!outputFile.exists()) {
                outputFile.mkdir();
            }
            // 循环子文件夹
            for (File child : inputFile.listFiles()) {
                copy(child, new File(outputFile.getPath() + "/" + child.getName()), isOverWrite);
            }
        }
    }

    /**
     * 功能：copy单个文件
     *
     * @param inputFile   源文件
     * @param outputFile  目标文件
     * @param isOverWrite 是否允许覆盖
     * @throws IOException
     */
    private static void copySingleFile(File inputFile, File outputFile, boolean isOverWrite) throws IOException {
        // 目标文件已经存在
        InputStream in = null;
        OutputStream out = null;
        try {
            if (outputFile.exists()) {
                if (isOverWrite) {
                    if (!outputFile.delete()) {
                        throw new RuntimeException(outputFile.getPath() + "无法覆盖！");
                    }
                } else {
                    // 不允许覆盖
                    return;
                }
            }
            in = new FileInputStream(inputFile);
            out = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } finally {
            StreamUtil.close(in);
            StreamUtil.close(out);
        }
    }

    /**
     * 功能：删除文件
     *
     * @param file 文件
     */
    public static void delete(File file) {
        deleteFile(file);
    }

    /**
     * 功能：删除文件，内部递归使用
     *
     * @param file 文件
     * @return boolean true 删除成功，false 删除失败。
     */
    private static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        // 单文件
        if (!file.isDirectory()) {
            boolean delFlag = file.delete();
            if (!delFlag) {
                throw new RuntimeException(file.getPath() + "删除失败！");
            } else {
                return;
            }
        }
        // 删除子目录
        for (File child : file.listFiles()) {
            deleteFile(child);
        }
        // 删除自己
        file.delete();
    }


    /**
     * 为文件创建父目录
     *
     * @param file
     */
    public static void createParentDirs(File file) {
        try {
            Files.createParentDirs(file);
        } catch (IOException e) {
            throw ExceptionUtil.transform(e);
        }
    }

    /**
     * 返回TreeTraverser用于遍历文件树
     *
     * @param file
     */
    public static TreeTraverser<File> getFileTree(File file) {
        return Files.fileTreeTraverser();
    }


    /**
     * 获取文件的Mime类型
     *
     * @param file 需要处理的文件
     * @return 返回文件的mime类型
     * @throws java.io.IOException
     */
    public static String mimeType(String file) throws java.io.IOException {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        return fileNameMap.getContentTypeFor(file);
    }

    /**
     * 获取文件的类型
     * <p/>
     * Summary:只利用文件头做判断故不全
     *
     * @param file 需要处理的文件
     * @return 文件类型
     */
    public static String fileType(File file) {
        return FileTypeImpl.getFileType(file);
    }

    /**
     * 获取文件最后的修改时间
     *
     * @param file 需要处理的文件
     * @return 返回文件的修改时间
     */
    public static Date modifyTime(File file) {
        return new Date(file.lastModified());
    }

    /**
     * 获取文件的Hash
     *
     * @param file 需要处理的文件
     * @return 返回文件的hash值
     */
    public static String hash(File file) {
        return Md5Util.getFileMD5String(file);
    }


    /**
     * 复制文件
     *
     * @param resourcePath 源文件
     * @param targetPath   目标文件
     * @return 是否成功
     */
    public static boolean copy(String resourcePath, String targetPath) {
        File file = new File(resourcePath);
        return copy(file, targetPath);
    }

    /**
     * 复制文件
     * 通过该方式复制文件文件越大速度越是明显
     *
     * @param file       需要处理的文件
     * @param targetFile 目标文件
     * @return 是否成功
     */
    public static boolean copy(File file, String targetFile) {
        try (
                FileInputStream fin = new FileInputStream(file);
                FileOutputStream fout = new FileOutputStream(new File(targetFile))
        ) {
            FileChannel in = fin.getChannel();
            FileChannel out = fout.getChannel();
            //设定缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (in.read(buffer) != -1) {
                //准备写入，防止其他读取，锁住文件
                buffer.flip();
                out.write(buffer);
                //准备读取。将缓冲区清理完毕，移动文件内部指针
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 利用简单的文件头字节特征探测文件编码
     *
     * @param file 需要处理的文件
     * @return UTF-8 Unicode UTF-16BE GBK
     */
    public static String simpleEncoding(String file) {
        try {
            return EncodingDetectUtil.detectEncoding(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建多级目录
     *
     * @param paths 需要创建的目录
     * @return 是否成功
     */
    public static boolean createPaths(String paths) {
        File dir = new File(paths);
        return !dir.exists() && dir.mkdir();
    }

    /**
     * 创建文件支持多级目录
     *
     * @param filePath 需要创建的文件
     * @return 是否成功
     */
    public static boolean createFiles(String filePath) {
        File file = new File(filePath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 删除一个文件
     *
     * @param file 需要处理的文件
     * @return 是否成功
     */
    public static boolean deleteSingleFile(File file) {
        return file.delete();
    }

    /**
     * 删除一个目录
     *
     * @param file 需要处理的文件
     * @return 是否成功
     */
    public static boolean deleteDir(File file) {
        List<File> files = listFileAll(file);
        if (Valid.valid(files)) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteDir(f);
                } else {
                    deleteFile(f);
                }
            }
        }
        return file.delete();
    }


    /**
     * 快速的删除超大的文件
     *
     * @param file 需要处理的文件
     * @return 是否成功
     */
    public static boolean deleteBigFile(File file) {
        return cleanFile(file) && file.delete();
    }


    /**
     * 复制目录
     *
     * @param filePath   需要处理的文件
     * @param targetPath 目标文件
     */
    public static void copyDir(String filePath, String targetPath) {
        File file = new File(filePath);
        copyDir(file, targetPath);
    }

    /**
     * 复制目录
     *
     * @param filePath   需要处理的文件
     * @param targetPath 目标文件
     */
    public static void copyDir(File filePath, String targetPath) {
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {
            createPaths(targetPath);
        }
        File[] files = filePath.listFiles();
        if (Valid.valid(files)) {
            for (File file : files) {
                String path = file.getName();
                if (file.isDirectory()) {
                    copyDir(file, targetPath + "/" + path);
                } else {
                    copy(file, targetPath + "/" + path);
                }
            }
        }
    }

    /**
     * 罗列指定路径下的全部文件
     *
     * @param path 需要处理的文件
     * @return 包含所有文件的的list
     */
    public static List<File> listFile(String path) {
        File file = new File(path);
        return listFile(file);
    }

    /**
     * 罗列指定路径下的全部文件
     *
     * @param path  需要处理的文件
     * @param child 是否罗列子文件
     * @return 包含所有文件的的list
     */
    public static List<File> listFile(String path, boolean child) {
        return listFile(new File(path), child);
    }


    /**
     * 罗列指定路径下的全部文件
     *
     * @param path 需要处理的文件
     * @return 返回文件列表
     */
    public static List<File> listFile(File path) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (Valid.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFile(file));
                } else {
                    list.add(file);
                }
            }
        }
        return list;
    }

    /**
     * 罗列指定路径下的全部文件
     *
     * @param path  指定的路径
     * @param child 是否罗列子目录
     * @return
     */
    public static List<File> listFile(File path, boolean child) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (Valid.valid(files)) {
            for (File file : files) {
                if (child && file.isDirectory()) {
                    list.addAll(listFile(file));
                } else {
                    list.add(file);
                }
            }
        }
        return list;
    }

    /**
     * 罗列指定路径下的全部文件包括文件夹
     *
     * @param path 需要处理的文件
     * @return 返回文件列表
     */
    public static List<File> listFileAll(File path) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (Valid.valid(files)) {
            for (File file : files) {
                list.add(file);
                if (file.isDirectory()) {
                    list.addAll(listFileAll(file));
                }
            }
        }
        return list;
    }

    /**
     * 罗列指定路径下的全部文件包括文件夹
     *
     * @param path   需要处理的文件
     * @param filter 处理文件的filter
     * @return 返回文件列表
     */
    public static List<File> listFileFilter(File path, FilenameFilter filter) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (Valid.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFileFilter(file, filter));
                } else {
                    if (filter.accept(file.getParentFile(), file.getName())) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取指定目录下的特点文件,通过后缀名过滤
     *
     * @param dirPath  需要处理的文件
     * @param postfixs 文件后缀
     * @return 返回文件列表
     */
    public static List<File> listFileFilter(File dirPath, final String postfixs) {
        /*
        如果在当前目录中使用Filter讲只罗列当前目录下的文件不会罗列孙子目录下的文件
        FilenameFilter filefilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(postfixs);
            }
        };
        */
        List<File> list = new ArrayList<File>();
        File[] files = dirPath.listFiles();
        if (Valid.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFileFilter(file, postfixs));
                } else {
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(postfixs.toLowerCase())) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 在指定的目录下搜寻文个文件
     *
     * @param dirPath  搜索的目录
     * @param fileName 搜索的文件名
     * @return 返回文件列表
     */
    public static List<File> searchFile(File dirPath, String fileName) {
        List<File> list = new ArrayList<>();
        File[] files = dirPath.listFiles();
        if (Valid.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(searchFile(file, fileName));
                } else {
                    String Name = file.getName();
                    if (Name.equals(fileName)) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 查找符合正则表达式reg的的文件
     *
     * @param dirPath 搜索的目录
     * @param reg     正则表达式
     * @return 返回文件列表
     */
    public static List<File> searchFileReg(File dirPath, String reg) {
        List<File> list = new ArrayList<>();
        File[] files = dirPath.listFiles();
        if (Valid.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(searchFile(file, reg));
                } else {
                    String Name = file.getName();
                    if (RegUtil.isMatche(Name, reg)) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }


    /**
     * 获取文件后缀名
     *
     * @param file
     * @return
     */
    public static String suffix(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.indexOf(".") + 1);
    }

    /**
     * 快速清空一个超大的文件
     *
     * @param file 需要处理的文件
     * @return 是否成功
     */
    public static boolean cleanFile(File file) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从ClassPath取文件
     *
     * @return
     */
    public static File getClasspathFile(String classPathFileName) {
        try {
            URL resource = FileUtil.class.getClassLoader().getResource(classPathFileName);
            String filePath = resource.getPath();
            File file = new File(filePath);
            log.debug("获取类路径下的文件路径：" + filePath);
            return file;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
