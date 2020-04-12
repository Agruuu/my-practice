package com.zb.demo.myhiberate;

//MySql的Session工厂
public class MySqlDialect implements SessionFactory {
	//MySql的Session
	public Session openSession() {
		// TODO Auto-generated method stub
		return new MySqlSession();
	}

}
