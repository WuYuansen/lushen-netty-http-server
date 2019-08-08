package org.lushen.zhuifeng.http.proxy.utils;

import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

/**
 * netty 工具类
 * 
 * @author hlm
 */
public class NettyUtils {

	/**
	 * 读取请求body
	 * 
	 * @param request
	 * @return
	 */
	public static final String readHttpBody(HttpRequest request) {
		if(request instanceof FullHttpRequest) {
			ByteBuf in = ((FullHttpRequest)request).content();
			try {
				return new String(ByteBufUtil.getBytes(in));
			} finally {
				in.release();
			}
		} else {
			return StringUtils.EMPTY;
		}
	}

	/**
	 * 转换http请求方法为netty http请求方法
	 * 
	 * @param methods
	 * @return
	 */
	public static final HttpMethod[] toHttpMethods(String[] methods) {
		if(methods == null) {
			return new HttpMethod[0];
		} else {
			return Arrays.stream(methods).filter(Objects::nonNull).map(HttpMethod::valueOf).toArray(len -> new HttpMethod[len]);
		}
	}

}
