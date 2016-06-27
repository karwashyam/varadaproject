package com.webapp.dto;

public class LoginApi {

	private String email;
	private String password;
	private String deviceUid;
	private String validLogin;
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
	public String getDeviceUid() {
		return deviceUid;
	}
	public void setDeviceUid(String deviceUid) {
		this.deviceUid = deviceUid;
	}
	public String getValidLogin() {
		return validLogin;
	}
	public void setValidLogin(String validLogin) {
		this.validLogin = validLogin;
	}

}