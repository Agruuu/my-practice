package com.zb.demo.myhiberate;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 将ResultSet转化为List<T>
 */
public class ChangeArrayFromResult extends DbManager{
	
	public  <T>List<T> change(Class cls,String sql,Object[] params){
		ResultSet rs = query(sql,params);
		List<T> list = new ArrayList<T>();
		
		try {
			while (rs.next()){
				//根据类型信息获得对象
				Object obj = cls.newInstance();
				//获取对象所有属性
				Field [] fields = cls.getDeclaredFields();
				//填充obj对象属性
				for (Field ftype:fields) {
					ftype.setAccessible(true);
					//获取属性类型并转换成类型枚举
					TypeEnum te = TypeEnum.valueOf(ftype.getType().getSimpleName().toUpperCase());
					switch(te){
						case INT:
						case INTEGER:
							ftype.setInt(obj, rs.getInt(ftype.getName()));
							break;
						case FLOAT:
							ftype.setFloat(obj,rs.getFloat(ftype.getName()));
							break;
						case DOUBLE:
							ftype.setDouble(obj, rs.getDouble(ftype.getName()));
							break;
						case DATE:
							
							ftype.set(obj, rs.getDate(ftype.getName()));
							break;
						default :
							ftype.set(obj, rs.getString(ftype.getName()));
					}
				}
				//利用反射获得实体类中属性信息(name,type)
				//因为属性名和数据库表中的列名完全一致
				
				list.add((T)obj);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
