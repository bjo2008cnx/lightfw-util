package org.lightfw.tool;

import java.lang.reflect.Field;

/**
 * CopyObjectCodeTool
 *
 * @author Michael.Wang
 * @date 2017/9/15
 */
public class ReadCSVCodeTool {
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
            builder.append("req.set").append(name).append("(vo[i++]").append(");\n");
        }
        return builder.toString();
    }
}