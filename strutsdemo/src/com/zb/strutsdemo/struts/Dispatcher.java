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
	
	// ���� configurationManager, ��ȡ Map<String, ActionMapping>
	// ������������ 
	public void init(){
		Map<String, ActionMapping> acts = configuration.getActionMappings();
		config.getServletContext().setAttribute("actions", acts);
	}
	
	// ��ȡ�û����õ���
	public boolean prepared(HttpServletRequest request){
		
		// ��ȡ�����ַ ���磺http://localhost:8888/demo/login.action?
		String url = request.getRequestURL().toString();
		if(url.lastIndexOf(".action") == -1){
			return false;
		} else {
			return true;
		}
	}
	
	// ִ����� Action
	public void servletAction(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			ActionMapping mapping) throws IOException,ServletException {
		// ��ȡ��ת·��
		String path ="";
		//��ȡ��������Ϣ������
		Configuration config = configuration.getConfiguration(mapping);
		ActionProxy proxy = config.getProxy(mapping,request);
		path = proxy.createProxy().execute();
		// ��ת
		request.getRequestDispatcher(path).forward(request, response);
		
	}
}































