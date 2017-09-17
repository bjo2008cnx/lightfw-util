package org.lightfw.tool;

import java.lang.reflect.Field;

/**
 * CopyObjectCodeTool
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
    public static String genearete(Object obj)  {
        StringBuilder builder = new StringBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            builder.append("obj.set").append(field.getName()).append("(null)");
        }
        return builder.toString();
    }
}