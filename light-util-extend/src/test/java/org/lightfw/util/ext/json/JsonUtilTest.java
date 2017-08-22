package org.lightfw.util.ext.json;

import org.junit.Test;
import org.lightfw.utilx.text.transfer.JsonUtil;

public class JsonUtilTest {

    private static final String RULES = "{'bills':" +
            "[{'bill':'order'," +
            "'rules':[{'type':'fixed','length':'2','value':'01'},{'type':'date','length':'6'},{'type':'seq','length':'9'},{'type':'random','length':'2'}]}," +
            "{'bill':'pay'," +
            "'rules':[{'type':'fixed','length':'2','value':'02'},{'type':'date','length':'6'},{'type':'seq','length':'10'},{'type':'random','length':'2'}]}" +
            "]}";

    private static final String pay = "";

    @Test
    public void testToJSONString() throws Exception {

    }

    @Test
    public void testFromJson() throws Exception {
        String str = RULES.replaceAll("'", "\"");
        BillNumRules json = JsonUtil.fromJson(str, BillNumRules.class);
        System.out.println(json);
    }
}