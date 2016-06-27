package com.webapp.models;

public class OrderModel {

	private int order_id;
	private String user_id;
	private String address;
	private String delivery_phone;
	private String order_status;
	private String previous_order_status;
	private String transaction_id;
	private int total;
	private long created_at;
	private String user_full_name;
	private String email;
	private String created_date;
	private String phone;
	private int total_price;
	private int total_discounted_price;
	private int total_courier;
	private int coupon_discount;
	private int referral_wallet_discount;
	private int adjustment_amount;
	private String adjustment_note;
	private String order_note;
	private String customer_note;
	private String customer_note_new;
	private String product_no; 
	private String coupon_id;
	private String payment_method;
	private String courier_method;
	 
	private int amount;
	private String cumulativeDiscountOrderId;
	private String recordStatus;
	private long updatedAt;
	private int internet_handling_charge ;
	private int online_payment;
	private int cod_payment ;
	private int cod_charge; 
	
	public int getInternet_handling_charge() {
		return internet_handling_charge;
	}
	public void setInternet_handling_charge(int internet_handling_charge) {
		this.internet_handling_charge = internet_handling_charge;
	}
	public int getOnline_payment() {
		return online_payment;
	}
	public void setOnline_payment(int online_payment) {
		this.online_payment = online_payment;
	}
	public int getCod_payment() {
		return cod_payment;
	}
	public void setCod_payment(int cod_payment) {
		this.cod_payment = cod_payment;
	}
	public int getCod_charge() {
		return cod_charge;
	}
	public void setCod_charge(int cod_charge) {
		this.cod_charge = cod_charge;
	}
	public String getPrevious_order_status() {
		return previous_order_status;
	}
	public void setPrevious_order_status(String previous_order_status) {
		this.previous_order_status = previous_order_status;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCumulativeDiscountOrderId() {
		return cumulativeDiscountOrderId;
	}
	public void setCumulativeDiscountOrderId(String cumulativeDiscountOrderId) {
		this.cumulativeDiscountOrderId = cumulativeDiscountOrderId;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}
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
	public String getCustomer_note_new() {
		return customer_note_new;
	}
	public void setCustomer_note_new(String customer_note_new) {
		this.customer_note_new = customer_note_new;
	}
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getProduct_no() {
		return product_no;
	}
	public void setProduct_no(String product_no) {
		this.product_no = product_no;
	}
	public int getCoupon_discount() {
		return coupon_discount;
	}
	public void setCoupon_discount(int coupon_discount) {
		this.coupon_discount = coupon_discount;
	}
	public int getReferral_wallet_discount() {
		return referral_wallet_discount;
	}
	public void setReferral_wallet_discount(int referral_wallet_discount) {
		this.referral_wallet_discount = referral_wallet_discount;
	}
	public int getAdjustment_amount() {
		return adjustment_amount;
	}
	public void setAdjustment_amount(int adjustment_amount) {
		this.adjustment_amount = adjustment_amount;
	}
	public String getAdjustment_note() {
		return adjustment_note;
	}
	public void setAdjustment_note(String adjustment_note) {
		this.adjustment_note = adjustment_note;
	}
	public String getCustomer_note() {
		return customer_note;
	}
	public void setCustomer_note(String customer_note) {
		this.customer_note = customer_note;
	}
	public String getOrder_note() {
		return order_note;
	}
	public void setOrder_note(String order_note) {
		this.order_note = order_note;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDelivery_phone() {
		return delivery_phone;
	}
	public void setDelivery_phone(String delivery_phone) {
		this.delivery_phone = delivery_phone;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
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
	public String getUser_full_name() {
		return user_full_name;
	}
	public void setUser_full_name(String user_full_name) {
		this.user_full_name = user_full_name;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	
	
	

}
