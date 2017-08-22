package org.lightfw.utilx.net;

import org.junit.Test;

/**
 * Created by lenovo on 2017/7/25.
 */
public class HttpClientUtilTest {
    @Test
    public void sendGetRequest() throws Exception {
        String response = HttpClientUtil.sendGetRequest("http://www.baidu.com");
        assert response.length()>0;
        System.out.println(response);
    }

    @Test
    public void sendPostRequest() throws Exception {

    }

}