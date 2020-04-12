package com.zb.strutsdemo.struts;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 搜索存放在 servletContext 中的 ActionMapping 信息
 * @author Agru
 *
 */
public class ActionMapper {
	
	// 获取 context 中的 ActionMapping
	public ActionMapping getMapping(HttpServletRequest request, ServletContext context){
		Map<String, ActionMapping> acts = (Map<String, ActionMapping>)context.getAttribute("actions");
		return acts.get(getAlias(request));
	}
	
	// 获取请求中的类别名
	private String getAlias(HttpServletRequest request){
		String url = request.getRequestURL().toString();
		return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".action"));
	}
	
}



















