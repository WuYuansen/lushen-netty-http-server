	
#### 介绍

	基于netty整合了springboot配置管理方式.
	
#### 快速启动

	执行入口：
	
		org.lushen.zhuifeng.http.proxy.HttpServerStarter
	
	自定义三个组件进行业务接入(代理事务执行器、代理异常处理器、代理过滤器)，已自带默认实现：
	
		org.lushen.zhuifeng.http.proxy.server.HttpProxyExecutor
		org.lushen.zhuifeng.http.proxy.server.HttpProxyErrorResolver
		org.lushen.zhuifeng.http.proxy.server.HttpProxyFilter
	
	接入自定义业务，请实现事务执行器和异常处理器，并配置为spring bean.
	
	接入自定义过滤规则，请实现过滤器，并配置为spring bean.
	
	接入示例请查看：org.lushen.zhuifeng.http.proxy.bus.HelloExecutor
	
	接入配置示例请查看：application.yml：
		
		①，org.lushen.zhuifeng.http.proxy.port=8080
		②，org.lushen.zhuifeng.http.proxy.acceptors=2
		③，org.lushen.zhuifeng.http.proxy.workers=500
		④，org.lushen.zhuifeng.http.proxy.white-list-user-agents=
		⑤，org.lushen.zhuifeng.http.proxy.white-list-ips=
		⑥，org.lushen.zhuifeng.http.proxy.allow-methods=POST, GET
		 
		①，代理服务器监听端口
		②，代理服务器IO线程数
		③，代理服务器工作线程数
		④，代理服务器客户端User-Agent白名单
		⑤，代理服务器ip白名单
		⑥，代理服务器请求方法白名单


