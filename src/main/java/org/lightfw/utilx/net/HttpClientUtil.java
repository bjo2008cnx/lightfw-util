package org.lightfw.utilx.net;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * HttpClientUtil
 *
 * @author Michael.Wang
 * @date 2017/7/25
 */
public class HttpClientUtil {

    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String ENCODING = "utf-8";

    public static String sendGetRequest(String url) {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        String result = null;
        try {
            // 发送请求
            HttpResponse httpResponse = client.execute(request);
            result = readResponse(result, httpResponse);
        } catch (Exception e) {
            throw (e instanceof RuntimeException) ? (RuntimeException) e : new RuntimeException(e);
        }
        return result;
    }

    /**
     * 发送HttpPost Json请求
     *
     * @param url
     * @param json
     * @return
     */
    public static String sendPostRequest(String url, String json) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", JSON_CONTENT_TYPE);
        String result = null;
        try {
            StringEntity entity = new StringEntity(json, ENCODING);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, JSON_CONTENT_TYPE));
            post.setEntity(entity);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);
            result = readResponse(result, httpResponse);
        } catch (Exception e) {
            throw (e instanceof RuntimeException) ? (RuntimeException) e : new RuntimeException(e);
        }
        return result;
    }

    private static String readResponse(String result, HttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, ENCODING));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            inStream.close();
            result = builder.toString();
        }
        return result;
    }
}