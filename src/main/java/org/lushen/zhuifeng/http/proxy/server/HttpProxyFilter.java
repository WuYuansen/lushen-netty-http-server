package org.lushen.zhuifeng.http.proxy.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * http代理过滤器
 * 
 * @author helm
 */
public interface HttpProxyFilter {

	/**
	 * 获取排序优先级(数值越小，优先级越高)
	 */
	default int getOrder() {
		return Integer.MAX_VALUE;
	}

	/**
	 * 执行过滤逻辑
	 * 
	 * @param context
	 * @param request
	 * @param chain
	 * @throws Exception
	 */
	void doFilter(ChannelHandlerContext context, HttpRequest request, HttpProxyFilterChain chain) throws Exception;

}
