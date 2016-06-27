package com.webapp.fb.models;

public class Messaging {

	private Sender sender;
	private Recipient recipient;
	private long timestamp;
	private Message message;
	private Optin optin;
	private Delivery delivery;
	private Postback postback;
	public Sender getSender() {
		return sender;
	}
	public void setSender(Sender sender) {
		this.sender = sender;
	}
	public Recipient getRecipient() {
		return recipient;
	}
	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Optin getOptin() {
		return optin;
	}
	public void setOptin(Optin optin) {
		this.optin = optin;
	}
	public Delivery getDelivery() {
		return delivery;
	}
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	public Postback getPostback() {
		return postback;
	}
	public void setPostback(Postback postback) {
		this.postback = postback;
	}
	
}
