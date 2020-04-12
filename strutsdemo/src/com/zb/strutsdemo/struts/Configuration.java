package com.zb.strutsdemo.struts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zb.strutsdemo.struts.interceptors.Interceptor;

//保存用户定义或系统自定义的拦截器集合
//并返回ActionProxy
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
