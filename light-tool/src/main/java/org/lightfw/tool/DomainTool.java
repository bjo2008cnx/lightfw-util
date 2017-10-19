package org.lightfw.tool;

/**
 * 将所有带_的替换成驼峰风格的
 *
 * @author Michael.Wang
 * @date 2017/10/2
 */
public class DomainTool {
    private static String str = " pid, bid, mobile, id, username, password, wm_business_id, agent_id, province, city, country, address, contact, mobile, " +
            "email, qq,registered_id, source_id, salesman, business_type, representative, company_name, linkman, is_new, is_reset, flag, status, " +
            "aptitude_status, package_id, principal_status, wechat_pay_status, program_deploy_status, program_status, opening_time, " +
            "wechat_pay_access_time, program_apply_time, program_access_time, expire_time, create_time, update_time, source_type, mobile_resource," + " " +
            "allocated_person, allocated_time";

    public static void main(String[] args) {
        str = str.replaceAll("\"", "");
        String[] strs = str.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            sb.append("private String ");
            sb.append(StringUtil.underlineToCamel(strs[i]));
            sb.append(";");
        }

        System.out.println(sb.toString());

    }
}