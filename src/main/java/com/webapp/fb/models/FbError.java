package com.webapp.fb.models;

public class FbError {

	private String message;
	private String type;
	private int code;
	private String error_data;
	private String fbtrace_id;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getError_data() {
		return error_data;
	}
	public void setError_data(String error_data) {
		this.error_data = error_data;
	}
	public String getFbtrace_id() {
		return fbtrace_id;
	}
	public void setFbtrace_id(String fbtrace_id) {
		this.fbtrace_id = fbtrace_id;
	}
	
}
