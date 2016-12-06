package org.lightfw.utilx.web.listener;


import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 通用的监听器,一个项目可以只配置一个监听器，在一个监听器中完成初始化工作
 */
@Log4j2
public class CommonContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent contextEvent) {
        log.info("容器初始化完成");
    }

    public void contextDestroyed(ServletContextEvent sc) {
        log.info("容器安全关闭");
    }
}
