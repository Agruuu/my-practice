package com.zb.demo.myhiberate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ��ȡhibernate.cfg.xml�ļ�
 * ��ȡ���ݿ���������
 * ����������Ϊһ�����ݵ�����
 * Configuration��һ������������
 * ͬʱ�����ܹ����ݷ��Բ�����Ӧ�Ĺ�������
 */
public class Configuration {
	private static final String CONFIG="/hiberate.cfg.xml"; 
	
	/*
	 * dialect�Ƿ��Ե���˼, ����Ҫ��Ŀ�����ڽ����ĳ��󹤳�
	 * ����ʵ�ֵ����ĸ������ʵ������
	 * �����󹤳����������������������ݿ�Ĺ���session��
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
	
	//�ھ�̬���ж�ȡhibernate.cfg.xml�ļ���Ϊ�˽�����Դ����
	//�������в������Դ�ļ��Զ�����,����ֻ����һ��
	static{
		readCfgFile();
	}
	
	/*
	 * ͨ��ʹ�õݹ��㷨�ҵ��ļ���Ҷ�ڵ�,����ȡ�ڵ������
	 */
	public static void readerXml(Element e){
		
		List<Element> list = e.elements();
		//���list�����ڿ�,˵���ýڵ㲻��Ҷ�ڵ�
		//ͬʱ,���Ҷ�ڵ㲻��property�Ͳ����κ�����
		if (list != null && !list.get(0).getName().equals("property"))
		{
			for (Element el:list){
				System.out.println(e.getName());
				//�ݹ�����ӽڵ�
				readerXml(el);
			}
		}else
		{
			for(Element property:list){
				//��ǰԪ����Property һ��������name
				//��name������ȡ��������ת����ö������
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
	 *readCfgFile����ֻ����ͨ��Dom4j������hibernate.cfg.xml
	 *�ļ��ĸ��ڵ�hibernate-configuration,
	 *��������readerXml����
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
	 * �򵥹���ģʽ����������Ҫ�Ĺ���
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
