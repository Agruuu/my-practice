package com.zb.demo.myhiberate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
/**
 * 
 * 本类任务是使用PreparedStatement接口处理用户的数据库
 * 操作请求
 *
 */
public class DbManager {
	private Connection conn;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	public DbManager(){
		conn = DbUtil.getConnection();
	}
	public int update(String sql,Object [] params){
		int count = 0 ;
		try {
			pstat = conn.prepareStatement(sql);
			for (int i=1;i<=params.length;i++){
				pstat.setObject(i, params[i-1]);
			}
			count = pstat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public ResultSet query(String sql,Object [] params){
		try {
			pstat = conn.prepareStatement(sql);
			for (int i=1;i<=params.length;i++){
				pstat.setObject(i, params[i-1]);
			}
			//pstat.setDate(1,new java.sql.Date("2012-5-6"));
			rs = pstat.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public void destory(){
		try {
			if (conn!=null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
//		String sql="select * from userinfos where userid=?";
//		DbManager dbm = new DbManager();
//		System.out.println(dbm.query(sql, new Object[]{2}));
		String sql="insert into userinfos values(?,?,?,?)";
		DbManager dbm = new DbManager();
		
		dbm.update(sql, new Object[]{3,"abc","123",new Date(2012,7,6)});
	}
}
