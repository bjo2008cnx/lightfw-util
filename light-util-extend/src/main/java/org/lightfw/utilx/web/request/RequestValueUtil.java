package org.lightfw.utilx.web.request;

import lombok.extern.log4j.Log4j2;
import org.lightfw.util.collection.MapWriteUtil;
import org.lightfw.util.lang.StringUtil;
import org.lightfw.utilx.text.transfer.JSONUtil;
import org.lightfw.utilx.web.UrlUtil;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * request工具类
 *
 * @author Miao
 */
@Log4j2
public class RequestValueUtil {
    /**
     * 解析请求的body 的value
     *
     * @param req
     * @return
     */
    public static String parseRequestValues(ServletRequest req) {
        //判断是否是包装过的request
        HttpServletRequest request = (req instanceof HttpServletRequest) ? (HttpServletRequest) req : null;
        if (request == null) {
            return null;
        }
        Map bodyMap = parseWrapperBody(req);
        Map jsonMap = request.getParameterMap();
        Map requestMap = parseQuery(request.getQueryString());
        Map all = MapWriteUtil.putAll(bodyMap, jsonMap, requestMap);

        String requestBody = joinValues(all);
        return requestBody;
    }

    /**
     * 解析 RepeatableReadWrapper 封装的body
     *
     * @param req
     * @return
     */
    private static Map parseWrapperBody(ServletRequest req) {
        Map bodyMap = null;
        try {
            if (req instanceof RepeatableReadWrapper) {
                String requestBody = ((RepeatableReadWrapper) req).getBody();
                bodyMap = JSONUtil.fromJson(requestBody, Map.class);
            }
        } catch (Throwable e) {
            log.error("fail to parse body", e);
        }
        return bodyMap;
    }

    /**
     * 解析 value,过滤之后以逗号分隔; 如果value是数组，将数组转换成以逗号分隔的字符串
     *
     * @param map
     * @return
     */
    public static String joinValues(Map<String, Object> map) {
        Iterator<String> names = map.keySet().iterator();
        StringBuilder builder = new StringBuilder();
        while (names.hasNext()) {
            String key = names.next().toString();
            Object value = map.get(key);
            Object joinedValue = (value instanceof String[]) ? StringUtil.join(',', (String[]) value) : value;
            builder.append(joinedValue).append(",");
        }
        return builder.toString();
    }

    /**
     * 解析字符串返回map键值对(例：a=1&b=2 => a=1,b=2)
     *
     * @param query
     * @return
     */
    public static Map<String, String> parseQuery(String query) {
        return UrlUtil.parseQuery(query, '&', '=', null);
    }
}
