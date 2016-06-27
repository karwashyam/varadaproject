package com.webapp.dto;

public class SubOrderJsonDto {
	private int order_product_id;
	private String product_id;
	private int order_id;
	private int price;
	private int discounted_price;
	private int weight;
	private String  action;
	private String product_type_name;
	
	public String getProduct_type_name() {
		return product_type_name;
	}

	public void setProduct_type_name(String product_type_name) {
		this.product_type_name = product_type_name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getOrder_product_id() {
		return order_product_id;
	}

	public void setOrder_product_id(int order_product_id) {
		this.order_product_id = order_product_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDiscounted_price() {
		return discounted_price;
	}

	public void setDiscounted_price(int discounted_price) {
		this.discounted_price = discounted_price;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
