package org.lightfw.util.ext.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

/**
 * 文件拆分工具:给定文件的路径和每一块要拆分的大小，就可以按要求拆分文件
 * 如果指定的块比原文件还要大，为了不动原文件，就生成另一个文件，以.bak为后缀，这样可以保证原文件
 * 如果是程序自动拆分为多个文件，那么后缀分别为".part序号"，这样就可以方便文件的合并了 原理：很简单，就是利用是输入输出流，加上随机文件读取。
 */
public class FileSplitUtil {

    public final static String STR_SEPARATE = "_separate";

    private String originFileName = null;// 原文件名

    private long originFileSize = 0;// 原文件的大小

    private long blockNum = 0;// 可分的块数

    public static File split(String file, long blockSize) {
        FileSplitUtil separator = new FileSplitUtil();
        File newFolder = new File(file + STR_SEPARATE + File.separator + new File(file).getName());
        newFolder.getParentFile().mkdirs();
        if (separator.split(file, blockSize, newFolder.toString())) {
            return new File(newFolder.toString()).getParentFile();
        } else {
            return null;
        }
    }

    /**
     * 取得原文件的属性
     *
     * @param fileAndPath 原文件名及路径
     */
    private void getFileAttribute(String fileAndPath) {
        File file = new File(fileAndPath);
        originFileName = file.getName();
        originFileSize = file.length();
    }

    /**
     * 取得分块数
     *
     * @param blockSize 每一块的大小
     * @return 能够分得的块数
     */
    private long getBlockNum(long blockSize) {
        long fileSize = originFileSize;
        if (fileSize <= blockSize) {// 如果分块的小小只够分一个块
            return 1;
        } else {
            if (fileSize % blockSize > 0) {
                return fileSize / blockSize + 1;
            } else {
                return fileSize / blockSize;
            }
        }
    }

    /**
     * 生成折分后的文件名，以便于将来合将
     *
     * @param fileAndPath  原文件及完整路径
     * @param currentBlock 当前块的序号
     * @return 现在拆分后块的文件名
     */
    private String generateSplitFileName(String fileAndPath, int currentBlock) {
        return fileAndPath + ".part" + currentBlock;
    }

    /**
     * 往硬盘写文件
     *
     * @param fileAndPath      原文件及完整路径
     * @param fileSeparateName 文件分隔后要生成的文件名，与原文件在同一个目录下
     * @param blockSize        当前块要写的字节数
     * @param beginPos         从原文件的什么地方开始读取
     * @return true为写入成功，false为写入失败
     */
    private boolean writeFile(String fileAndPath, String fileSeparateName, long blockSize, long beginPos) {
        RandomAccessFile raf = null;
        FileOutputStream fos = null;
        byte[] bt = new byte[1024];
        long writeByte = 0;
        int len = 0;
        try {
            raf = new RandomAccessFile(fileAndPath, "r");
            raf.seek(beginPos);
            fos = new FileOutputStream(fileSeparateName);
            while ((len = raf.read(bt)) > 0) {
                // 如果当前块还没有写满
                if (writeByte < blockSize) {
                    writeByte = writeByte + len;
                    if (writeByte <= blockSize) {
                        fos.write(bt, 0, len);
                    } else {
                        len = len - (int) (writeByte - blockSize);
                        fos.write(bt, 0, len);
                    }
                }
            }
            fos.close();
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (fos != null)
                    fos.close();
                if (raf != null)
                    raf.close();
            } catch (Exception f) {
                f.printStackTrace();
            }
            return false;
        }
        return true;
    }

    /**
     * 折分文件主函数
     *
     * @param fileAndPath 原文路径及文件名
     * @param blockSize   要拆分的每一块的大小
     * @return true为拆分成功，false为拆分失败
     */
    private boolean split(String fileAndPath, long blockSize, String newFolder) {
        getFileAttribute(fileAndPath);// 将文件的名及大小属性取出来
        blockNum = getBlockNum(blockSize);// 取得分块总数
        if (blockNum == 1) {// 如果只能够分一块，就一次性写入
            blockSize = originFileSize;
        }
        long writeSize = 0;// 每次写入的字节
        long writeTotal = 0;// 已经写了的字节
        String FileCurrentNameAndPath = null;
        for (int i = 1; i <= blockNum; i++) {
            if (i < blockNum) {
                writeSize = blockSize;// 取得每一次要写入的文件大小
            } else {
                writeSize = originFileSize - writeTotal;
            }
            if (blockNum == 1) {
                FileCurrentNameAndPath = fileAndPath + ".bak";
            } else {
                if (newFolder == null || newFolder.length() == 0) {
                    newFolder = fileAndPath;
                }
                FileCurrentNameAndPath = generateSplitFileName(newFolder, i);
            }
            if (!writeFile(fileAndPath, FileCurrentNameAndPath, writeSize, writeTotal)) {// 循环往硬盘写文件
                return false;
            }
            writeTotal = writeTotal + writeSize;
            // System.out.println(" 总共写入:"+writeTotal);
        }
        return true;
    }


}
