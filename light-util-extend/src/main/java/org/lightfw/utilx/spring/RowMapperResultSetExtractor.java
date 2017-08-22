package org.lightfw.utilx.spring;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 行映射结果集
 *
 * @author jason
 */
public class RowMapperResultSetExtractor implements ResultSetExtractor {
    private final RowMapper rowMapper;
    private final int startIndex;
    private final int size;
    private boolean absoluteByNext;

    public RowMapperResultSetExtractor(RowMapper rowMapper, int startIndex, int size,
                                       boolean absoluteByNext) {
        this.rowMapper = rowMapper;
        this.startIndex = startIndex;
        this.size = size;
        this.absoluteByNext = absoluteByNext;
    }

    public Object extractData(ResultSet rs) throws SQLException {
        List<Object> results = new ArrayList<Object>();
        int rowProcessed = 0;
        if (absoluteByNext) {
            if (startIndex > 0) {
                int pos = 1;
                while (pos < startIndex) {
                    rs.next();
                    pos++;
                }
            }
        } else {
            if (startIndex > 0) {
                /*移动到相对于结果集头部的指定行*/
                if (rs.getRow() != (startIndex - 1)) {
                    rs.absolute(startIndex - 1);
                }
            } else if (startIndex < 0) {
                /*移动到相对于结果集尾部的指定行*/
                rs.absolute(startIndex - 1);
            }
        }
        while (rs.next() && rowProcessed < size) {
            results.add(this.rowMapper.mapRow(rs, rs.getRow()));
            rowProcessed++;
        }
        return results;
    }
}
