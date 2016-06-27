package com.webapp.fb.models;

public class SendResponse {

	private Recipient recipient;
	private SendMessage message;
	private String notification_type;//REGULAR, SILENT_PUSH, NO_PUSH
	public Recipient getRecipient() {
		return recipient;
	}
	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}
	public SendMessage getMessage() {
		return message;
	}
	public void setMessage(SendMessage message) {
		this.message = message;
	}
	public String getNotification_type() {
		return notification_type;
	}
	public void setNotification_type(String notification_type) {
		this.notification_type = notification_type;
	}
}
