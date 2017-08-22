package org.lightfw.utilx.spring;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * @Description: 对tinyint进行处理的RowMappper
 * 使用方法:TODO 待描述
 * @date 2016年5月3日 下午2:43:28
 */
public class DefaultColumnMapRowMapper extends ColumnMapRowMapper {

    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Map<String, Object> mapOfColValues = createColumnMap(columnCount);
        for (int i = 1; i <= columnCount; i++) {

            String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
            Object obj = null;
            if (rsmd.getColumnTypeName(i).equals("TINYINT")) {
                obj = rs.getInt(i);
            } else {
                obj = getColumnValue(rs, i);
            }
            mapOfColValues.put(key, obj);

        }
        return mapOfColValues;
    }
}