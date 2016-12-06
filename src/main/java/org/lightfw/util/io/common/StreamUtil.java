package org.lightfw.util.io.common;

import lombok.extern.log4j.Log4j2;

import java.io.*;

/**
 * 流工具类
 *
 * @author Wangxm
 */
@Log4j2
public class StreamUtil {

    /**
     * 通用的关闭方法
     *
     * @param closeables
     */
    public static void close(final Closeable... closeables) {
        closeQuietly(closeables);
    }


    /**
     * 通用的关闭方法
     *
     * @param closeables
     */
    public static void closeQuietly(final Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                if (null != closeable) {
                    closeable.close();
                }
            } catch (Throwable e) {
                log.error("fail to close.", e);
            }
        }
    }

    /**
     * Read an input stream into a string
     */
    public static String streamToString(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    /**
     * Read an input stream into a byte[]
     */
    public static byte[] stream2Byte(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = is.read(b, 0, b.length)) != -1) {
            baos.write(b, 0, len);
        }
        byte[] buffer = baos.toByteArray();
        return buffer;
    }

    /**
     * @方法功能 InputStream 转为 byte
     */
    public static byte[] inputStream2Byte(InputStream inStream) throws Exception {
        int count = 0;
        while (count == 0) {
            count = inStream.available();
        }
        byte[] b = new byte[count];
        inStream.read(b);
        return b;
    }

    /**
     * @return InputStream
     * @throws Exception
     * @方法功能 byte 转为 InputStream
     */
    public static InputStream byte2InputStream(byte[] b) throws Exception {
        InputStream is = new ByteArrayInputStream(b);
        return is;
    }

    /**
     * 将流另存为文件
     */
    public static void streamSaveAsFile(InputStream is, File outfile) {
        try (FileOutputStream fos = new FileOutputStream(outfile)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
