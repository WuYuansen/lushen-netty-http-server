package org.lushen.zhuifeng.http.proxy.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.lushen.zhuifeng.http.proxy.server.supports.DefaultHttpProxyFilterChain;
import org.lushen.zhuifeng.http.proxy.utils.AssertUtils;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * http 代理处理逻辑
 * 
 * @author helm
 */
@Sharable
public class HttpProxyHandler extends ChannelInboundHandlerAdapter {

	private final List<HttpProxyFilter> httpProxyFilters;			//过滤器集合

	private final HttpProxyErrorResolver httpProxyErrorResolver;	//异常处理器

	public HttpProxyHandler(
			List<HttpProxyFilter> httpProxyFilters,
			HttpProxyExecutor httpProxyExecutor, 
			HttpProxyErrorResolver httpProxyErrorResolver) {
		super();
		AssertUtils.nonNull(httpProxyFilters, () -> new IllegalArgumentException("http proxy filters can't be null or contains null !"));
		AssertUtils.notNull(httpProxyExecutor, () -> new IllegalArgumentException("http proxy executor can't be null !"));
		AssertUtils.notNull(httpProxyErrorResolver, () -> new IllegalArgumentException("http proxy error-resolver can't be null !"));
		this.httpProxyErrorResolver = httpProxyErrorResolver;
		this.httpProxyFilters = initializeFilters(httpProxyFilters, httpProxyExecutor);
	}

	//初始化filter集合
	private List<HttpProxyFilter> initializeFilters(List<HttpProxyFilter> httpProxyFilters, HttpProxyExecutor httpProxyExecutor) {

		//初始化filters
		List<HttpProxyFilter> filters = new ArrayList<HttpProxyFilter>();
		filters.addAll(httpProxyFilters);

		//根据权重排序
		filters.sort(Comparator.comparingInt(HttpProxyFilter::getOrder));

		//包装http事务处理器为最后一个filter
		filters.add(new HttpProxyFilter() {
			@Override
			public void doFilter(ChannelHandlerContext context, HttpRequest request, HttpProxyFilterChain chain) throws Exception {
				HttpResponse response = httpProxyExecutor.doService(request);
				context.write(response);
				context.flush();
			}
		});

		return Collections.unmodifiableList(filters);
	}

	@Override
	public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
		//只接收http请求
		if(msg instanceof HttpRequest) {
			HttpRequest request = (HttpRequest) msg;
			try {
				new DefaultHttpProxyFilterChain(this.httpProxyFilters).invoke(context, request);
			} catch (Throwable cause) {
				this.httpProxyErrorResolver.resolve(context, request, cause);
				throw cause;
			}
		} else {
			context.close();
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext context) throws Exception {
		context.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
		context.close();
	}

}
