package com.webapp.dto;

public class SignupDto {

	private String email;
	private String fullName;
	private String password;
	private String phone;
	private String city;
	private String invitedBy;
	private String deviceUid;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getInvitedBy() {
		return invitedBy;
	}
	public void setInvitedBy(String invitedBy) {
		this.invitedBy = invitedBy;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDeviceUid() {
		return deviceUid;
	}
	public void setDeviceUid(String deviceUid) {
		this.deviceUid = deviceUid;
	}
	
	
}
