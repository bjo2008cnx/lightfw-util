package org.lightfw.util.thread;

import lombok.extern.log4j.Log4j2;
import org.lightfw.util.collection.ArrayUtil;
import org.lightfw.util.lang.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

@Log4j2
public class ThreadUtil {

    /**
     * 休眠
     *
     * @param millSeconds 毫秒数
     */
    public static boolean sleep(long millSeconds) {
        try {
            Thread.sleep(millSeconds);
            return true;
        } catch (InterruptedException e) {
            log.warn("[WARNING]Sleeping is interrupted!!!");
            return false;
        }
    }

    /**
     * 获取线程id和名称
     *
     * @param t
     * @return
     */
    public static String getIdName(Thread t) {
        return "[" + t.getId() + ":" + t.getName() + "]";
    }

    /**
     * 挂起线程
     */
    public static void park() {
        log.debug("current thread is going to park");
        LockSupport.park();
        log.debug("current thread is parked");
    }

    /**
     * 解挂线程
     */
    public static void unpark(Thread t) {
        log.debug("current thread is going to unpark");
        LockSupport.unpark(t);
        log.debug("current thread is unparked");
    }

    /**
     * join到主线程
     *
     * @param threads
     */
    public static void join(Thread... threads) {
        for (Thread t : threads) {
            String idName = getIdName(t);
            log.debug("线程" + idName + "将join到主线程");
            try {
                t.join();
            } catch (InterruptedException e) {
                log.warn("线程" + idName + "join时被中断");
            }
            log.debug("线程" + idName + "joined 到主线程");
        }
    }

    /**
     * 创建一个固定大小的线程池并执行线程
     *
     * @param runnables
     */
    public static void execute(Runnable... runnables) {
        Executor executor = Executors.newFixedThreadPool(runnables.length);
        for (final Runnable r : runnables) {
            executor.execute(r);
        }
    }

    /**
     * 使用ExecutorService的invokeAll方法调研callable集合，批量执行多个线程
     * 在invokeAll方法结束之后，再执行主线程其他业务逻辑
     *
     * @throws InterruptedException
     */
    public static <T> void execute(Callable<T>... callables) {
        Collection<Callable<T>> callList = ArrayUtil.toList(callables);
        ExecutorService exec = Executors.newFixedThreadPool(2);

        try {
            exec.invokeAll(callList);
        } catch (InterruptedException e) {
            handleException(e);
        }
        exec.shutdown();
        log.info("子线程全部执行完毕，等待主线程执行任务");
        //主线程可以做些其他工作
        //do some extra work
    }

    /**
     * 处理线程异常
     *
     * @param e
     */
    public static void handleException(Exception e) {
        if (e instanceof InterruptedException) {
            log.error("executor 被中断", e);
        }
    }

    /**
     * 判断任务是否已完成
     *
     * @param tasks
     * @return
     */
    public static boolean isDone(RecursiveTask[] tasks) {
        for (RecursiveTask task : tasks) {
            if (!task.isDone()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 执行任务
     *
     * @param tasks
     * @param sleepSeconds
     */
    public static void execute(RecursiveTask[] tasks, int sleepSeconds) {
        ForkJoinPool pool = new ForkJoinPool();
        for (RecursiveTask task : tasks) {
            pool.execute(task);
        }

        do {
            log.info("******************************************\n");
            log.info("Main: Parallelism: " + pool.getParallelism());
            log.info("Main: Active Threads:" + pool.getActiveThreadCount());
            log.info("Main: Task Count:" + pool.getQueuedTaskCount());
            log.info("Main: Steal Count:" + pool.getStealCount());
            log.info("******************************************\n");

            ThreadUtil.sleep(sleepSeconds * 1000);
        } while (!ThreadUtil.isDone(tasks));
        pool.shutdown();
    }

    public static Thread findThreadByName(String threadName) {
        Thread[] list = findAllThreads();
        for (Thread thread : list) {
            if (thread.getName().equals(threadName)) {
                return thread;
            }
        }
        return null;
    }

    public static List<Thread> findThreadByStartWith(String prefix) {
        Thread[] list = findAllThreads();
        List<Thread> res = new ArrayList();
        for (Thread thread : list) {
            if (StringUtil.startsWith(thread.getName(), prefix)) {
                res.add(thread);
            }
        }
        return res;
    }

    /**
     * @return
     */
    public static Thread[] findAllThreads() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 遍历线程组树，获取根线程组
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // 激活的线程数加倍
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
        // 获取根线程组的所有线程
        int actualSize = topGroup.enumerate(slackList);
        // copy into a list that is the exact size
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        return list;
    }

    /**
     * 等待
     *
     * @param latch
     * @param sleepTime
     */
    public static void await(CountDownLatch latch, long sleepTime) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            ThreadUtil.sleep(sleepTime);
        }
    }
}
