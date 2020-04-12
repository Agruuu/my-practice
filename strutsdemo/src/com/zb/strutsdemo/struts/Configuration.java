package com.zb.strutsdemo.struts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zb.strutsdemo.struts.interceptors.Interceptor;

//�����û������ϵͳ�Զ��������������
//������ActionProxy
public class Configuration {
	private List<Interceptor> inters = new ArrayList<Interceptor>(0);

	public List<Interceptor> getInters() {
		return inters;
	}

	public void setInters(List<Interceptor> inters) {
		this.inters = inters;
	}
	
	public ActionProxy getProxy(ActionMapping mapping,HttpServletRequest request){
		return new ActionProxy(mapping,request,inters);
	}
	
}
