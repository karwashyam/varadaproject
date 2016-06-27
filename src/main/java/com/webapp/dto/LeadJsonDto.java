package com.webapp.dto;


public class LeadJsonDto {
	private String  phone ;
	private String email;
	private String created_date;
	private long  created_at; 
	private int srNo;
	private String leadFrom;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public long getCreated_at() {
		return created_at;
	}
	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getLeadFrom() {
		return leadFrom;
	}
	public void setLeadFrom(String leadFrom) {
		this.leadFrom = leadFrom;
	}
	
	
}
