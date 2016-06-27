package com.webapp.exceptions;

public class FavoriteBeerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String message;

	public FavoriteBeerException(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
