package org.lushen.zhuifeng.http.proxy.server.supports;

import org.lushen.zhuifeng.http.proxy.server.HttpProxyExecutor;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * 默认的http事务执行器
 * 
 * @author helm
 */
public class DefaultHttpProxyExecutor implements HttpProxyExecutor {

	@Override
	public HttpResponse doService(HttpRequest request) {
		throw new RuntimeException("没有定义http事务处理器!");
	}

}
