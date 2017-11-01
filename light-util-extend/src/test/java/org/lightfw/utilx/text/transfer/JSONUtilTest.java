package org.lightfw.utilx.text.transfer;

import org.junit.Assert;
import org.junit.Test;
import org.lightfw.util.collection.MapWriteUtil;

import java.util.Map;

public class JSONUtilTest {
    @Test
    public void toJSONString() throws Exception {
        Map map = MapWriteUtil.createHashMap(1, 1, 2, 2);
        String json = JSONUtil.toJSONString(map);
        Assert.assertEquals("{1:1,2:2\n" + "}",json);
    }

    private static final String RULES = "{'bills':" +
            "[{'bill':'order'," +
            "'rules':[{'type':'fixed','length':'2','value':'01'},{'type':'date','length':'6'},{'type':'seq','length':'9'},{'type':'random','length':'2'}]}," +
            "{'bill':'pay'," +
            "'rules':[{'type':'fixed','length':'2','value':'02'},{'type':'date','length':'6'},{'type':'seq','length':'10'},{'type':'random','length':'2'}]}" +
            "]}";

    private static final String pay = "";

    @Test
    public void testFromJson() throws Exception {
        String str = RULES.replaceAll("'", "\"");
        BillNumRules json = JSONUtil.fromJson(str, BillNumRules.class);
        System.out.println(json);
    }
}