package org.lightfw.utilx.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 用于统计单个容器的在线人数。如果有集群，可以存储到redis中
 * Web容器在启动之后设置onlie_num属性，OnlineUserSessionListener在session建立时更新online_num
 */
public class OnlineUserServletContextListener implements ServletContextListener {

    public static final String ONLINE_NUM = "online_num";

    public void contextInitialized(ServletContextEvent event) {
        int num = 0;
        ServletContext application = event.getServletContext();
        application.setAttribute(ONLINE_NUM, num);
    }

    public void contextDestroyed(ServletContextEvent event) {
        ServletContext application = event.getServletContext();
        application.removeAttribute(ONLINE_NUM);
    }

}