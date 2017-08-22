package org.lightfw.utilx.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 用于统计单个容器的在线人数
 * Web容器在启动之后设置onlie_num属性，OnlineUserSessionListener在session建立时更新online_num
 */
public class OnlineUserSessionListener implements HttpSessionListener {

    public static final String ONLINE_NUM = "online_num";

    public void sessionCreated(HttpSessionEvent event) {
        ServletContext application = event.getSession().getServletContext();
        Integer num = (Integer) application.getAttribute(ONLINE_NUM);
        if (num != null) {
            int count = num;
            count = count + 1;
            application.setAttribute(ONLINE_NUM, count);
        }
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        ServletContext application = event.getSession().getServletContext();
        Integer num = (Integer) application.getAttribute(ONLINE_NUM);
        int count = num;
        count = count - 1;
        application.setAttribute(ONLINE_NUM, count);
    }
}