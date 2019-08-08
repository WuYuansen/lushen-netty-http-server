package org.lushen.zhuifeng.http.proxy.server.supports;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.lushen.zhuifeng.http.proxy.server.HttpProxyFilter;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyFilterChain;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

/**
 * http 允许OPTIONS请求拦截器
 * 
 * @author helm
 */
public class HttpAllowOptionsMethodFilter implements HttpProxyFilter {

	@Override
	public int getOrder() {
		return Orders.ALLOW_OPTIONS_METHOD_ORDER;
	}

	@Override
	public void doFilter(ChannelHandlerContext context, HttpRequest request, HttpProxyFilterChain chain) throws Exception {
		if(request.method() == HttpMethod.OPTIONS) {
			DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.EMPTY_BUFFER);
			response.headers().set(CONNECTION, KEEP_ALIVE);
			response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
			context.write(response);
			context.flush();
		} else {
			chain.invoke(context, request);
		}
	}

}
