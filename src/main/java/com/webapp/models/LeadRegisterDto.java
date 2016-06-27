package com.webapp.models;


public class LeadRegisterDto {
	private String email;
	private String leadFrom;
	private String phone;
	private String redirectUrl;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLeadFrom() {
		return leadFrom;
	}
	public void setLeadFrom(String leadFrom) {
		this.leadFrom = leadFrom;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	
	
}
