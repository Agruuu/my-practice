package com.zb.strutsdemo.test;

public class MyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String p = "2009.5.6";
		String [] date = p.split("[-|/|Äê|ÔÂ|ÈÕ|\\.]");
		for (String s:date){
			System.out.println(s);
		}

	}

}
