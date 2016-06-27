package com.webapp.models;

import com.webapp.models1.AbstractModel;

public class SubOrderModel extends AbstractModel{

	private Integer order_product_id;
	private String product_id;
	private Integer order_id;
	private Integer price;
	private Integer discounted_price;
	private Integer weight;
	public Integer getOrder_product_id() {
		return order_product_id;
	}
	public void setOrder_product_id(Integer order_product_id) {
		this.order_product_id = order_product_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getDiscounted_price() {
		return discounted_price;
	}
	public void setDiscounted_price(Integer discounted_price) {
		this.discounted_price = discounted_price;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	

}
