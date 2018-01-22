package org.lightfw.tool;

import third.BusinessInfo;

import java.lang.reflect.Field;

/**
 * 生成对象拷贝代码工具集
 *
 * @author Michael.Wang
 * @date 2017/9/15
 */
public class CopyObjectCodeTool {
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
            String name = StringUtil.toUpperFirst(field.getName());
            builder.append("data.set").append(name).append("(vo.get").append(name).append("());\n");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String code = genearete(new BusinessInfo());
        System.out.println(code);
    }
}