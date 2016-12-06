package org.lightfw.utilx.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class RuntimeListener implements ServletContextListener {

	private static final Logger log = Logger.getLogger(RuntimeListener.class.getName());

	public void contextInitialized(ServletContextEvent arg0) {
		log.info("contextInitialized");

		/*
		 * 注册JVM钩子，在JVM关闭之前做一些收尾的工作，当然也能阻止TOMCAT的关闭；必须放在contextInitialized中注册。
		 */
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			public void run() {
				int n = 0;
				while (n < 10) {
					log.info(Thread.currentThread() + "," + n++);
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}));
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("contextDestroyed ....");
	}

}