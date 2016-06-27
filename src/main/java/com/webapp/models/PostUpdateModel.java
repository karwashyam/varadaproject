package com.webapp.models;

public class PostUpdateModel {

	private boolean ok;
	private Message result;
	private String error_code;
	private String description;
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public Message getResult() {
		return result;
	}
	public void setResult(Message result) {
		this.result = result;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
