package org.lushen.zhuifeng.http.proxy.server.supports;

import static io.netty.handler.codec.http.HttpHeaderNames.USER_AGENT;

import org.apache.commons.lang3.ArrayUtils;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyFilter;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyFilterChain;
import org.lushen.zhuifeng.http.proxy.utils.AssertUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * 白名单User-Agent拦截器
 * 
 * @author helm
 */
public class HttpUserAgentFilter implements HttpProxyFilter {

	private String[] userAgents;

	public HttpUserAgentFilter(String[] userAgents) {
		super();
		AssertUtils.nonNull(userAgents, () -> new IllegalArgumentException("user agents can't be null or contains null !"));
		this.userAgents = userAgents;
	}

	@Override
	public int getOrder() {
		return Orders.USER_AGENT_ORDER;
	}

	@Override
	public void doFilter(ChannelHandlerContext context, HttpRequest request, HttpProxyFilterChain chain) throws Exception {
		if( ! ArrayUtils.contains(this.userAgents, request.headers().get(USER_AGENT))) {
			throw new RuntimeException("非法客户端!");
		}
		chain.invoke(context, request);
	}

}
