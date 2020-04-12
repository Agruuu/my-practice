package com.zb.demo.myhiberate;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * ��ResultSetת��ΪList<T>
 */
public class ChangeArrayFromResult extends DbManager{
	
	public  <T>List<T> change(Class cls,String sql,Object[] params){
		ResultSet rs = query(sql,params);
		List<T> list = new ArrayList<T>();
		
		try {
			while (rs.next()){
				//����������Ϣ��ö���
				Object obj = cls.newInstance();
				//��ȡ������������
				Field [] fields = cls.getDeclaredFields();
				//���obj��������
				for (Field ftype:fields) {
					ftype.setAccessible(true);
					//��ȡ�������Ͳ�ת��������ö��
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
				//���÷�����ʵ������������Ϣ(name,type)
				//��Ϊ�����������ݿ���е�������ȫһ��
				
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
