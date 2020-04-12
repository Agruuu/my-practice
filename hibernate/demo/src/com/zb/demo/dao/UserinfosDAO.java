package com.zb.demo.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zb.demo.entity.Userinfos;
import com.zb.demo.myhiberate.BaseHibernateDAO;

//将来要操纵数据的类
public class UserinfosDAO extends BaseHibernateDAO{
	public String s;
	public static void main(String[] args) {
		UserinfosDAO udao = new UserinfosDAO();
		Userinfos usr = (Userinfos)udao.get(Userinfos.class, 10001);
		System.out.println(usr.getUserid()+"-----"+usr.getUsername());
		
//		List<Userinfos> list = udao.findByProperty("userid", 50);
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i).getUserid()+"-----"+list.get(i).getUsername());
//		}
//		udao.fillName();
//		System.out.println(udao.s);
		
//		Userinfos user = new Userinfos();
//		user.setUsername("rrrr");
//		user.setPassword("rrr");
//		user.setBirthday(new Date());
//		udao.save(user);
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			System.out.println(udao.findByProperty("birthday", sdf.parse("2012-6-5")));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Calendar cal = Calendar.getInstance();
//		cal.set(2012, 8,10);
//		System.out.println(udao.findByProperty("birthday", cal.getTime()));
	}
}
