package com.webapp.dto;

public class Login {

	private String email;
	private String userName;
	private String password;
	private boolean rememberme;
	private String validLogin;
	
	public String getValidLogin() {
		return validLogin;
	}

	public void setValidLogin(String validLogin) {
		this.validLogin = validLogin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberme() {
		return rememberme;
	}

	public void setRememberme(boolean rememberme) {
		this.rememberme = rememberme;
	}
}
