package org.lightfw.util.db;

import com.google.common.base.Throwables;
import org.lightfw.util.lang.ExceptionUtil;

import java.sql.*;

/**
 * 数据库工具类
 *
 * @author Wangxm
 * @date 2016/5/10
 */
public class JDBCUtil {
    /**
     * 关闭连接
     *
     * @param conn
     */
    public static void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            Throwables.propagateIfPossible(e);
        }
    }

    /**
     * 关闭语句
     *
     * @param stmt
     */
    public static void close(Statement stmt) {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            Throwables.propagateIfPossible(e);
        }
    }

    /**
     * 关闭结果集
     *
     * @param rs
     */
    public static void close(ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            Throwables.propagateIfPossible(e);
        }
    }

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
            close(rs);
            close(pstmt);
        }
    }
}

