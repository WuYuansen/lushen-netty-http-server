package org.lushen.zhuifeng.http.proxy.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * http代理异常处理器
 * 
 * @author helm
 */
public interface HttpProxyErrorResolver {

	/**
	 * 处理异常
	 * 
	 * @param context
	 * @param request
	 * @param cause
	 */
	void resolve(ChannelHandlerContext context, HttpRequest request, Throwable cause);

}
