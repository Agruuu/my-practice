package com.zb.strutsdemo.struts;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * ��������� servletContext �е� ActionMapping ��Ϣ
 * @author Agru
 *
 */
public class ActionMapper {
	
	// ��ȡ context �е� ActionMapping
	public ActionMapping getMapping(HttpServletRequest request, ServletContext context){
		Map<String, ActionMapping> acts = (Map<String, ActionMapping>)context.getAttribute("actions");
		return acts.get(getAlias(request));
	}
	
	// ��ȡ�����е������
	private String getAlias(HttpServletRequest request){
		String url = request.getRequestURL().toString();
		return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".action"));
	}
	
}



















