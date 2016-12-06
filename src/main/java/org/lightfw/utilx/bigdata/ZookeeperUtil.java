package org.lightfw.utilx.bigdata;

import lombok.extern.log4j.Log4j2;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.lightfw.util.thread.ThreadUtil;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * ZookeeperUtil
 *
 * @author Wangxm
 */
@Log4j2
public class ZookeeperUtil {

    /**
     * 创建zookeeper 会话,不等待连接成功直接返回
     */
    public static ZooKeeper start(String hostPort, int sessionTimeout, Watcher watcher) {
        ZooKeeper zk = null;
        log.info("开始连接 zookeeper:" + hostPort);
        try {
            zk = new ZooKeeper(hostPort, sessionTimeout, watcher);
            log.info("连接 zookeeper:" + hostPort + "成功");
        } catch (IOException e) {
            log.error("连接 zookeeper:" + hostPort + "失,败", e);
            return null;
        }
        return zk;
    }


    /**
     * 创建zookeeper 会话,并等待连接成功
     */
    public static ZooKeeper start(String hostPort, int sessionTimeout, Watcher watcher, CountDownLatch connectedLatch) {
        ZooKeeper zk = null;
        log.info("开始连接 zookeeper:" + hostPort);
        try {
            zk = new ZooKeeper(hostPort, sessionTimeout, watcher);
            log.info("连接 zookeeper:" + hostPort + "成功");
            // 等待连接成功
            try {
                connectedLatch.await();
            } catch (InterruptedException e) {
                ThreadUtil.sleep(1000);
            }
        } catch (IOException e) {
            log.error("连接 zookeeper:" + hostPort + "失,败", e);
            return null;
        }
        return zk;
    }
}
