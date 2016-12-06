package org.lightfw.utilx.spring;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Map;

/**
 * 功能:通用DAO
 *
 * @author jason
 */
public class DaoTemplate extends JdbcTemplate {
    private PlatformTransactionManager platformTransactionManager;
    private DefaultTransactionDefinition transactionDefinition;
    private ThreadLocal<TransactionStatus> transcationStatus = new ThreadLocal<TransactionStatus>();

    public void beginTranstaion() {
        TransactionStatus tmp = platformTransactionManager.getTransaction(transactionDefinition);
        transcationStatus.set(tmp);
    }

    public void commit() {
        TransactionStatus tmp = transcationStatus.get();
        if (tmp == null) {
            throw new RuntimeException("no transcation");
        }
        platformTransactionManager.commit(tmp);
        transcationStatus.remove();
    }

    public void rollback() {
        TransactionStatus tmp = transcationStatus.get();
        if (tmp == null) {
            throw new RuntimeException("no transcation");
        }
        platformTransactionManager.rollback(tmp);
        transcationStatus.remove();

    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        platformTransactionManager = new DataSourceTransactionManager(getDataSource());
    }

    public PlatformTransactionManager getPlatformTransactionManager() {
        return platformTransactionManager;
    }

    public DefaultTransactionDefinition getTransactionDefinition() {
        return transactionDefinition;
    }

    public ThreadLocal<TransactionStatus> getTranscationStatus() {
        return transcationStatus;
    }


    /**
     * 通用的方法，返回自己控制的单个对象
     *
     * @param sql          要执行的sql语句
     * @param requiredType 需要的类型
     * @return
     */
    public <T> T queryForObject(String sql, Class<T> requiredType) {
        return queryForObject(sql, getSingleColumnRowMapper(requiredType));
    }

    /**
     * 通用的方法，返回自己控制的对象集合
     *
     * @param sql         要执行的sql语句
     * @param elementType 需要的类型
     * @return
     */
    public <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
        return query(sql, getSingleColumnRowMapper(elementType));
    }

    /**
     * 通用的方法，返回自己控制的动态对象集合
     *
     * @param sql 要执行的sql语句
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
        return query(sql, getColumnMapRowMapper());
    }

    /**
     * 通用的方法，返回自己控制的对象
     *
     * @param sql      要执行的sql语句
     * @param argTypes 需要的类型
     * @return
     */
    public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType)
            throws DataAccessException {
        return query(sql, args, argTypes, getSingleColumnRowMapper(elementType));
    }

    /**
     * 通用的方法，返回自己控制的对象
     *
     * @param sql         要执行的sql语句
     * @param args        需要的参数
     * @param elementType 需要的类型
     * @return
     */
    public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType)
            throws DataAccessException {
        return query(sql, args, getSingleColumnRowMapper(elementType));
    }

    /**
     * 通用的方法，返回自己控制的对象
     *
     * @param sql 要执行的sql语句
     * @return
     */
    public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args)
            throws DataAccessException {
        return query(sql, args, getSingleColumnRowMapper(elementType));
    }

    /**
     * 通用的方法，返回自己控制的对象
     *
     * @param sql 要执行的sql语句
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, Object[] args, int[] argTypes)
            throws DataAccessException {
        return query(sql, args, argTypes, getColumnMapRowMapper());
    }

    /**
     * 通用的方法，返回自己控制的对象
     *
     * @param sql 要执行的sql语句
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, Object... args)
            throws DataAccessException {
        return query(sql, args, getColumnMapRowMapper());
    }

    /**
     * 通用的方法，返回自己控制的对象
     *
     * @param sql 要执行的sql语句
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, Object[] args, RowMapper rowMapper, int startIndex, int size)
            throws DataAccessException {
        return (List<Map<String, Object>>) query(getSqlPage4Mysql(sql, startIndex, size), args, rowMapper);
    }

    private static String getSqlPage4Mysql(String strsql, int startIndex, int size) {
        return strsql + " limit " + (startIndex - 1) + "," + size;
    }

    /**
     * 插入一个对象，并返回这个对象的自增id
     * @param obj
     * @return
     */
  /*  public <T> int insertObjectAndGetAutoIncreaseId(T obj) {
        final String sql = BeanOperator.getSqlByObject(SqlTypes.INSERT, obj);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int autoIncId = 0;

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                return ps;
            }
        }, keyHolder);

        autoIncId = keyHolder.getKey().intValue();

        return autoIncId;
    }*/

}