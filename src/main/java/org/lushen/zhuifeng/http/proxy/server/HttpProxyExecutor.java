package org.lushen.zhuifeng.http.proxy.server;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * http事务执行接口
 * 
 * @author helm
 */
public interface HttpProxyExecutor {

	/**
	 * 执行http事务
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	HttpResponse doService(HttpRequest request) throws Exception;

}
