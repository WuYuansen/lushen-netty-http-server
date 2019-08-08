package org.lushen.zhuifeng.http.proxy.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.zhuifeng.http.proxy.server.supports.HttpAllowMethodFilter;
import org.lushen.zhuifeng.http.proxy.server.supports.HttpAllowOptionsMethodFilter;
import org.lushen.zhuifeng.http.proxy.server.supports.HttpRequestDebugFilter;
import org.lushen.zhuifeng.http.proxy.server.supports.HttpUserAgentFilter;
import org.lushen.zhuifeng.http.proxy.server.supports.HttpWhiteListIpsFilter;
import org.lushen.zhuifeng.http.proxy.utils.NettyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 代理服务器
 * 
 * @author helm
 */
public class HttpProxyServer implements ApplicationContextAware, InitializingBean, DisposableBean {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	private ApplicationContext applicationContext;		//spring上下文

	private EventLoopGroup parentGroup;				//IO监听线程组

	private EventLoopGroup childGroup;				//IO处理线程组

	/**
	 * 配置注入
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * 初始化代理
	 */
	@Override
	public synchronized void afterPropertiesSet() throws Exception {

		//阻止二次初始化
		if(this.parentGroup != null) {
			log.warn("Http proxy server initialize already!");
			return;
		}

		//异步初始化
		Thread thread = new Thread(() -> initialize());
		thread.setDaemon(true);
		thread.start();
	}

	private void initialize() {

		//加载代理bean
		HttpProxyProperties properties = this.applicationContext.getBean(HttpProxyProperties.class);
		HttpProxyExecutor proxyExecutor = this.applicationContext.getBean(HttpProxyExecutor.class);
		HttpProxyErrorResolver proxyErrorResolver = this.applicationContext.getBean(HttpProxyErrorResolver.class);
		List<HttpProxyFilter> proxyFilters = new ArrayList<HttpProxyFilter>(this.applicationContext.getBeansOfType(HttpProxyFilter.class).values());

		log.info("http代理服务器配置：" + properties);
		
		//添加默认的过滤器
		proxyFilters.add(new HttpRequestDebugFilter());
		proxyFilters.add(new HttpAllowOptionsMethodFilter());
		if(ArrayUtils.isNotEmpty(properties.getWhiteListUserAgents())) {
			proxyFilters.add(new HttpUserAgentFilter(properties.getWhiteListUserAgents()));
		}
		if(ArrayUtils.isNotEmpty(properties.getWhiteListIps())) {
			proxyFilters.add(new HttpWhiteListIpsFilter(properties.getWhiteListIps()));
		}
		if(ArrayUtils.isNotEmpty(properties.getAllowMethods())) {
			String[] allowMethods = properties.getAllowMethods();
			proxyFilters.add(new HttpAllowMethodFilter(NettyUtils.toHttpMethods(allowMethods)));
		}

		//初始化代理handler
		final HttpProxyHandler proxyHandler = new HttpProxyHandler(proxyFilters, proxyExecutor, proxyErrorResolver);

		try {

			//初始化IO线程组和工作线程组
			this.parentGroup = new NioEventLoopGroup(properties.getAcceptors());
			this.childGroup = new NioEventLoopGroup(properties.getWorkers());

			//初始化代理
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(this.parentGroup, this.childGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.SO_BACKLOG, 128);
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.handler(new LoggingHandler(LogLevel.DEBUG));
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel channel) throws Exception {
					channel.pipeline().addLast(new HttpResponseEncoder());
					channel.pipeline().addLast(new HttpRequestDecoder());
					channel.pipeline().addLast(new HttpObjectAggregator(65536));
					channel.pipeline().addLast(proxyHandler);
					channel.pipeline().addLast(new HttpServerCodec());
				}
			});
			ChannelFuture future = bootstrap.bind(properties.getPort()).sync();
			future.channel().closeFuture().sync();

		} catch (Throwable e) {

			log.error(e.getMessage(), e);
			throw new RuntimeException(e);

		} finally {

			//异常处理
			this.childGroup.shutdownGracefully();
			this.parentGroup.shutdownGracefully();

		}
	}

	/**
	 * 清理退出代理
	 */
	@Override
	public synchronized void destroy() throws Exception {
		if(this.childGroup != null && ! this.childGroup.isTerminated()) {
			this.childGroup.shutdownGracefully();
		}
		if(this.parentGroup != null && ! this.parentGroup.isTerminated()) {
			this.parentGroup.shutdownGracefully();
		}
	}

}
