package com.zb.demo.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class cls=Class.forName("com.zb.demo.test.MyClass"); 
			Object obj = cls.newInstance();
			Field [] fields = cls.getDeclaredFields();
			Method [] methods= cls.getDeclaredMethods();
			fields[0].setAccessible(true);
			fields[0].set(obj, "22");
			
			Object o = methods[0].invoke(obj,new Object[]{"cm"});
			System.out.println(o);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
