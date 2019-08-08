package org.lushen.zhuifeng.http.proxy.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * http代理过滤器链定义接口
 * 
 * @author helm
 */
public interface HttpProxyFilterChain {

	/**
	 * 调用下一个filter
	 * 
	 * @param context
	 * @param request
	 * @throws Exception
	 */
	void invoke(ChannelHandlerContext context, HttpRequest request) throws Exception;

}
