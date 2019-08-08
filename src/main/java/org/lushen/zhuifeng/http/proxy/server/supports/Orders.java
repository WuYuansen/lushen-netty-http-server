package org.lushen.zhuifeng.http.proxy.server.supports;

/**
 * filter排序权重常量
 * 
 * @author helm
 */
public class Orders {

	/**
	 * debug日志打印filter排序权重
	 */
	public static final int DEBUG_ORDER = Integer.MIN_VALUE;

	/**
	 * User-Agent白名单filter排序权重
	 */
	public static final int USER_AGENT_ORDER = DEBUG_ORDER + 1;

	/**
	 * 客户端ip白名单filter排序权重
	 */
	public static final int WHITE_LIST_IPS_ORDER = DEBUG_ORDER + 1;

	/**
	 * http方法白名单filter排序权重
	 */
	public static final int ALLOW_HTTP_METHOD_ORDER = DEBUG_ORDER + 5;

	/**
	 * 允许options请求filter排序权重
	 */
	public static final int ALLOW_OPTIONS_METHOD_ORDER = DEBUG_ORDER + 9;

}
