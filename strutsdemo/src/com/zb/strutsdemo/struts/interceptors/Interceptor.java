package com.zb.strutsdemo.struts.interceptors;

import javax.servlet.http.HttpServletRequest;

//本接口是代理组合的拦截接口
//目的是在代理对象工作时提供一个统一的
//所有自我调用的共同方法
//对象方法的前后拦截
public interface Interceptor {
	
	public Object intercept(HttpServletRequest request,Object action);
}
