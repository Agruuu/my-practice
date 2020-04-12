package com.zb.strutsdemo.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zb.strutsdemo.struts.interceptors.Interceptor;

public class ActionProxy {
	private HttpServletRequest request;
	private ActionMapping mapping;
	private List<Interceptor> inters;
	public ActionProxy(ActionMapping actionMapping,HttpServletRequest req,List<Interceptor> inters){
		this.request = req;
		this.mapping = actionMapping;
		this.inters = inters;
	}
	
	public Action createProxy(){
		//获取真实对象
		//实际上是用户的自定义的Action类,只不过由DefaultAction去调用
		Action action = new DefaultAction(mapping);
		//产生代理对象
		//在用户的对象方法的前后加上各种拦截对象
		ActionInvocation invocation = new ActionInvocation(action,request,inters);
		return (Action)invocation.getProxy();
	}
	
}
