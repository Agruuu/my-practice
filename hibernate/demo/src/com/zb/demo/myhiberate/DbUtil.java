package com.zb.demo.myhiberate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//����һ��Connection����Ҳֻ��һ��Connection����
public class DbUtil {
	//˽�й��캯������˼�ǽ�ֹ�������ø����ʵ��
	private DbUtil(){}
	//���ݿ����ӵ���Դ�ǳ�����,������������ֻ�����һ��
	//Connection��������,��ʵ�ʹ�����,����Ӧ�������￼��
	//ʹ���ڲ��๹��һ�����ӳ�
	private static Connection conn;
	static {
		try {
			Class.forName(Configuration.getDriverClass());
			conn = DriverManager.getConnection(Configuration.getUrl(),Configuration.getUsername(),Configuration.getPassword());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static Connection getConnection(){
		//���ݱ�Ż�ó��е����Ӷ���
		return conn;
	}
	public static void main(String[] args) {
		System.out.println(DbUtil.getConnection());
	}
	/*�ڲ��๹����ʽ
	*�ھ������������,��һ��Ҫ�Լ��������ӳ�,���Խ���
	*���������ӳؼ���,������������ʹ��JNDI�����������ӳ�
	*���ߵ�������ܽ��Դ����ӳؼ����Ƚ����е���:dbcp,c3p0
	*proxool��
	*/
	private static class Pool{
		public static Connection [] conns;
	}
}
