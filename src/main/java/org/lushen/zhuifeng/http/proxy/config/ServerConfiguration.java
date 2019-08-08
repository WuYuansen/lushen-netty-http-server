package org.lushen.zhuifeng.http.proxy.config;

import org.lushen.zhuifeng.http.proxy.server.HttpProxyErrorResolver;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyExecutor;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyProperties;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyServer;
import org.lushen.zhuifeng.http.proxy.server.supports.DefaultHttpProxyErrorResolver;
import org.lushen.zhuifeng.http.proxy.server.supports.DefaultHttpProxyExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 代理服务器配置
 * 
 * @author helm
 */
@Configuration
@EnableConfigurationProperties
public class ServerConfiguration {

	private static final String PROXY_PREFIX = "org.lushen.zhuifeng.http.proxy";

	/**
	 * 代理服务器配置
	 */
	@Bean
	@ConfigurationProperties(prefix=PROXY_PREFIX)
	public HttpProxyProperties httpProxyProperties() {
		return new HttpProxyProperties();
	}

	/**
	 * 代理服务器
	 */
	@Bean
	public HttpProxyServer httpProxyServer() {
		return new HttpProxyServer();
	}

	/**
	 * 代理事务执行器
	 */
	@Bean
	@ConditionalOnMissingBean
	public HttpProxyExecutor httpServiceExecutor() {
		return new DefaultHttpProxyExecutor();
	}

	/**
	 * 代理异常处理器
	 */
	@Bean
	@ConditionalOnMissingBean
	public HttpProxyErrorResolver httpProxyErrorResolver() {
		return new DefaultHttpProxyErrorResolver();
	}

}
