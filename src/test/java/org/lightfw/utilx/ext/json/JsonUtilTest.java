package org.lightfw.utilx.ext.json;

import org.junit.Test;
import org.lightfw.utilx.ext.JsonUtil;

public class JsonUtilTest {

    private static final String RULES = "{'bill':'order','rules':[{'type':'fixed','length':'2','value':'01'},{'type':'date','length':'6'},{'type':'seq'," +
            "'length':'9'}," +
            "{'type':'random','length':'2'}]}";

    private static final String PAY = "[{'TYPE':'FIXED','LENGTH':'2','VALUE':'02'},{'TYPE':'DATE','LENGTH':'6'},{'TYPE':'SEQ','LENGTH':'10'}," +
            "{'TYPE':'RANDOM','LENGTH':'2'}]";

    @Test
    public void testToJSONString() throws Exception {

    }

    @Test
    public void testFromJson() throws Exception {
        String str = RULES.replaceAll("'","\"");
        BillNumRule json = JsonUtil.fromJson(str, BillNumRule.class);
        System.out.println(json);
    }
}