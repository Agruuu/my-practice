package com.zb.demo.myhiberate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 读取hibernate.cfg.xml文件
 * 获取数据库连接配置
 * 并将自身作为一个数据的载体
 * Configuration是一个驱动配置类
 * 同时该类能够根据方言产生对应的工厂对象
 */
public class Configuration {
	private static final String CONFIG="/hiberate.cfg.xml"; 
	
	/*
	 * dialect是方言的意思, 它主要的目的是在将来的抽象工厂
	 * 决定实现的是哪个具体的实例工厂
	 * （抽象工厂将决定产生的是哪种数据库的管理session）
	 */
	private static String dialect;
	private static String driverClass;
	private static String url;
	private static String username;
	private static String password;
	public static String getDialect() {
		return dialect;
	}
	public static void setDialect(String dialect) {
		Configuration.dialect = dialect;
	}
	public static String getDriverClass() {
		return driverClass;
	}
	public static void setDriverClass(String driverClass) {
		Configuration.driverClass = driverClass;
	}
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		Configuration.url = url;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		Configuration.username = username;
	}
	
	//在静态块中读取hibernate.cfg.xml文件是为了降低资源消耗
	//对运行中不变的资源文件自动加载,而且只加载一次
	static{
		readCfgFile();
	}
	
	/*
	 * 通过使用递归算法找到文件的叶节点,并获取节点的属性
	 */
	public static void readerXml(Element e){
		
		List<Element> list = e.elements();
		//如果list不等于空,说明该节点不是叶节点
		//同时,如果叶节点不是property就不做任何事情
		if (list != null && !list.get(0).getName().equals("property"))
		{
			for (Element el:list){
				System.out.println(e.getName());
				//递归调用子节点
				readerXml(el);
			}
		}else
		{
			for(Element property:list){
				//当前元素是Property 一定有属性name
				//将name属性提取出来，并转换成枚举类型
				DriverEnum type=DriverEnum.valueOf(property.attributeValue("name").toUpperCase());
				switch(type)
				{
					case DIALECT:
						dialect=property.getTextTrim();
						break;
					case DRIVERCLASS:
						driverClass =property.getTextTrim(); 
						break;
					case USERNAME:
						username =property.getTextTrim();
						break;
					case PASSWORD:
						password = property.getTextTrim();
						break;
					case URL:
						url =property.getTextTrim();
						break;
				}
			}
		}
	}
	
	/*
	 *readCfgFile方法只负责通过Dom4j组件获得hibernate.cfg.xml
	 *文件的根节点hibernate-configuration,
	 *并交付给readerXml方法
	 */
	private static void readCfgFile() {
		// TODO Auto-generated method stub
		try {
			String path = URLDecoder.decode(Configuration.class.getResource(CONFIG).getPath(),"UTF-8");
			SAXReader reader = new SAXReader();
			try {
				Document document = reader.read(path);
				Element root = document.getRootElement();
				readerXml(root);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			System.out.println(URLDecoder.decode(Configuration.class.getResource(CONFIG).getPath(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 简单工厂模式生成你所需要的工厂
	 */
	public SessionFactory buildSessionFactory(){
		Object obj=null;
		try {
			obj = Class.forName(dialect).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (SessionFactory)obj;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		Configuration.password = password;
	}
	
	
}
