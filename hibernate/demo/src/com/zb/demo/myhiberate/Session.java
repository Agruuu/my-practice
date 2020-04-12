package com.zb.demo.myhiberate;

/**
 * ������ɾ�Ĳ鷽���Ķ���
 * ����ӿ�������DAO�ĸ��ӿ�, �ǳ��󹤳���
 * �ĳ����Ʒ�ӿ�
 */

public interface Session<T> {
	public void save(T t);
	public void delete(int id);
	public void merge(T t);
	public T get(Class cls,Object id);
}
