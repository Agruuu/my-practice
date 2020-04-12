package com.zb.demo.myhiberate;

/**
 * 含有增删改查方法的对象
 * 这个接口是所有DAO的父接口, 是抽象工厂中
 * 的抽象产品接口
 */

public interface Session<T> {
	public void save(T t);
	public void delete(int id);
	public void merge(T t);
	public T get(Class cls,Object id);
}
