package org.lightfw.util.ext.io;

/**
 * 合并文件：合并由拆分文件拆分的文件
 * 要求将拆分文件放到一个文件夹中
 * 主要利用随机文件读取和文件输入输出流
 */

import org.lightfw.util.io.common.StreamUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FileJoinUtil {
    private String srcDirectory = null;// 拆分文件存放的目录

    private String[] separatedFiles;// 存放所有拆分文件名

    private String[][] separatedFilesAndSize;// 存放所有拆分文件名及分件大小

    private int fileNum = 0;// 确定文件个数

    private String fileRealName = "";// 据拆分文件名确定现在原文件名

    public FileJoinUtil(String srcDirectory) {
        this.srcDirectory = srcDirectory;
    }

    /**
     * 合并文件
     *
     * @param newFolder
     * @return
     */
    public static File join(String newFolder) {
        if (!newFolder.endsWith(File.separator)) {
            newFolder = newFolder + File.separator;
        }
        FileJoinUtil combination = new FileJoinUtil(newFolder);
        combination.getFileAttribute(combination.srcDirectory);
        if (combination.join()) {
            return new File(newFolder + combination.fileRealName);
        } else {
            return null;
        }
    }

    /**
     * @param fileName 任一一个拆分文件名
     * @return 原文件名
     */
    private String getRealName(String fileName) {
        StringTokenizer st = new StringTokenizer(fileName, ".");
        return st.nextToken() + "." + st.nextToken();
    }

    /**
     * 取得指定拆分文件模块的文件大小
     *
     * @param fileName 拆分的文件名
     * @return
     */
    private long getFileSize(String fileName) {
        fileName = srcDirectory + fileName;
        return (new File(fileName).length());
    }

    /**
     * 生成一些属性，做初使化
     *
     * @param dir 拆分文件目录
     */
    private void getFileAttribute(String dir) {
        File file = new File(dir);
        separatedFiles = new String[file.list().length];// 依文件数目动态生成一维数组，只有文件名
        separatedFiles = file.list();
        // 依文件数目动态生成二维数组，包括文件名和文件大小
        // 第一维装文件名，第二维为该文件的字节大小
        separatedFilesAndSize = new String[separatedFiles.length][2];
        Arrays.sort(separatedFiles);// 排序
        fileNum = separatedFiles.length;// 当前文件夹下面有多少个文件
        for (int i = 0; i < fileNum; i++) {
            separatedFilesAndSize[i][0] = separatedFiles[i];// 文件名
            separatedFilesAndSize[i][1] = String.valueOf(getFileSize(separatedFiles[i]));// 文件大上
        }
        fileRealName = getRealName(separatedFiles[fileNum - 1]);// 取得文件分隔前的原文件名
    }

    /**
     * 合并文件：利用随机文件读写
     *
     * @return true为成功合并文件
     */
    private boolean join() {
        RandomAccessFile raf = null;
        long alreadyWrite = 0;
        FileInputStream fis = null;
        int len;
        byte[] bt = new byte[1024];
        try {
            raf = new RandomAccessFile(srcDirectory + fileRealName, "rw");
            for (int i = 0; i < fileNum; i++) {
                raf.seek(alreadyWrite);
                fis = new FileInputStream(srcDirectory + separatedFilesAndSize[i][0]);
                while ((len = fis.read(bt)) > 0) {
                    raf.write(bt, 0, len);
                }
                fis.close();
                alreadyWrite = alreadyWrite + Long.parseLong(separatedFilesAndSize[i][1]);
            }
            StreamUtil.close(raf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            StreamUtil.close(raf);
            StreamUtil.close(fis);
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(join("d:/tmp/12047731683169241855.zip" + FileSplitUtil.STR_SEPARATE));
    }
}