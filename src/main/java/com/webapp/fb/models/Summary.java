package com.webapp.fb.models;

public class Summary {

	private int subtotal;
	private int shipping_cost;
	private int total_tax;
	private int total_cost;
	public int getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}
	public int getShipping_cost() {
		return shipping_cost;
	}
	public void setShipping_cost(int shipping_cost) {
		this.shipping_cost = shipping_cost;
	}
	public int getTotal_tax() {
		return total_tax;
	}
	public void setTotal_tax(int total_tax) {
		this.total_tax = total_tax;
	}
	public int getTotal_cost() {
		return total_cost;
	}
	public void setTotal_cost(int total_cost) {
		this.total_cost = total_cost;
	}
	

	
}
