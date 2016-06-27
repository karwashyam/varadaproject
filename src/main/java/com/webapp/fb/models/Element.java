package com.webapp.fb.models;

import java.util.List;

public class Element {
	//for both order products and template
	private String title;
	private String image_url;//N
	private String subtitle;//N
	
	//for template
	private String item_url;//	N
	//URL that is opened when bubble is tapped
	private List<Buttons> buttons;
	
	//for order products
	private int quantity;
	private int price;
	private String currency;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getItem_url() {
		return item_url;
	}
	public void setItem_url(String item_url) {
		this.item_url = item_url;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public List<Buttons> getButtons() {
		return buttons;
	}
	public void setButtons(List<Buttons> buttons) {
		this.buttons = buttons;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	

}
