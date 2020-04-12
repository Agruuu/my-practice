package com.zb.strutsdemo.struts;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//封装request,session,servletContext
//并将这些对象存放到线程中
public class ActionContext {
	private static ThreadLocal<Object> threadRequest = new ThreadLocal<Object>();
	private static ThreadLocal<Object> threadContext = new ThreadLocal<Object>();
	
	public HttpServletRequest getRequest(){
		return (HttpServletRequest)threadRequest.get();
	}
	
	public HttpSession getSession(){
		return this.getRequest().getSession();
	}
	
	public ServletContext getContext(){
		return (ServletContext)threadContext.get();
	}
	
	public void setRequestAndWrap(HttpServletRequest request,ServletContext context){
		threadRequest.set(request);
		threadContext.set(context);
	}
	//返回自身
	public static ActionContext getActionContext(){
		return new ActionContext();
	}
}





