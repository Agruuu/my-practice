package com.zb.strutsdemo.struts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zb.strutsdemo.struts.interceptors.Interceptor;

/**
 * 读取配置文件
 * 
 * @author Agru
 * 
 */
public class ConfigurationManager {
	private static final String CONFIG = "/struts.xml";
	private static Element root;

	static {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new FileInputStream(getPath()));
			root = document.getRootElement();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 获取 struts.xml 文件路径
	private static String getPath() {
		try {
			String path = URLDecoder.decode(ConfigurationManager.class
					.getResource(CONFIG).getPath(), "UTF-8");
			return path;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, ActionMapping> getActionMappings() {
		Map<String, ActionMapping> acts = new HashMap<String, ActionMapping>(0);

		// 读取文件，并封装 Map 集合

		List<Element> pack = root.elements();

		// 拿出所有 Action
		List<Element> actions = pack.get(0).elements();

		// 填充 acts
		for (Element action : actions) {
			ActionMapping am = new ActionMapping();
			// 填充 别名 和 类路径
			am.setName(action.attributeValue("name"));
			am.setClassname(action.attributeValue("class"));

			// 获取 action 的所有跳转路径集合

			List<Element> results = action.elements();
			for (Element result : results) {
				am.getResults().put(result.attributeValue("name"),
						result.getTextTrim());
			}

			// 用 action 的别名(name) 别名填充 ActionMapping 的类属性
			acts.put(action.attributeValue("name"), am);
		}
		return acts;
	}
	
	//读取用户struts.xml文件组装成Configuration对象
	public Configuration getConfiguration(ActionMapping mapping){
		//存放interceptor对象的集合
		List<Interceptor> inters = new ArrayList<Interceptor>(0);
		//读取并比较配置填充inters集合
		//获取package节点
		Element pack = root.element("package");
		//获取interceptors节点
		Element inter = pack.element("interceptors");
		//获取所有的interceptor节点
		List<Element> interceptors = inter.elements("interceptor");
		//获取所有的interceptor-stack节点
		List<Element> stacks = inter.elements("interceptor-stack");
		//根据用的Action选择是默认节点还是自定义节点
		//找到用户的Action节点
		List<Element> actions = pack.elements("action");
		Element userAction=null;
		for (Element el:actions){
			if (mapping.getName().equals(getNodeAttr(el, "name"))){
				userAction = el;
				break;
			}
		}
		//查询下用户节点下是否有interceptor-ref节点
		Element defaultRef = userAction.element("interceptor-ref");
		if(defaultRef==null){
		//获取默认default-interceptor-ref节点
			defaultRef= pack.element("default-interceptor-ref");
		}
		//先获取拦截器栈(拦截器组合)
		for (Element stack:stacks){
			if (getNodeAttr(defaultRef,"name").equals(getNodeAttr(stack,"name"))){
				//如果用户已经找到默认栈的节点就去获取默认栈下面的拦截器名
				List<Element> intercepts = stack.elements("interceptor-ref");
				//与interceptors集合进行比较并实例化interceptor
				for (Element interref:intercepts){
					for(Element inte:interceptors){
						if(getNodeAttr(interref, "name").equals(getNodeAttr(inte, "name"))){
							try {
								inters.add((Interceptor)Class.forName(getNodeAttr(inte,"class")).newInstance());
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
						}
					}
				}
				break;
			}
		}
		
		//把集合存放到Configuration集合对象中
		Configuration config = new Configuration();
		config.setInters(inters);
		return config;
	}
	//根据用户需要获取节点的属性值
	private String getNodeAttr(Element node, String attr) {
		
		return node.attributeValue(attr);
	}

	public static void main(String[] args) {
		ConfigurationManager cm = new ConfigurationManager();
		ActionMapping am = new ActionMapping();
		am.setName("login");
		System.out.println(cm.getConfiguration(am).getInters());
	}
}
