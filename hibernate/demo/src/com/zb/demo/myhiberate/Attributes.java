package com.zb.demo.myhiberate;

/**
 * ������Ե�ʵ����,���������������xxx.hbm.xml�ļ��Ľڵ�
 * ͨ����ŵ���id��property�ڵ�����Ժ��ӱ�ǩ������
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
