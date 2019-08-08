package org.lushen.zhuifeng.http.proxy.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;

/**
 * 异常工具类
 * 
 * @author helm
 */
public class ThrowableUtils {

	/**
	 * 转换异常对象为异常信息
	 * 
	 * @param cause
	 * @return
	 */
	public static final String toString(Throwable cause) {
		if(cause == null) {
			return StringUtils.EMPTY;
		} else {
			StringWriter out = new StringWriter();
			cause.printStackTrace(new PrintWriter(out));
			return out.toString();
		}
	}

	/**
	 * 获取异常信息
	 * 
	 * @param cause
	 * @return
	 */
	public static final String getMessage(Throwable cause) {
		if(cause == null) {
			return toString(cause);
		} else {
			String message = cause.getMessage();
			if(message == null) {
				if(cause.getCause() != null) {
					return getMessage(cause.getCause());
				} else {
					return toString(cause);
				}
			} else {
				return message;
			}
		}
	}

}
