package com.webapp.dto;

import java.util.List;

public class OrderJsonDto {
	private int order_id;
	private String user_id;
	private String product_id;
	private String user_full_name;
	private String  address ;
	private String  phone ;
	private String email;
	private String  order_status;
	private String  action;
	private String  created_date; 
	private String  payment_method; 
	private String  courier_method; 
	private int  total_price;
	private int  total_discounted_price; 
	private int  total_courier ;
	private int  total ;
	private long  created_at; 
	private long  updated_at;
	private List<SubOrderJsonDto> subOrderJsonDto;
	
	
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public String getCourier_method() {
		return courier_method;
	}
	public void setCourier_method(String courier_method) {
		this.courier_method = courier_method;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public int getTotal_price() {
		return total_price;
	}
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	public int getTotal_discounted_price() {
		return total_discounted_price;
	}
	public void setTotal_discounted_price(int total_discounted_price) {
		this.total_discounted_price = total_discounted_price;
	}
	public int getTotal_courier() {
		return total_courier;
	}
	public void setTotal_courier(int total_courier) {
		this.total_courier = total_courier;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
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
	public List<SubOrderJsonDto> getSubOrderJsonDto() {
		return subOrderJsonDto;
	}
	public void setSubOrderJsonDto(List<SubOrderJsonDto> subOrderJsonDto) {
		this.subOrderJsonDto = subOrderJsonDto;
	}
	
	
}
