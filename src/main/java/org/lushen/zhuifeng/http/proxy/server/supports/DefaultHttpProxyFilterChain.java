package org.lushen.zhuifeng.http.proxy.server.supports;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.lushen.zhuifeng.http.proxy.server.HttpProxyFilter;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyFilterChain;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * 默认的http过滤器链实现
 * 
 * @author helm
 */
public class DefaultHttpProxyFilterChain implements HttpProxyFilterChain {

	private final AtomicInteger offset = new AtomicInteger(0);

	private final List<HttpProxyFilter> proxyFilters;

	public DefaultHttpProxyFilterChain(List<HttpProxyFilter> proxyFilters) {
		super();
		this.proxyFilters = Collections.unmodifiableList(proxyFilters);
	}

	@Override
	public void invoke(ChannelHandlerContext context, HttpRequest request) throws Exception {
		if(offset.get() < proxyFilters.size()) {
			this.proxyFilters.get(offset.getAndIncrement()).doFilter(context, request, this);
		}
	}

}
