package org.lightfw.utilx.web;

import junit.framework.TestCase;
import org.junit.Test;
import org.lightfw.util.text.CharsetUtil;

import java.io.UnsupportedEncodingException;


public class HtmlUtilTest extends TestCase {

    @Test
    public void testhtml() {
        String html = "<div class=\"ipaddress\">服务器IP地址：</div>";
        System.out.println(HtmlUtil.unhtml(html));
        assertEquals(html, HtmlUtil.html(HtmlUtil.unhtml(html)));
    }

    @Test
    public void testescapeEncoding() throws UnsupportedEncodingException {
        String unescape = "<div class=\"ipaddress\">服务器IP地址：</div>";
        String gbk = "%3C%64%69%76%20%63%6C%61%73%73%3D%22%69%70%61%64%64%72%65%73" +
                "%73%22%3E%B7%FE%CE%F1%C6%F7%49%50%B5%D8%D6%B7%A3%BA%3C%2F%64%69%76%3E";
        String utf8 = "%3C%64%69%76%20%63%6C%61%73%73%3D%22%69%70%61%64%64%72%65%73%" +
                "73%22%3E%E6%9C%8D%E5%8A%A1%E5%99%A8%49%50%E5%9C%B0%E5%9D%80%EF%BC" +
                "%9A%3C%2F%64%69%76%3E";
        //GBK
        assertEquals(gbk, HtmlUtil.escape(unescape, CharsetUtil.GBK));

        //utf8
        assertEquals(utf8, HtmlUtil.escape(unescape, CharsetUtil.UTF_8));
    }
}