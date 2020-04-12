package com.zb.demo.myhiberate;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ����Oracle����������session��Ʒ
 * �����ʵ����Ӧ�����������������,
 * Ϊ�˼�ʵ�ֲ���,�������ǽ���Щ���
 * ���������ڱ�����
 * �����ʵ������һ�������Ʒ
 */
public class BaseHibernateDAO<T> extends ChangeArrayFromResult implements Session<T> {
	//ʵ��ӳ���ļ�������
	private  String HBMNAME=".hbm.xml";
	//attrs���Խ���Ŷ�ȡ�������е�ʵ���������
	private  List<Attributes> attrs= new ArrayList<Attributes>();
	//xxx.hbm.xml�ļ���class��ǩ��table���Եı���
	private  String TABLENAME;
	//xxx.hbm.xml�ļ���class��ǩ��name�Ķ���������Ϣ��
	private  Class entityClass;
	//��������
	Object [] params =null;
	public BaseHibernateDAO(){
		fillName();
	}
	
	//��ȡxxx.hbm.xml�ļ�
	private  void fillName(){
		/**
		 * ��Ҫ���ʵ��ӳ���ļ���ȫ�ļ�������:Userinfos.hbm.xml
		 * ��Ϊ����ļ̳���һ�㶼��XxxxxDAO��,��������ֻҪ���
		 * ʵ�������ȫ�޶��������磺com.zb.demo.dao.UserinfosDAO
		 * �������е�.�滻��/,��dao�����滻��entity,�ٽ�
		 * ����DAOȥ��,��������.hbm.xml���ɻ��ʵ��ӳ���ļ�
		 * ��ȫ�޶���
		 */
		String name = this.getClass().getName().replace(".", "/");
		name = name.replace("dao", "entity");
		try {
			String path = URLDecoder.decode(BaseHibernateDAO.class.getResource("/hiberate.cfg.xml").getPath(),"UTF-8");
			System.out.println(path);
			HBMNAME =path.substring(0,path.lastIndexOf("/") + 1)+name.substring(0,name.length()-3)+HBMNAME;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(HBMNAME);
		//��ȡxxxx.hbm.xml�ļ�,���Lisst<Attributes>����
		readRoot();
	}
	
	//����Dom4j��ȡ��Ӧ��xxx.hbm.xml�ļ�
	private void readRoot(){
		if (attrs.isEmpty()){
			SAXReader reader = new SAXReader();
			Document root=null;
			try {
				root = reader.read(new File(HBMNAME));
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Element element = root.getRootElement();
			//��ȡ�����е�class�ڵ�
			Element currentClass = element.element("class");
			//��ñ���
			TABLENAME = currentClass.attributeValue("table");
			//���ʵ���������
			try {
				entityClass = Class.forName(currentClass.attributeValue("name"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//�ݹ�ʵ��List��� 
			fillList(currentClass);
		}
	}
	//���List<Attributes>����
	private void fillList(Element currentNode){
		List<Element> nodes = currentNode.elements();
		for (Element node:nodes){
			Attributes attributes = new Attributes();
			attributes.setName(node.attributeValue("name"));
			attributes.setType(node.attributeValue("type"));
			//������һ��column,generator
			List<Element> cg = node.elements();
			for (Element no:cg){
				if (no.getName().equals("column")){
					attributes.setColumnname(no.attributeValue("name"));
				}else
				{
					attributes.setGenerator(no.getTextTrim());
				}
			}
			attrs.add(attributes);
			
		}
	}
	
	//�������ƴװ����ɾ�����
	//ͨ�����Ǹ�������ɾ��,��������ֻ��ʾ�˴�����ɾ����ƴװ
	public void delete(int id) {
		String columnname="";
		//��Ϊֻ������ӵ��Generator����,�������Ǳ������ϲ���
		//������������ȡ����
		for (Attributes ats:attrs){
			if (ats.getGenerator()!=null){
				columnname = ats.getColumnname();
			}
		}
		String sql= "delete from "+TABLENAME+" where "+columnname+"=?";
		update(sql, new Object[]{id});
		
	}
	
	public T get(Class cls, Object id) {
		String sql = "select userid,username,password,birthday from userinfos where userid = ?";
		List<Object> list = change(cls, sql, new Object[]{id});
		if(list.size()>0){
			return (T)list.get(0);
		}
		return null;
	}
	
	//�޸ĵķ���
	public void merge(T t) {
		String sql="update "+TABLENAME+" set ";
		//����һ����������
		Object [] params = new Object[attrs.size()-1];
		int flag=0;
		for (int i= attrs.size()-1;i>=0;i--){
			//��ȡ����
			String colname = attrs.get(i).getColumnname();
			//��װ���,���i==0˵������������,
			//��˾Ͳ�����װ���
			if(i!=0){
				sql+= colname+"=?,";
			}
			//������Сд���þ���ʵ������������
			//�������÷�����ʵ�����и����Ե���Ϣ
			//����ŵ���Ӧ��������
			try {
				Field field = t.getClass().getDeclaredField(colname.toLowerCase());
				field.setAccessible(true);
				//ÿ�θ������Խ�ʵ���������ֵ��ŵ�������
				params[flag++]=field.get(t);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		sql=sql.substring(0,sql.length()-1)+" where "+attrs.get(0).getColumnname()+"=?";
		update(sql,params);
	}

	//��Ա���ķ���
	public void save(T t) {
		//��ü����е�������
		String sequencename=null;
		for (Attributes ats:attrs){
			if (ats.getGenerator()!=null ){
				sequencename = ats.getGenerator();
			}
		}
		//���β�������,��һ��λ�����������,��������е�.nextval
		String sql="insert into "+TABLENAME+" values(";
		
		//��һ��������
		if(!sequencename.equals(""))
		{
			sql+=sequencename+".nextval";
			sql=saveHelp(sql, attrs.size()-1, t);
		}else
		{
			//�����һ�в�������
			sql=saveHelp(sql, attrs.size(), t);
		}
		System.out.println(params.length);
		update(sql,params);
		
	}
	//������ƴװ�������
	private String saveHelp(String sql,int end,T t){
		params =  new Object[end];
		try {
			for (int i=0;i<end;i++){
				//��ֹ��û�����е������,��ĵ�һ������ǰӦ��û�ж���
				sql+=(end==attrs.size() && i==0)?"?":",?";
				Field field=null;
				//�����������ӵڶ������Կ�ʼ��ȡ,���û��������ӵ�һ�����Կ�ʼ��ȡ
				field= t.getClass()
						.getDeclaredField(
								attrs.get((end!=attrs.size())?i+1:i).getName());
				field.setAccessible(true);
				//���Ԥ�������Ĳ���,�����java.util.Date��
				//ת����java.sql.Date
				params[i]=field.getType()
						.getSimpleName()
						.toUpperCase()
						.equals("DATE")?changeDate((java.util.Date)field.get(t)):field.get(t);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql+=")";
		return sql;
	}
	
	//����װ��java.util.DateתΪjava.sql.Date
	private java.sql.Date changeDate(java.util.Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return new java.sql.Date(cal.getTimeInMillis());
	}
	//��װ��ѯ���
	//��ѯ������Ϣ
	public List<T> findAll(){
		String sql="select * from "+TABLENAME;
		return change(entityClass,sql,new Object[]{});
	}
	//��ѯ���е���Ϣ
	public List<T> findByProperty(String columnName,Object val){
		String sql="select * from "+TABLENAME+" where "+columnName+"=?";
		String param=val.toString();
		//��ȡ�������������
		String typename =  val.getClass().getSimpleName().toUpperCase();
		if (typename.trim().equals("DATE")){
			return change(entityClass,sql,new Object[]{changeDate((java.util.Date)val)});	
		}
		return change(entityClass,sql,new Object[]{param});
	}
}
