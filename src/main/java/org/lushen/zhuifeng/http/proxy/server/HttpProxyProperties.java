package org.lushen.zhuifeng.http.proxy.server;

import java.util.Arrays;
import java.util.Optional;

/**
 * http 代理配置
 * 
 * @author helm
 */
public class HttpProxyProperties {

	private static final Integer PROCESSORS = Runtime.getRuntime().availableProcessors();	//服务器处理器个数

	private static final Integer DEFAULT_PORT = 8080;	//默认端口

	private static final Integer DEFAULT_ACCEPTORS = Optional.of(((int)PROCESSORS/8)).map(e -> e <= 1? 1:e).get();	//默认IO处理线程数

	private static final Integer DEFAULT_WORKERS = PROCESSORS*100;	//默认工作线程数

	private Integer port = DEFAULT_PORT;			//代理服务器监听端口号

	private Integer acceptors = DEFAULT_ACCEPTORS;	//代理服务器最大监听线程数

	private Integer workers = DEFAULT_WORKERS;		//代理服务器最大处理线程数

	private String[] whiteListUserAgents;			//白名单User-Agent

	private String[] whiteListIps;					//白名单IP

	private String[] allowMethods;					//白名单请求方法

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getAcceptors() {
		return acceptors;
	}

	public void setAcceptors(Integer acceptors) {
		this.acceptors = acceptors;
	}

	public Integer getWorkers() {
		return workers;
	}

	public void setWorkers(Integer workers) {
		this.workers = workers;
	}

	public String[] getWhiteListUserAgents() {
		return whiteListUserAgents;
	}

	public void setWhiteListUserAgents(String[] whiteListUserAgents) {
		this.whiteListUserAgents = whiteListUserAgents;
	}

	public String[] getWhiteListIps() {
		return whiteListIps;
	}

	public void setWhiteListIps(String[] whiteListIps) {
		this.whiteListIps = whiteListIps;
	}

	public String[] getAllowMethods() {
		return allowMethods;
	}

	public void setAllowMethods(String[] allowMethods) {
		this.allowMethods = allowMethods;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[port=");
		builder.append(port);
		builder.append(", acceptors=");
		builder.append(acceptors);
		builder.append(", workers=");
		builder.append(workers);
		builder.append(", whiteListUserAgents=");
		builder.append(Arrays.toString(whiteListUserAgents));
		builder.append(", whiteListIps=");
		builder.append(Arrays.toString(whiteListIps));
		builder.append(", allowMethods=");
		builder.append(Arrays.toString(allowMethods));
		builder.append("]");
		return builder.toString();
	}

}
