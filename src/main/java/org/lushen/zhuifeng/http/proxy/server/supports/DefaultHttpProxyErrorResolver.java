package org.lushen.zhuifeng.http.proxy.server.supports;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.zhuifeng.http.proxy.server.HttpProxyErrorResolver;
import org.lushen.zhuifeng.http.proxy.utils.ThrowableUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * 默认的代理异常处理器
 * 
 * @author helm
 */
public class DefaultHttpProxyErrorResolver implements HttpProxyErrorResolver {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	@Override
	public void resolve(ChannelHandlerContext context, HttpRequest request, Throwable cause) {

		//读取异常信息
		String message = ThrowableUtils.getMessage(cause);
		ByteBuf buf = Unpooled.wrappedBuffer(message.getBytes());

		log.error(message, cause);

		//输出异常信息
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.valueOf(500), buf);
		response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
		response.headers().set(CONTENT_TYPE, "text/*;charset=utf-8");
		context.write(response);
		context.flush();
	}

}
