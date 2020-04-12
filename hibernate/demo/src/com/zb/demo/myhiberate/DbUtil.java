package com.zb.demo.myhiberate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//返回一个Connection而且也只有一个Connection对象
public class DbUtil {
	//私有构造函数的意思是禁止其他类获得该类的实例
	private DbUtil(){}
	//数据库链接的资源非常宝贵,所以我们这里只会产生一个
	//Connection对象引用,在实际工作中,我们应该在这里考虑
	//使用内部类构建一个连接池
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
		//根据编号获得池中的链接对象
		return conn;
	}
	public static void main(String[] args) {
		System.out.println(DbUtil.getConnection());
	}
	/*内部类构建方式
	*在绝大多数工程中,不一定要自己定义连接池,可以借助
	*第三方连接池技术,例如在容器中使用JNDI技术配置连接池
	*或者第三方框架将自带连接池技术比较流行的如:dbcp,c3p0
	*proxool等
	*/
	private static class Pool{
		public static Connection [] conns;
	}
}
