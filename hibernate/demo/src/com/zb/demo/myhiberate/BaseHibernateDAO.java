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
 * 这是Oracle工厂产生的session产品
 * 这个类实际上应该有许多的其他类组成,
 * 为了简化实现步骤,所以我们将这些类的
 * 功能整合在本类中
 * 这个类实际上是一个具体产品
 */
public class BaseHibernateDAO<T> extends ChangeArrayFromResult implements Session<T> {
	//实体映射文件的名字
	private  String HBMNAME=".hbm.xml";
	//attrs属性将存放读取到得所有的实体类的属性
	private  List<Attributes> attrs= new ArrayList<Attributes>();
	//xxx.hbm.xml文件中class标签中table属性的表名
	private  String TABLENAME;
	//xxx.hbm.xml文件中class标签的name的对象类型信息类
	private  Class entityClass;
	//参数数组
	Object [] params =null;
	public BaseHibernateDAO(){
		fillName();
	}
	
	//读取xxx.hbm.xml文件
	private  void fillName(){
		/**
		 * 先要获得实体映射文件的全文件名例如:Userinfos.hbm.xml
		 * 因为本类的继承者一般都是XxxxxDAO类,所以我们只要获得
		 * 实现子类的全限定名字例如：com.zb.demo.dao.UserinfosDAO
		 * 并将所有的.替换成/,将dao包名替换到entity,再将
		 * 最后的DAO去处,并连接上.hbm.xml即可获得实体映射文件
		 * 的全限定名
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
		//读取xxxx.hbm.xml文件,填充Lisst<Attributes>集合
		readRoot();
	}
	
	//利用Dom4j读取相应的xxx.hbm.xml文件
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
			//获取配置中的class节点
			Element currentClass = element.element("class");
			//获得表名
			TABLENAME = currentClass.attributeValue("table");
			//获得实体对象类型
			try {
				entityClass = Class.forName(currentClass.attributeValue("name"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//递归实现List填充 
			fillList(currentClass);
		}
	}
	//填充List<Attributes>集合
	private void fillList(Element currentNode){
		List<Element> nodes = currentNode.elements();
		for (Element node:nodes){
			Attributes attributes = new Attributes();
			attributes.setName(node.attributeValue("name"));
			attributes.setType(node.attributeValue("type"));
			//进入下一层column,generator
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
	
	//这个方法拼装的是删除语句
	//通常我们根据主键删除,所以这里只演示了从主键删除的拼装
	public void delete(int id) {
		String columnname="";
		//因为只有主键拥有Generator属性,所以我们遍历集合并将
		//主键的列名获取出来
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
	
	//修改的方法
	public void merge(T t) {
		String sql="update "+TABLENAME+" set ";
		//产生一个参数数组
		Object [] params = new Object[attrs.size()-1];
		int flag=0;
		for (int i= attrs.size()-1;i>=0;i--){
			//获取列名
			String colname = attrs.get(i).getColumnname();
			//组装语句,如果i==0说明是最后的主键,
			//因此就不再组装语句
			if(i!=0){
				sql+= colname+"=?,";
			}
			//将列名小写正好就是实体对象的属性名
			//所以利用反射获得实体类中该属性的信息
			//并存放到相应的数组中
			try {
				Field field = t.getClass().getDeclaredField(colname.toLowerCase());
				field.setAccessible(true);
				//每次根据属性将实体类的属性值存放到数组中
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

	//针对保存的方法
	public void save(T t) {
		//获得集合中的序列名
		String sequencename=null;
		for (Attributes ats:attrs){
			if (ats.getGenerator()!=null ){
				sequencename = ats.getGenerator();
			}
		}
		//依次插入数据,第一个位置如果是序列,就添加序列的.nextval
		String sql="insert into "+TABLENAME+" values(";
		
		//第一列是序列
		if(!sequencename.equals(""))
		{
			sql+=sequencename+".nextval";
			sql=saveHelp(sql, attrs.size()-1, t);
		}else
		{
			//如果第一列不是序列
			sql=saveHelp(sql, attrs.size(), t);
		}
		System.out.println(params.length);
		update(sql,params);
		
	}
	//填充参数拼装保存语句
	private String saveHelp(String sql,int end,T t){
		params =  new Object[end];
		try {
			for (int i=0;i<end;i++){
				//防止在没有序列的情况下,你的第一个参数前应该没有逗号
				sql+=(end==attrs.size() && i==0)?"?":",?";
				Field field=null;
				//如果有序列则从第二个属性开始获取,如果没有序列则从第一个属性开始获取
				field= t.getClass()
						.getDeclaredField(
								attrs.get((end!=attrs.size())?i+1:i).getName());
				field.setAccessible(true);
				//填充预处理语句的参数,如果是java.util.Date则
				//转换成java.sql.Date
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
	
	//日期装换java.util.Date转为java.sql.Date
	private java.sql.Date changeDate(java.util.Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return new java.sql.Date(cal.getTimeInMillis());
	}
	//组装查询语句
	//查询所有信息
	public List<T> findAll(){
		String sql="select * from "+TABLENAME;
		return change(entityClass,sql,new Object[]{});
	}
	//查询单列的信息
	public List<T> findByProperty(String columnName,Object val){
		String sql="select * from "+TABLENAME+" where "+columnName+"=?";
		String param=val.toString();
		//获取传入参数的类型
		String typename =  val.getClass().getSimpleName().toUpperCase();
		if (typename.trim().equals("DATE")){
			return change(entityClass,sql,new Object[]{changeDate((java.util.Date)val)});	
		}
		return change(entityClass,sql,new Object[]{param});
	}
}
