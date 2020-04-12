package com.zb.demo.myhiberate;

/**
 * Oracle数据库的Session工厂
 * 本类的任务是通过openSession方法产生
 * 数据库对应的语句的分装对象
 */
public class OracleDialect implements SessionFactory {

	//产生Oracle的Session
	public Session openSession() {
		// TODO Auto-generated method stub
		return new BaseHibernateDAO();
	}

}
