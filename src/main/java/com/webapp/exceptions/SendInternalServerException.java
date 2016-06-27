package com.webapp.exceptions;

import java.util.List;

import com.fnf.utils.ErrorMessage;

public class SendInternalServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private List<ErrorMessage> errors;

	public SendInternalServerException() {

	}

	public SendInternalServerException(ErrorMessage error) {
		this.errors.add(error);
	}

	public SendInternalServerException(List<ErrorMessage> errors) {
		this.errors = errors;
	}

	public List<ErrorMessage> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorMessage> errors) {
		this.errors = errors;
	}

}
