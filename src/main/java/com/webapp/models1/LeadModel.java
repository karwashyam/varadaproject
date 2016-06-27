package com.webapp.models1;

public class LeadModel {
	
	private String leadId;
	private String email;
	private String phone;
	private String leadFrom;
	private long createdAt;
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLeadFrom() {
		return leadFrom;
	}
	public void setLeadFrom(String leadFrom) {
		this.leadFrom = leadFrom;
	}
	public long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
