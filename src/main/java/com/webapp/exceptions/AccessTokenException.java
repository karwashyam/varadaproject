package com.webapp.exceptions;

public class AccessTokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;

	public AccessTokenException(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
