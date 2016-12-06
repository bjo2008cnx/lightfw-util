package org.lightfw.utilx.spring;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 性能检测
 *
 * @author jason
 */
public class PerfInterceptor implements MethodInterceptor {
    Logger logger = LoggerFactory.getLogger(PerfInterceptor.class.getName());
    private static ConcurrentHashMap<String, MethodStatstics> methodStats = new ConcurrentHashMap<String, MethodStatstics>();
    private static long statLogFrequency = 10;
    private static long methodWarningThreshold = 1000;

    /**
     * 调用方法
     *
     * @param method
     * @return
     * @throws Throwable
     */
    public Object invoke(MethodInvocation method) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return method.proceed();
        } finally {
            updateStatistics(method.getMethod().getName(), (System.currentTimeMillis() - start));
        }
    }

    /**
     * 更新统计信息
     *
     * @param methodName
     * @param elapsedTime
     */
    private void updateStatistics(String methodName, long elapsedTime) {
        MethodStatstics stats = methodStats.get(methodName);
        if (stats == null) {
            stats = new MethodStatstics(methodName);
            methodStats.put(methodName, stats);
        }
        stats.count++;
        stats.totalTime += elapsedTime;
        if (elapsedTime > stats.maxTime) {
            stats.maxTime = elapsedTime;
        }

        if (elapsedTime > methodWarningThreshold) {
            logger.warn("method warning: " + methodName + "(), cnt = " + stats.count + ", lastTime = " + elapsedTime + ", maxTime = "
                    + stats.maxTime);
        }

        if (stats.count % statLogFrequency == 0) {
            long avgTime = stats.totalTime / stats.count;
            long runningAvg = (stats.totalTime - stats.lastTotalTime) / statLogFrequency;
            logger.debug("method: " + methodName + "(), cnt = " + stats.count + ", lastTime = " + elapsedTime + ", avgTime = " + avgTime
                    + ", runningAvg = " + runningAvg + ", maxTime = " + stats.maxTime);

            //reset the last total time
            stats.lastTotalTime = stats.totalTime;
        }
    }

    /**
     * 方法统计信息
     *
     * @author jason
     */
    class MethodStatstics {
        public String methodName;
        public long count;
        public long totalTime;
        public long lastTotalTime;
        public long maxTime;

        public MethodStatstics(String methodName) {
            this.methodName = methodName;
        }
    }

    @Override
    public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
        return null;
    }
}
