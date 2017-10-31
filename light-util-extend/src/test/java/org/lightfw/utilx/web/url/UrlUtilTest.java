package org.lightfw.utilx.web.url;

import org.junit.Test;
import org.lightfw.utilx.web.UrlUtil;

import java.util.Map;
import java.util.Set;

/**
 * Created by lenovo on 2017/10/27.
 */
public class UrlUtilTest {
    @Test
    public void parseQuery() throws Exception {
        String url = "q=test&hl=zh_cn&oq=test&gs_l=heirloom-serp";
        Map<String, String> map = UrlUtil.parseQuery(url, '&', '=', null);
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry<String, String> entry : set) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}