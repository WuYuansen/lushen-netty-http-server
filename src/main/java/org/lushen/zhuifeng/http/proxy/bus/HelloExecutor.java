package org.lushen.zhuifeng.http.proxy.bus;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.lushen.zhuifeng.http.proxy.server.HttpProxyExecutor;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * 自定义http事务执行器
 * 
 * @author hlm
 */
@Component
public class HelloExecutor implements HttpProxyExecutor {

	@Override
	public HttpResponse doService(HttpRequest request) throws Exception {	
		String message = String.format("{\"answer\":\"%s请求!\"}", request.method().name());
		ByteBuf buf = Unpooled.wrappedBuffer(message.getBytes());
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, buf);
		response.headers().set(CONNECTION, KEEP_ALIVE);
		response.headers().set(CONTENT_TYPE, "application/json; charset=utf-8");
		response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
		return response;
	}

}
