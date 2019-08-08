package org.lushen.zhuifeng.http.proxy.server.supports;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyFilter;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyFilterChain;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

/**
 * debug级别日志打印拦截器
 * 
 * @author helm
 */
public class HttpRequestDebugFilter implements HttpProxyFilter {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	@Override
	public int getOrder() {
		return Orders.DEBUG_ORDER;
	}

	@Override
	public void doFilter(ChannelHandlerContext context, HttpRequest request, HttpProxyFilterChain chain) throws Exception {
		if(log.isDebugEnabled()) {
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
			HttpVersion version = request.protocolVersion();
			HttpMethod method = request.method();
			String uri = request.uri();
			log.debug(String.format("%s %s %s %s.", time, version, method, uri));
		}
		chain.invoke(context, request);
	}
	
}
