package org.lushen.zhuifeng.http.proxy;

import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 代理启动入口
 * 
 * @author helm
 */
@SpringBootApplication
@ComponentScan(basePackageClasses=HttpServerStarter.class)
public class HttpServerStarter {

	private static final Log log = LogFactory.getLog(HttpServerStarter.class.getSimpleName());

	public static void main(String[] args) {
		try {
			log.info("http代理服务器启动开始...");
			SpringApplication application = new SpringApplication(HttpServerStarter.class);
			application.setWebApplicationType(WebApplicationType.NONE);
			application.run(args);
			log.info("http代理服务器启动成功.");
			new CountDownLatch(1).await();
		} catch (Exception e) {
			log.info("http代理服务器启动失败.");
			e.printStackTrace();
		}
	}

}
