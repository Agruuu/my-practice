package com.zb.demo.myhiberate;

/**
 * 存放属性的实体类,本类的属性来自于xxx.hbm.xml文件的节点
 * 通常存放的是id和property节点的属性和子标签的属性
 */
public class Attributes {
	private String name;
	private String type;
	private String columnname;
	private String generator;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	
}
