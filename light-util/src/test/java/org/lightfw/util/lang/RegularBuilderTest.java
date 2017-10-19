package org.lightfw.util.lang;

import org.junit.Test;

/**
 * Created by lenovo on 2017/10/19.
 */
public class RegularBuilderTest {
    @Test
    public void build() throws Exception {
        RegularUtil regular = new RegularBuilder().startOfLine().then("http").maybe("s").then("://").maybe("www.").anythingBut(" ").endOfLine().build();
        System.out.println(regular.toString());
        regular = new RegularBuilder().startOfLine().then("http").maybe("s").then("://").maybe("www.").anythingBut(" ").endOfLine().build();
        System.out.println(regular.toString());

    }
}