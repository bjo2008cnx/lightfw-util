package org.lightfw.tool;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JsonToObjectSource
 *
 * @author Michael.Wang
 * @date 2017/9/15
 */
public class JsonToObjectSource {

    /**
     * 用于驼峰转下划线
     */
    private static Pattern linePattern = Pattern.compile("_(\\w)");

    public static String toSource(String json) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> map = JsonUtil.fromJson(json, Map.class);
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            builder.append("private String ");
            builder.append(underLineToCamel(entry.getKey())).append(";\n");

        }
        return builder.toString();
    }


    /**
     * 下划线转驼峰
     */
    public static String underLineToCamel(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        String code = toSource(JSON_STR);
        System.out.println(code);
    }

    public static final String JSON_STR = "{\n" + "        \"id\": \"57092437\",\n" + "        \"aid\": \"0\",\n" + "        \"status\": \"0\",\n" + "       " +
            "" + " \"wid\": \"0\",\n" + "        \"username\": \"18017111018\",\n" + "        \"password\": \"25f9e794323b453885f5181f1b624d0b\",\n" + "     " +
            "   " + "\"name\": \"18017111018\",\n" + "        \"agent_id\": \"1339\",\n" + "        \"province\": \"浙江省\",\n" + "        \"city\": \"杭州市\"," +
            "\n" + "   " + "     \"country\": \"上城区\",\n" + "        \"address\": \"\",\n" + "        \"mobile\": \"18017111018\",\n" + "        \"email\": " +
            "\"abc@qq.com\"," + "\n" + "        \"qq\": \"123456\",\n" + "        \"pub_limit\": \"1\",\n" + "        \"copyright_hide\": \"0\",\n" + "      " +
            "  \"service_id\": " + "\"203\",\n" + "        \"pay_type\": \"\",\n" + "        \"postpone\": \"0\",\n" + "        \"lottery_amount\": \"0\",\n"
            + "        " + "\"lottery_balance\": \"0\",\n" + "        \"btime\": \"1482422400\",\n" + "        \"etime\": \"1514044740\",\n" + "        " +
            "\"is_bind\": \"0\"," + "\n" + "        \"is_locked\": \"0\",\n" + "        \"ltime\": \"0\",\n" + "        \"ctime\": \"1482463320\",\n" + "    " +
            "    \"flag\": \"0\",\n" + "        \"new_password\": \"f6c01d9bfc07c903979170b2561262f8\",\n" + "        \"is_change_new_password\": \"1\",\n" +
            "        " + "\"is_change_flag\": \"1\",\n" + "        \"level\": \"1\",\n" + "        \"store_num\": \"77\"\n" + "    }\n";

}