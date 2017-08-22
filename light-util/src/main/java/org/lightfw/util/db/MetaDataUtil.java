package org.lightfw.util.db;

import org.lightfw.util.lang.ExceptionUtil;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取类名与列类型
 *
 * @author jason
 */
public class MetaDataUtil {

    private static Map<String, String> pks = new HashMap();

    /**
     * 获取给定表的所有列名及列类型
     *
     * @param conn
     * @param tableName
     * @return
     */
    public static Map<String, String> loadColumnType(Connection conn, String tableName) {
        Map<String, String> map = new HashMap();
        String sql = "select 1 from ".concat(tableName).concat(" where 1=2");
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsme = rs.getMetaData();

            int columnCount = rsme.getColumnCount();
            for (int i = 1; i < columnCount; i++) {
                map.put(rsme.getColumnName(i), rsme.getColumnTypeName(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * 返回表的主键以及类型
     *
     * @param conn
     * @param catalog
     * @return
     */
    public static Map<String, String> loadTableMetaData(Connection conn, String catalog) {
        if (pks.isEmpty()) {
            synchronized (MetaDataUtil.class) {
                if (pks.isEmpty()) {
                    loadTableMeta(conn, catalog);
                }
            }
        }
        return pks;
    }

    private static void loadTableMeta(Connection conn, String catalog) {
        ResultSet rs = null;
        try {
            DatabaseMetaData metadata = conn.getMetaData();
            rs = metadata.getTables(catalog, null, null, null);
            while (rs.next()) {
                String tableName = rs.getString(3);
                ResultSet rsPk = metadata.getPrimaryKeys(catalog, null, tableName);
                if (rsPk.next()) {
                    pks.put(tableName, rsPk.getString(4));
                }
                JDBCUtil.close(rsPk);
            }
        } catch (SQLException e) {
            throw ExceptionUtil.transform(e);
        } finally {
            JDBCUtil.close(rs);
        }
    }

}
