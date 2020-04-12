package com.zb.strutsdemo.struts;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zb.strutsdemo.struts.interceptors.Interceptor;

public class ActionInvocation implements InvocationHandler {
	private Action action;
	private List<Interceptor> param;
	private HttpServletRequest request;
	public ActionInvocation(Action targer,HttpServletRequest request,List<Interceptor> inters){
		this.action = targer;
		this.request = request;
		this.param = inters;
	}
	public Object invoke(Object obj, Method method, Object[] params)
			throws Throwable {
		//在调用用户的DefaultAction中的任何方法都会在
		//DefaultAction内部调用的用户对象的前后拦截
		for (Interceptor intept:param){
			intept.intercept(request, action.getAction());
		}
		Object result = method.invoke(action, null);
		
		return result;
	}
	public Object getProxy(){
		return Proxy.newProxyInstance(action.getClass().getClassLoader(), action.getClass().getInterfaces(), this);
	}

}
