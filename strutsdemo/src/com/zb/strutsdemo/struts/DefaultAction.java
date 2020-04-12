package com.zb.strutsdemo.struts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//真实的用户对象
public class DefaultAction implements Action {
	private ActionMapping mapping;
	//用户的Action
	private Object action;
	public DefaultAction(ActionMapping action){
		this.mapping = action;
		try {
			this.action = Class.forName(action.getClassname()).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//执行Action方法并返回路径结果
	public String execute() {
		Object res=null;
		try {
			Method execute= action.getClass().getMethod("execute",null);
			res = execute.invoke(action, null);
			//取出你所有需要跳转的路径
			res = mapping.getResults().get(res);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res.toString();
	}
	public Object getAction() {
		return action;
	}
	
	

}
