package com.zb.strutsdemo.struts;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zb.strutsdemo.struts.interceptors.Interceptor;
import com.zb.strutsdemo.struts.interceptors.ParamInterceptor;

public class Dispatcher {
	
	private ConfigurationManager configuration;
	private FilterConfig config;
	
	public Dispatcher(FilterConfig config){
		configuration = new ConfigurationManager();
		this.config = config;
	}
	
	// 调用 configurationManager, 获取 Map<String, ActionMapping>
	// 并将结果存放在 
	public void init(){
		Map<String, ActionMapping> acts = configuration.getActionMappings();
		config.getServletContext().setAttribute("actions", acts);
	}
	
	// 获取用户调用的类
	public boolean prepared(HttpServletRequest request){
		
		// 获取请求地址 例如：http://localhost:8888/demo/login.action?
		String url = request.getRequestURL().toString();
		if(url.lastIndexOf(".action") == -1){
			return false;
		} else {
			return true;
		}
	}
	
	// 执行你的 Action
	public void servletAction(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			ActionMapping mapping) throws IOException,ServletException {
		// 获取跳转路径
		String path ="";
		//获取拦截器信息配置类
		Configuration config = configuration.getConfiguration(mapping);
		ActionProxy proxy = config.getProxy(mapping,request);
		path = proxy.createProxy().execute();
		// 跳转
		request.getRequestDispatcher(path).forward(request, response);
		
	}
}































