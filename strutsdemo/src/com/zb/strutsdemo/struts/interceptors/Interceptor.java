package com.zb.strutsdemo.struts.interceptors;

import javax.servlet.http.HttpServletRequest;

//���ӿ��Ǵ�����ϵ����ؽӿ�
//Ŀ�����ڴ��������ʱ�ṩһ��ͳһ��
//�������ҵ��õĹ�ͬ����
//���󷽷���ǰ������
public interface Interceptor {
	
	public Object intercept(HttpServletRequest request,Object action);
}
