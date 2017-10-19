package org.lightfw.tool;

import third.BusinessInfo;

import java.lang.reflect.Field;

/**
 * 生成对象的set语句
 *
 * @author Michael.Wang
 * @date 2017/9/15
 */
public class CreateObjectCodeTool {
    /**
     * 对象转成Map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static String genearete(Object obj) {
        StringBuilder builder = new StringBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            builder.append("obj.set").append(field.getName()).append("(null)");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String objectSetCode = genearete(new BusinessInfo());
        System.out.println(objectSetCode);
    }
}