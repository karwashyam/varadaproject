package com.webapp.fb.models;

public class PostUpdateFbModel {

	private String recipient_id;
	private String message_id;
	private FbError error;
	public String getRecipient_id() {
		return recipient_id;
	}
	public void setRecipient_id(String recipient_id) {
		this.recipient_id = recipient_id;
	}
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public FbError getError() {
		return error;
	}
	public void setError(FbError error) {
		this.error = error;
	}
}
