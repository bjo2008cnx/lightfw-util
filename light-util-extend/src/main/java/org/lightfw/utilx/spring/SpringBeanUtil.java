package org.lightfw.utilx.spring;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Bean实例工厂
 */
@Log4j2
public class SpringBeanUtil {

    /**
     * 如果默认的文件名路径不是classpath:service.xml，需要调用initContextFile进行配置
     */
    private static String DEFAULT_FILE_NAME = "service.xml";
    private static final String DEFAULT_DATA_SOURCE = "dataSource";

    /**
     * 全局单例的BeanFactory
     */
    private static BeanFactory beanFactory = null;

    /**
     * 已经开始初始化过程
     */
    private static volatile boolean initBeanStarted = false;

    /**
     * 初始化过程完毕
     */
    private static volatile boolean isInitBean = false;

    /**
     * 初始化时的锁，用于将调用getBean的消费者排队等待
     */
    public static Object lockInitFactory = new Object();

    public static void initContextFile(String xmlFileName) {
        DEFAULT_FILE_NAME = xmlFileName;
    }

    /**
     * 返回bean数组
     *
     * @param beanFactory
     * @param type        类的类型
     * @return bean数组
     */
    public static Object[] getBeans(BeanFactory beanFactory, Class type) {
        AbstractXmlApplicationContext ctx = (AbstractXmlApplicationContext) beanFactory;
        String[] beanNames = ctx.getBeanNamesForType(type);
        if (beanNames == null) {
            return null;
        }
        Object[] beans = new Object[beanNames.length];
        for (int i = 0; i < beanNames.length; i++) {
            String beanName = beanNames[i];
            beans[i] = getBean(beanName);
        }
        return beans;
    }

    /**
     * 返回bean数组
     *
     * @param type 类的类型
     * @return bean数组
     */
    public static Object[] getBeans(Class type) {

        BeanFactory ctx = getBeanFactory();

        String[] beanNames = ((AbstractApplicationContext) ctx).getBeanNamesForType(type);
        if (beanNames == null) {
            return null;
        }
        Object[] beans = new Object[beanNames.length];
        for (int i = 0; i < beanNames.length; i++) {
            String beanName = beanNames[i];
            beans[i] = getBean(beanName);
        }
        return beans;
    }

    public static void setInitBeanStarted(boolean initBeanStarted_) {
        initBeanStarted = initBeanStarted_;
    }

    private static void initBeanFactory(String... fileNames) {
        ApplicationContext context = new ClassPathXmlApplicationContext(fileNames);
        beanFactory = context;
    }

    /**
     * 返回BeanFactory
     *
     * @return
     */
    public static BeanFactory getBeanFactory(String... contextFiles) {
        if (!isInitBean) {
            synchronized (lockInitFactory) { // lock and init
                if (!isInitBean) {
                    setInitBeanStarted(true);
                    initBeanFactory(contextFiles);
                    isInitBean = true;
                }
            }
        }
        return beanFactory;
    }

    /**
     * 手动设置BeanFactory，一般由Web.xml配置启动的Listener调用
     *
     * @param bf
     */
    public static void setBeanFactory(BeanFactory bf) {
        if (bf == null) {
            throw new RuntimeException("BeanFactory can not be null!");
        }
        beanFactory = bf;
        isInitBean = true;
    }

    private static BeanFactory getBeanFactorySafe(String... contextFiles) {
        if (contextFiles == null || contextFiles.length == 0) {
            contextFiles = new String[]{DEFAULT_FILE_NAME};
        }
        if (!isInitBean && initBeanStarted) { // 如果未启动完毕，但已经开始启动，则不走getBeanFactory()防止触发创建第2个BeanFactory
            if (beanFactory != null) {
                return beanFactory;
            }
            synchronized (lockInitFactory) {
                //.do nothing but wait for process that init
            }
            if (!isInitBean) {
                StringBuilder warnInfo = new StringBuilder("BeanHelper.getBeanFactorySafe(\"");
                warnInfo.append("\") return null, StackTrace:");
                log.warn(warnInfo.toString());
                return null;
            }
        }
        return getBeanFactory(contextFiles);
    }


    public static Object getBean(String beanName) {
        BeanFactory bf = getBeanFactorySafe();
        if (bf != null) {
            return bf.getBean(beanName);
        }
        return null;
    }

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        BeanFactory bf = getBeanFactorySafe();
        if (bf != null) {
            return bf.getBean(name, requiredType);
        }
        return null;
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        BeanFactory bf = getBeanFactorySafe();
        if (bf != null) {
            return bf.getBean(requiredType);
        }
        return null;
    }

    public static Object getBean(String name, Object... args) throws BeansException {
        BeanFactory bf = getBeanFactorySafe();
        if (bf != null) {
            return bf.getBean(name, args);
        }
        return null;
    }

    /**
     * ro
     * 获得默认的DataSource
     *
     * @return
     */
    public static DataSource getDataSource() {
        DataSource ds = (DataSource) SpringBeanUtil.getBean(DEFAULT_DATA_SOURCE);
        return ds;
    }

    /**
     * 获得默认的Connection
     *
     * @return
     */
    public static Connection getConnection() {
        DataSource ds = (DataSource) SpringBeanUtil.getBean(DEFAULT_DATA_SOURCE);
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("获取默认数据源出错", e);
        }
        return conn;
    }

    /**
     * 返回SQL Map
     *
     * @param beanName
     * @return
     */
    public static Map<String, String> getSQLMap(String beanName) {
        Map<String, String> map = (Map<String, String>) SpringBeanUtil.getBean(beanName);
        return map;
    }

    /**
     * 对应spring中设置的配置信息
     *
     * @param beanName
     * @return 键值对保存配置信息
     */
    public static Map<String, String> getMap(String beanName) {
        Map<String, String> map = (Map<String, String>) SpringBeanUtil.getBean(beanName);
        return map;
    }

}
