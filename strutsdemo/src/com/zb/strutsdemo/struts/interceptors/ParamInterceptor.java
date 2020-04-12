package com.zb.strutsdemo.struts.interceptors;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

//参数拦截类
//任务是将请求中的参数填充到对应的自定义Action中
public class ParamInterceptor implements Interceptor {
	//类型枚举
	enum TypeEnum{
		STRING,DOUBLE,FLOAT,DATE,INT,INTEGER
	}
	
	public Object intercept(HttpServletRequest request, Object action) {
		//取出action对象的所有属性
		Class cls = action.getClass();
		Field [] fields = cls.getDeclaredFields();
		for (Field field:fields){
			field.setAccessible(true);
			fillAttributes(request,action,field);
		}
		return action;
	}

	//根据请求填充对象
	private void fillAttributes(HttpServletRequest request, Object action,
			Field field) {
		//获取本属性的名称和类型
		String fieldname = field.getName();
		TypeEnum typeEnum = TypeEnum.valueOf(field.getType().getSimpleName().toUpperCase());
		//获取请求中的值
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
					//日期类型转化方法
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

	//日期类型转化
	private Date parseDate(String param) {
		String [] date = param.split("[-|/|年|月|日|\\.]");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
		return cal.getTime();
	}

}
