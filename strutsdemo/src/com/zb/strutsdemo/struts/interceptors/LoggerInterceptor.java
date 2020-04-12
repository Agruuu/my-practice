package com.zb.strutsdemo.struts.interceptors;

import javax.servlet.http.HttpServletRequest;

public class LoggerInterceptor implements Interceptor {

	public Object intercept(HttpServletRequest request, Object action) {
		System.out.println("–¥»’÷æ");
		return null;
	}

}
