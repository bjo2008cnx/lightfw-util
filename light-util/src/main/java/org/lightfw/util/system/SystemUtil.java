package org.lightfw.util.system;

import org.lightfw.util.lang.StringUtil;

import java.io.IOException;

public class SystemUtil {

    /**
     * 获取系统默认字符集
     *
     * @return
     */
    public static String getSystemEncoding() {
        return System.getProperty("file.encoding");
    }

    /**
     * 从系统属性文件中获取相应的值
     *
     * @param key key
     * @return 返回value
     */
    public static String getSystemKey(String key) {
        return System.getProperty(key);
    }

    /**
     * 运行本地可执行文件
     *
     * @param command
     */
    public static void exec(String command) {
        try {
            //执行.bat 或.exe文件
            Process child = Runtime.getRuntime().exec(command);

            //获取执行结果
            int c;
            StringBuffer sb = new StringBuffer();
            while ((c = child.getInputStream().read()) != -1) {
                sb.append((char) c);
            }
            //关闭输入流
            child.getInputStream().close();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * 得到调用栈信息
     *
     * @param rows 最长多少行
     * @return
     */
    public static String getStackTrace(int rows) {
        StringBuilder result = new StringBuilder();
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        for (int i = 0; i < sts.length; i++) {
            if (i == 0 && Thread.class.getName().equals(sts[i].getClassName()) && "getStackTrace".equals(sts[i].getMethodName())) {
                continue;
            }
            if (i == 1 && StringUtil.class.getName().equals(sts[i].getClassName()) && "getStackTrace".equals(sts[i].getMethodName())) {
                continue;
            }
            if (result.length() > 0) {
                result.append("\n");
            }
            if (rows > 0 && i > rows) {
                result.append("......");
                break;
            }
            result.append(sts[i]);
        }
        return result.toString();
    }

}
