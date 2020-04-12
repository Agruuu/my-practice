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
		//��ȡ��ʵ����
		//ʵ�������û����Զ����Action��,ֻ������DefaultActionȥ����
		Action action = new DefaultAction(mapping);
		//�����������
		//���û��Ķ��󷽷���ǰ����ϸ������ض���
		ActionInvocation invocation = new ActionInvocation(action,request,inters);
		return (Action)invocation.getProxy();
	}
	
}
