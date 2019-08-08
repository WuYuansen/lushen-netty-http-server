package org.lushen.zhuifeng.http.proxy.server.supports;

import java.util.Arrays;

import org.lushen.zhuifeng.http.proxy.server.HttpProxyFilter;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyFilterChain;
import org.lushen.zhuifeng.http.proxy.utils.AssertUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

/**
 * http请求方法白名单拦截器
 * 
 * @author helm
 */
public class HttpAllowMethodFilter implements HttpProxyFilter {

	private HttpMethod[] allowMethods;

	public HttpAllowMethodFilter(HttpMethod[] allowMethods) {
		super();
		AssertUtils.nonNull(allowMethods, () -> new IllegalArgumentException("allow methods can't be null or contains null !"));
		this.allowMethods = allowMethods;
	}

	@Override
	public int getOrder() {
		return Orders.ALLOW_HTTP_METHOD_ORDER;
	}

	@Override
	public void doFilter(ChannelHandlerContext context, HttpRequest request, HttpProxyFilterChain chain) throws Exception {
		//方法白名单判断
		if( ! Arrays.stream(this.allowMethods).filter(method -> method == request.method()).findFirst().isPresent()) {
			throw new RuntimeException("非法http请求方法!");
		}
		chain.invoke(context, request);
	}

}
