package com.zb.demo.myhiberate;

//MySql��Session����
public class MySqlDialect implements SessionFactory {
	//MySql��Session
	public Session openSession() {
		// TODO Auto-generated method stub
		return new MySqlSession();
	}

}
