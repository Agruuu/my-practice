package com.zb.strutsdemo.action;

import com.zb.strutsdemo.struts.ActionContext;

public class LoginAction {
	private String username;
	private String password;

	public String execute() {
		if (username.equals("accp") && password.equals("123456")) {
			ActionContext.getActionContext()
				.getSession()
				.setAttribute("abc", username);
			
			return "ok";
		} else {
			return "error";
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
