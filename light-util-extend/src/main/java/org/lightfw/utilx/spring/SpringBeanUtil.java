package org.lightfw.utilx.spring;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Bean实例工厂
 */
@Log4j2
public class SpringBeanUtil {

    private static final String DEFAULT_FILE_NAME = "services.all.xml"; //TODO 参数传入
    private static final String DEFAULT_CONTEXT = "default-context";
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

    /**
     * 自执行初始化BeanFactory
     */
    private static void defaultInitBeanFactory() {

        log.info("Init bean factory start................");
        BeanFactoryLocator instance = null;
        instance = SingletonBeanFactoryLocator.getInstance(DEFAULT_FILE_NAME);

        BeanFactoryReference bfr = instance.useBeanFactory(DEFAULT_CONTEXT); // TODO
        // 传入参数
        beanFactory = bfr.getFactory();
        log.info("Init bean factory end.");
    }

    /**
     * 返回BeanFactory
     *
     * @return
     */
    public static BeanFactory getBeanFactory() {
        if (!isInitBean) {
            synchronized (lockInitFactory) { // lock and init
                if (!isInitBean) {
                    setInitBeanStarted(true);
                    defaultInitBeanFactory();
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

    private static BeanFactory getBeanFactorySafe() {
        if (!isInitBean && initBeanStarted) { // 如果未启动完毕，但已经开始启动，则不走getBeanFactory()防止触发创建第2个BeanFactory
            if (beanFactory != null) {
                return beanFactory;
            }
            synchronized (lockInitFactory) { // wait for process that initing
                // beanFactory
                // nothing
            }
            if (!isInitBean) {
                StringBuilder warnInfo = new StringBuilder("BeanHelper.getBeanFactorySafe(\"");
                warnInfo.append("\") return null, StackTrace:");
                log.warn(warnInfo.toString());
                return null;
            }
        }
        return getBeanFactory();
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

    /**
     * Return the bean instance that uniquely matches the given object type, if
     * any.
     *
     * @param requiredType type the bean must match; can be an interface or superclass.
     *                     {@code null} is disallowed.
     *                     <p/>
     *                     This method goes into {@link ListableBeanFactory} by-type
     *                     lookup territory but may also be translated into a
     *                     conventional by-name lookup based on the name of the given
     *                     type. For more extensive retrieval operations across sets of
     *                     beans, use {@link ListableBeanFactory} and/or
     *                     {@link BeanFactoryUtils}.
     * @return an instance of the single bean matching the required type
     * @throws NoSuchBeanDefinitionException   if no bean of the given type was found
     * @throws NoUniqueBeanDefinitionException if more than one bean of the given type was found
     * @see ListableBeanFactory
     * @since 3.0
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        BeanFactory bf = getBeanFactorySafe();
        if (bf != null) {
            return bf.getBean(requiredType);
        }
        return null;
    }

    /**
     * Return an instance, which may be shared or independent, of the specified
     * bean.
     * <p/>
     * Allows for specifying explicit constructor arguments / factory method
     * arguments, overriding the specified default arguments (if any) in the
     * bean definition.
     *
     * @param name the name of the bean to retrieve
     * @param args arguments to use if creating a prototype using explicit
     *             arguments to a static factory method. It is invalid to use a
     *             non-null args value in any other case.
     * @return an instance of the bean
     * @throws NoSuchBeanDefinitionException if there is no such bean definition
     * @throws BeanDefinitionStoreException  if arguments have been given but the affected bean isn't a
     *                                       prototype
     * @throws BeansException                if the bean could not be created
     * @since 2.5
     */
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
