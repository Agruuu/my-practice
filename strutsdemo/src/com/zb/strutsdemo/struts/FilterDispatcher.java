package com.zb.strutsdemo.struts;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterDispatcher implements Filter {
	
	private Dispatcher dispatcher;
	private FilterConfig config;
	private ActionMapper actionMapper;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		ServletContext context = config.getServletContext();
		//将请求,ServletContext设置到线程中,以备用户的Action调用
		ActionContext ac = ActionContext.getActionContext();
		ac.setRequestAndWrap(request, context);
		// 请求封装
		if(preparedRequestAndWrap(request)){
			// 用户请求 Action
			actionMapper = new ActionMapper();
			ActionMapping mapping = actionMapper.getMapping(request, context);
			if(mapping == null){
				// 用户的请求错误
				throw new ServletException("找不到 Action");
			} else {
				// 用户输入正确
				dispatcher.servletAction(request,response,context,mapping);
			}
		} else {
			// 用户请求的不是Action类，只是调用普通的页面
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		// 初始化调用 Dispatcher 在 application 中存放
		// 读取的 struts.xml 所有配置信息
		// servletContext 是容器上下文
		this.config = config;
		dispatcher = createDispatcher(config);
		dispatcher.init();
	}
	
	// 构建 Dispatcher 类
	public Dispatcher createDispatcher(FilterConfig config){
		return new Dispatcher(config);
	}
	
	// 请求封装
	public boolean preparedRequestAndWrap(HttpServletRequest request){
		return dispatcher.prepared(request);
	}
}









































