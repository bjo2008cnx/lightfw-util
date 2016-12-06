package org.lightfw.util.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 线程对象, 写时加写锁，读时加读锁。加读锁之后可以读，但不可以写；加写锁时其他线程不可以读也不可以写
 *
 * @author Wangxm
 * @date 2016/5/26
 */
public class ThreadObject {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Object object;

    /**
     * 写对象
     *
     * @param object
     */
    public void write(Object object) {
        Lock write = lock.writeLock();
        write.lock();
        try {
            this.object = object;
        } finally {
            write.unlock();
        }
    }

    /**
     * 读对象
     *
     * @return
     */
    public Object read() {
        Lock read = lock.readLock();
        read.lock();

        try {
            return this.object;
        } finally {
            read.unlock();
        }
    }
}
