package com.webapp.dto;


public class UsersJsonDto {
	private String user_id;
	private String user_full_name;
	private String  phone ;
	private String email;
	private String user_city;
	private String user_name;
	private boolean  saree ;
	private boolean kurti;
	private boolean gown;
	private boolean lehenga;
	private boolean dress_material;
	private String update_frequency;
	private String catalogue_delivery;
	private String created_date;
	private long  created_at; 
	private long  updated_at;
	private String action;
	private int srNo;
	private int referralBalance;
	
	
	public int getReferralBalance() {
		return referralBalance;
	}
	public void setReferralBalance(int referralBalance) {
		this.referralBalance = referralBalance;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_full_name() {
		return user_full_name;
	}
	public void setUser_full_name(String user_full_name) {
		this.user_full_name = user_full_name;
	}
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
	public String getUser_city() {
		return user_city;
	}
	public void setUser_city(String user_city) {
		this.user_city = user_city;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public boolean isSaree() {
		return saree;
	}
	public void setSaree(boolean saree) {
		this.saree = saree;
	}
	public boolean isKurti() {
		return kurti;
	}
	public void setKurti(boolean kurti) {
		this.kurti = kurti;
	}
	public boolean isGown() {
		return gown;
	}
	public void setGown(boolean gown) {
		this.gown = gown;
	}
	public boolean isLehenga() {
		return lehenga;
	}
	public void setLehenga(boolean lehenga) {
		this.lehenga = lehenga;
	}
	public boolean isDress_material() {
		return dress_material;
	}
	public void setDress_material(boolean dress_material) {
		this.dress_material = dress_material;
	}
	public String getUpdate_frequency() {
		return update_frequency;
	}
	public void setUpdate_frequency(String update_frequency) {
		this.update_frequency = update_frequency;
	}
	public String getCatalogue_delivery() {
		return catalogue_delivery;
	}
	public void setCatalogue_delivery(String catalogue_delivery) {
		this.catalogue_delivery = catalogue_delivery;
	}
	public long getCreated_at() {
		return created_at;
	}
	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}
	public long getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(long updated_at) {
		this.updated_at = updated_at;
	}
		
		
	
}
