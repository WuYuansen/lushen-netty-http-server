package org.lushen.zhuifeng.http.proxy.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * 断言工具类
 * 
 * @author hlm
 */
public class AssertUtils {

	/**
	 * 非null断言
	 * 
	 * @param arg
	 * @param cause
	 */
	public static final void notNull(Object arg, Supplier<RuntimeException> cause) {
		if(arg == null) {
			throw cause.get();
		}
	}

	/**
	 * 任意不为null断言
	 * 
	 * @param args
	 * @param cause
	 */
	public static final void nonNull(List<?> args, Supplier<RuntimeException> cause) {
		if(args == null) {
			throw cause.get();
		}
		args.forEach(arg -> notNull(arg, cause));
	}

	/**
	 * 任意不为null断言
	 * 
	 * @param args
	 * @param cause
	 */
	public static final void nonNull(Object[] args, Supplier<RuntimeException> cause) {
		if(args == null) {
			throw cause.get();
		}
		Arrays.stream(args).forEach(arg -> notNull(arg, cause));
	}

}
