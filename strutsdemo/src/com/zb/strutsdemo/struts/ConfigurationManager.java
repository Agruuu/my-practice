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
 * ��ȡ�����ļ�
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

	// ��ȡ struts.xml �ļ�·��
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

		// ��ȡ�ļ�������װ Map ����

		List<Element> pack = root.elements();

		// �ó����� Action
		List<Element> actions = pack.get(0).elements();

		// ��� acts
		for (Element action : actions) {
			ActionMapping am = new ActionMapping();
			// ��� ���� �� ��·��
			am.setName(action.attributeValue("name"));
			am.setClassname(action.attributeValue("class"));

			// ��ȡ action ��������ת·������

			List<Element> results = action.elements();
			for (Element result : results) {
				am.getResults().put(result.attributeValue("name"),
						result.getTextTrim());
			}

			// �� action �ı���(name) ������� ActionMapping ��������
			acts.put(action.attributeValue("name"), am);
		}
		return acts;
	}
	
	//��ȡ�û�struts.xml�ļ���װ��Configuration����
	public Configuration getConfiguration(ActionMapping mapping){
		//���interceptor����ļ���
		List<Interceptor> inters = new ArrayList<Interceptor>(0);
		//��ȡ���Ƚ��������inters����
		//��ȡpackage�ڵ�
		Element pack = root.element("package");
		//��ȡinterceptors�ڵ�
		Element inter = pack.element("interceptors");
		//��ȡ���е�interceptor�ڵ�
		List<Element> interceptors = inter.elements("interceptor");
		//��ȡ���е�interceptor-stack�ڵ�
		List<Element> stacks = inter.elements("interceptor-stack");
		//�����õ�Actionѡ����Ĭ�Ͻڵ㻹���Զ���ڵ�
		//�ҵ��û���Action�ڵ�
		List<Element> actions = pack.elements("action");
		Element userAction=null;
		for (Element el:actions){
			if (mapping.getName().equals(getNodeAttr(el, "name"))){
				userAction = el;
				break;
			}
		}
		//��ѯ���û��ڵ����Ƿ���interceptor-ref�ڵ�
		Element defaultRef = userAction.element("interceptor-ref");
		if(defaultRef==null){
		//��ȡĬ��default-interceptor-ref�ڵ�
			defaultRef= pack.element("default-interceptor-ref");
		}
		//�Ȼ�ȡ������ջ(���������)
		for (Element stack:stacks){
			if (getNodeAttr(defaultRef,"name").equals(getNodeAttr(stack,"name"))){
				//����û��Ѿ��ҵ�Ĭ��ջ�Ľڵ��ȥ��ȡĬ��ջ�������������
				List<Element> intercepts = stack.elements("interceptor-ref");
				//��interceptors���Ͻ��бȽϲ�ʵ����interceptor
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
		
		//�Ѽ��ϴ�ŵ�Configuration���϶�����
		Configuration config = new Configuration();
		config.setInters(inters);
		return config;
	}
	//�����û���Ҫ��ȡ�ڵ������ֵ
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
