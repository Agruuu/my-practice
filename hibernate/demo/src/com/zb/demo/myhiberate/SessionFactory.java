package com.zb.demo.myhiberate;

/**
*制造CRUD对象的工厂
*本接口是抽象工厂的父接口
*/
public interface SessionFactory<T> {
	//这个方法是用来产生抽象产品
	public Session<T> openSession();
}
