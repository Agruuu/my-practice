package com.zb.strutsdemo.struts.interceptors;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

//����������
//�����ǽ������еĲ�����䵽��Ӧ���Զ���Action��
public class ParamInterceptor implements Interceptor {
	//����ö��
	enum TypeEnum{
		STRING,DOUBLE,FLOAT,DATE,INT,INTEGER
	}
	
	public Object intercept(HttpServletRequest request, Object action) {
		//ȡ��action�������������
		Class cls = action.getClass();
		Field [] fields = cls.getDeclaredFields();
		for (Field field:fields){
			field.setAccessible(true);
			fillAttributes(request,action,field);
		}
		return action;
	}

	//��������������
	private void fillAttributes(HttpServletRequest request, Object action,
			Field field) {
		//��ȡ�����Ե����ƺ�����
		String fieldname = field.getName();
		TypeEnum typeEnum = TypeEnum.valueOf(field.getType().getSimpleName().toUpperCase());
		//��ȡ�����е�ֵ
		String param = request.getParameter(fieldname);
		try {
			switch(typeEnum){
				case INT:
				case INTEGER:
					field.setInt(action, Integer.parseInt(param));
					break;
				case DOUBLE:
					field.setDouble(action, Double.parseDouble(param));
					break;
				case FLOAT:
					field.setFloat(action, Float.parseFloat(param));
					break;
				case STRING:
					field.set(action, param);
					break;
				case DATE:
					//��������ת������
					field.set(action, parseDate(param));
					break;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//��������ת��
	private Date parseDate(String param) {
		String [] date = param.split("[-|/|��|��|��|\\.]");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
		return cal.getTime();
	}

}
