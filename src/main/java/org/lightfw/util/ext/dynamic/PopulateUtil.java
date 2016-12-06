package org.lightfw.util.ext.dynamic;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.lightfw.util.lang.ExceptionUtil;

public class PopulateUtil {
    /**
     * 将rs数据拷贝到map
     */
    public static void populate(Map map, ResultSet rs) {
        if (map == null) {
            map = new HashMap();
        }
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            for (int i = 1; i <= count; i++) {
                String key = rsmd.getColumnLabel(i);
                String value = rs.getString(i);
                map.put(key.toLowerCase(), value);
            }
        } catch (SQLException e) {
            throw ExceptionUtil.transform(e);
        }
    }
}
