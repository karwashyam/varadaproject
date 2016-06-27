package com.webapp.fb.models;

import java.util.List;

public class Payload {

	private String url;//for images
	
	//for templates
	//generic, button and receipt  - template_type
	private String template_type;
	//for generic template and reciept(order products)
	private List<Element> elements;
	
	//button template
	private String text;
	private List<Buttons> buttons;
	
	//reciept template
	private String recipient_name;
	private String order_number;
	private String currency;
	private String payment_method;
	private String timestamp;//N
	private String order_url;//N
	private Address address;//N
	private Summary summary;
	private List<Adjustment> adjustments;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTemplate_type() {
		return template_type;
	}

	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Buttons> getButtons() {
		return buttons;
	}

	public void setButtons(List<Buttons> buttons) {
		this.buttons = buttons;
	}

	public String getRecipient_name() {
		return recipient_name;
	}

	public void setRecipient_name(String recipient_name) {
		this.recipient_name = recipient_name;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getOrder_url() {
		return order_url;
	}

	public void setOrder_url(String order_url) {
		this.order_url = order_url;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	public List<Adjustment> getAdjustments() {
		return adjustments;
	}

	public void setAdjustments(List<Adjustment> adjustments) {
		this.adjustments = adjustments;
	}

	
}
