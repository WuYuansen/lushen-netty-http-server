package org.lushen.zhuifeng.http.proxy.server.supports;

import java.net.InetSocketAddress;

import org.apache.commons.lang3.ArrayUtils;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyFilter;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyFilterChain;
import org.lushen.zhuifeng.http.proxy.utils.AssertUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * ip白名单拦截器
 * 
 * @author helm
 */
public class HttpWhiteListIpsFilter implements HttpProxyFilter {

	private String[] whiteListIps;

	public HttpWhiteListIpsFilter(String[] whiteListIps) {
		super();
		AssertUtils.nonNull(whiteListIps, () -> new IllegalArgumentException("white list ips can't be null or contains null !"));
		this.whiteListIps = whiteListIps;
	}

	@Override
	public int getOrder() {
		return Orders.WHITE_LIST_IPS_ORDER;
	}

	@Override
	public void doFilter(ChannelHandlerContext context, HttpRequest request, HttpProxyFilterChain chain) throws Exception {
		InetSocketAddress address = (InetSocketAddress) context.pipeline().channel().remoteAddress();
		String ipAddress = address.getAddress().getHostAddress();
		if( ! ArrayUtils.contains(this.whiteListIps, ipAddress)) {
			throw new RuntimeException(String.format("非法IP地址[%s]!", ipAddress));
		}
		chain.invoke(context, request);
	}

}
