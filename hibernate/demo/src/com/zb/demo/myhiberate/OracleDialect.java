package com.zb.demo.myhiberate;

/**
 * Oracle���ݿ��Session����
 * �����������ͨ��openSession��������
 * ���ݿ��Ӧ�����ķ�װ����
 */
public class OracleDialect implements SessionFactory {

	//����Oracle��Session
	public Session openSession() {
		// TODO Auto-generated method stub
		return new BaseHibernateDAO();
	}

}
