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
		//������,ServletContext���õ��߳���,�Ա��û���Action����
		ActionContext ac = ActionContext.getActionContext();
		ac.setRequestAndWrap(request, context);
		// �����װ
		if(preparedRequestAndWrap(request)){
			// �û����� Action
			actionMapper = new ActionMapper();
			ActionMapping mapping = actionMapper.getMapping(request, context);
			if(mapping == null){
				// �û����������
				throw new ServletException("�Ҳ��� Action");
			} else {
				// �û�������ȷ
				dispatcher.servletAction(request,response,context,mapping);
			}
		} else {
			// �û�����Ĳ���Action�ֻ࣬�ǵ�����ͨ��ҳ��
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		// ��ʼ������ Dispatcher �� application �д��
		// ��ȡ�� struts.xml ����������Ϣ
		// servletContext ������������
		this.config = config;
		dispatcher = createDispatcher(config);
		dispatcher.init();
	}
	
	// ���� Dispatcher ��
	public Dispatcher createDispatcher(FilterConfig config){
		return new Dispatcher(config);
	}
	
	// �����װ
	public boolean preparedRequestAndWrap(HttpServletRequest request){
		return dispatcher.prepared(request);
	}
}









































