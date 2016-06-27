package com.webapp.models1;

import java.util.List;

public class Products extends AbstractModel {

	private String productId;
	private String productTypeId;
	private int productPrice;
	private String productImage;
	private int quantity;
	private int weight;
	private String caption;
	private String action;
	private String productTypeName;

	private List<String> tagNames;
	
	private String tagName;
	
	
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Products [productId=");
		builder.append(productId);
		builder.append(", productTypeId=");
		builder.append(productTypeId);
		builder.append(", productPrice=");
		builder.append(productPrice);
		builder.append(", productImage=");
		builder.append(productImage);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", weight=");
		builder.append(weight);
		builder.append(", recordStatus=");
		builder.append(recordStatus);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", updatedAt=");
		builder.append(updatedAt);
		builder.append(", action=");
		builder.append(action);
		builder.append(", productTypeName=");
		builder.append(productTypeName);
		builder.append(", tagNames=");
		builder.append(tagNames);
		builder.append("]");
		return builder.toString();
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}