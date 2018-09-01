package org.lightfw.util.db;

import org.lightfw.util.io.common.StreamUtil;
import org.lightfw.util.lang.ExceptionUtil;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库工具类
 *
 * @author Wangxm
 * @date 2016/5/10
 */
public class JDBCUtil {

    /**
     * 静态SQL模式，新增记录并获取新增记录的主键
     *
     * @param conn
     * @param sql
     */
    public static Long insertWithAutoId(Connection conn, String sql) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            //检索由于执行此 Statement 对象而创建的所有自动生成的键
            rs = pstmt.getGeneratedKeys();
            return rs.next() ? rs.getLong(1) : 0L;//知其仅有一列，故获取第一列
        } catch (SQLException e) {
            throw ExceptionUtil.transform(e);
        } finally {
            StreamUtil.close(rs);
            StreamUtil.close(pstmt);
        }
    }

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

