package com.zb.strutsdemo.struts;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存配置文件信息
 * @author Agru
 *
 */
public class ActionMapping {
	private String name;
	private String classname;
	private Map<String, String> results = new HashMap<String, String>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public Map<String, String> getResults() {
		return results;
	}

	public void setResults(Map<String, String> results) {
		this.results = results;
	}
}
