package com.zb.demo.myhiberate;

/**
*����CRUD����Ĺ���
*���ӿ��ǳ��󹤳��ĸ��ӿ�
*/
public interface SessionFactory<T> {
	//����������������������Ʒ
	public Session<T> openSession();
}
