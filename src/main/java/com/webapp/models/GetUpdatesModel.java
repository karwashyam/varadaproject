package com.webapp.models;

/**
 * @author Asus
 *
 */
public class GetUpdatesModel {

	private boolean ok;
	private Update[] result;
	private String error_code;
	private String description;
	
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
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public Update[] getResult() {
		return result;
	}
	public void setResult(Update[] result) {
		this.result = result;
	}
	
}
