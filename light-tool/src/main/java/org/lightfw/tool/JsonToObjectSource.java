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

    public static final String JSON_STR = "{\"sdptype\":\"0\",\"avatar_url\":\"http:\\/\\/wx.qlogo" +
            ".cn\\/mmopen\\/K99G1fbmow7QLY4BMfKjsloT8uFo89EnKA1aFQEseY4H3xc2E6d6jtr7A5AYIv3Z7XH4ia34sRr9jo7Azic6SesKvgga5JSnk5\\/0\",\"open_appid\":\"\"," +
            "\"appid\":\"wxa4f88e5b830110f8\",\"funs\":null,\"type\":null,\"city\":null,\"default_reply_flag\":\"0\",\"default_reply\":\"\"," +
            "\"template_set\":\"0\",\"microapp_set\":null,\"api_token\":\"456129_g\",\"home_style\":\"\",\"province\":null,\"account_type\":\"2\"," +
            "\"wx_authorizer_refresh_token\":\"refreshtoken@@@Wj5M-1U3dZwDsDjnc-TlOk_MXydj3sw4aqkX2RJNBtA\",\"plc_sourceid\":\"gh_b7a7f298969e\"," +
            "\"template_list\":\"0\",\"wechat_id\":\"\\u5c0f\\u9986\\u8ba2\\u9910\",\"ctime\":\"1491448744\",\"template_channel\":null,\"flag\":\"0\"," +
            "\"template_homemenu\":\"0\",\"is_news\":\"0\",\"email\":null,\"api_key\":\"e4e31db0fb8bc2ea42784c1dd9daf6e5==C\",\"lbs_maptype\":\"1\"," +
            "\"is_vsdp\":\"0\",\"template_home\":\"0\",\"is_bp\":\"1\",\"menu_style\":\"\",\"appsecret\":null,\"wx_alias\":\"\",\"account_is_auth\":\"1\"," +
            "\"code_stat\":null,\"id\":\"55969236\",\"area\":null,\"template_menu\":\"0\",\"business_id\":\"57173201\"," +
            "\"wx_service_type_info\":\"{\\\"id\\\":2}\",\"wx_funcscope_category\":\"[{\\\"funcscope_category\\\":{\\\"id\\\":1}}," +
            "{\\\"funcscope_category\\\":{\\\"id\\\":15}},{\\\"funcscope_category\\\":{\\\"id\\\":4}},{\\\"funcscope_category\\\":{\\\"id\\\":7}}," +
            "{\\\"funcscope_category\\\":{\\\"id\\\":2}},{\\\"funcscope_category\\\":{\\\"id\\\":3}},{\\\"funcscope_category\\\":{\\\"id\\\":11}}," +
            "{\\\"funcscope_category\\\":{\\\"id\\\":6}},{\\\"funcscope_category\\\":{\\\"id\\\":5}},{\\\"funcscope_category\\\":{\\\"id\\\":8}}," +
            "{\\\"funcscope_category\\\":{\\\"id\\\":13}},{\\\"funcscope_category\\\":{\\\"id\\\":10}},{\\\"funcscope_category\\\":{\\\"id\\\":12}}]\"," +
            "\"wx_sourceid\":\"gh_b7a7f298969e\",\"gz_wx_wifi_switch\":\"N\",\"wx_verify_type_info\":\"{\\\"id\\\":0}\",\"open_weimobpay\":\"0\"," +
            "\"list_style\":\"\",\"channel_style\":\"\",\"plc_name\":\"\\u5c0f\\u9986\\u8ba2\\u9910\",\"lbs_distance\":\"10000\",\"homemenu_style\":\"\"," +
            "\"template_detail\":\"0\",\"detail_style\":\"\"}";

}