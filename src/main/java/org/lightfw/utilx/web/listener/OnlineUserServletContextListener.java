package org.lightfw.utilx.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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