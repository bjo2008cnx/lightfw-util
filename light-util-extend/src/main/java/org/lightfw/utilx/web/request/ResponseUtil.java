package org.lightfw.utilx.web.request;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * ResponseUtil
 *
 * @author Michael.Wang
 * @date 2017/10/31
 */
public class ResponseUtil {
    /**
     * 回写ajax形式的响应
     *
     * @param response
     * @param content
     */
    public static void writeAjaxResponse(ServletResponse response, Map<String, Object> content, String encoding) {
        try {
            JSONObject json = new JSONObject();
            json.putAll(content);
            response.setContentType("application/json;charset=" + encoding);
            response.getWriter().write(json.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException("fail to write ajax response", e);
        }
    }
}