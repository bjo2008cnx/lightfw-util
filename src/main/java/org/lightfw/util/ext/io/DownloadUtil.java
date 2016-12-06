package org.lightfw.util.ext.io;

import org.lightfw.util.lang.ExceptionUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUtil {
    /**
     * 功能: 下载urlPath到file中
     * 参考：Resources.asByteSource(url).copyTo(Files.asByteSink(file));
     *
     * @param urlPath
     * @param file
     * @return
     * @throws MalformedURLException
     */
    public static long downloadFileFromUrl(String urlPath, File file) {
        try {
            long size = 0;
            URL url = new URL(urlPath);
            HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
            BufferedInputStream bufferedinputstream = new BufferedInputStream(httpurlconnection.getInputStream());
            BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(file));
            int i;
            while ((i = bufferedinputstream.read()) != -1) {
                bufferedoutputstream.write(i);
            }
            bufferedinputstream.close();
            bufferedoutputstream.close();
            httpurlconnection.disconnect();
            size = file.length();
            return size;

        } catch (IOException e) {
            throw ExceptionUtil.transform(e);
        }

    }

}