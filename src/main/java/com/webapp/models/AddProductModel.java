package com.webapp.models;

public class AddProductModel {

	private String productNo;
	private int quantity;
	private double price;
	private long createdAt;
	private String productPic;
	private String productUrlThumb;
	private String productUrlOriginal;
	private String productType;
	private String caption;
	private int weight;
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	public String getProductPic() {
		return productPic;
	}
	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductUrlThumb() {
		return productUrlThumb;
	}
	public void setProductUrlThumb(String productUrlThumb) {
		this.productUrlThumb = productUrlThumb;
	}
	public String getProductUrlOriginal() {
		return productUrlOriginal;
	}
	public void setProductUrlOriginal(String productUrlOriginal) {
		this.productUrlOriginal = productUrlOriginal;
	}
	
}
