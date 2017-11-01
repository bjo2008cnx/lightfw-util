package org.lightfw.utilx.web.request;

import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.entity.ContentType;
import org.lightfw.utilx.text.transfer.JSONUtil;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * HttpServletResponse 工具类
 *
 * @author Michael.Wang
 * @date 2017/10/31
 */
public class ResponseUtil {
    public static final String UTF_8 = "UTF-8";

    // list字段如果为null，输出为[]，而不是null . 可选：SerializerFeature.PrettyFormat
    public static final SerializerFeature[] jsonFeatures = {SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty,};

    /**
     * 返回响应
     *
     * @param response
     * @param content
     * @throws IOException
     */
    public static void response(HttpServletResponse response, String content) {
        response(response, content, ContentType.APPLICATION_JSON);
    }

    public static void response(HttpServletResponse response, String data, ContentType contentType) {
        Charset charset = Charset.forName(UTF_8);
        contentType = contentType == null ? ContentType.TEXT_PLAIN : contentType;
        response.setContentType(contentType.withCharset(charset).toString());
        response.setContentLength(data.getBytes().length);
        try {
            response.getOutputStream().write(data.getBytes());
        } catch (IOException ignored) {
            //do nothing
        }
    }

    /**
     * 回写ajax形式的响应
     *
     * @param response
     * @param content
     */
    public static void writeAjaxResponse(ServletResponse response, Map<String, Object> content, String encoding) {
        try {
            response.setContentType("application/json;charset=" + encoding);
            response.setContentLength(content.size());
            response.getWriter().write(JSONUtil.toJSONString(content));
        } catch (IOException e) {
            throw new RuntimeException("fail to write ajax response", e);
        }
    }
}