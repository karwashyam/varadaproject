package com.webapp.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private String message;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {

	}

	public ResourceNotFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
