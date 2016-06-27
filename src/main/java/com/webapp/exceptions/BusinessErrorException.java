package com.webapp.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.fnf.utils.FieldError;
import com.utils.constant.ErrorConstant;

public class BusinessErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private List<FieldError> fieldErrors;

	public BusinessErrorException() {

	}

	public BusinessErrorException(String message) {
		FieldError error = new FieldError(ErrorConstant.BUSSINES_ERROR_TYPE, message);
		fieldErrors = new ArrayList<FieldError>();
		this.fieldErrors.add(error);
	}

	public BusinessErrorException(FieldError error) {
		this.fieldErrors.add(error);
	}

	public BusinessErrorException(List<FieldError> errors) {
		this.fieldErrors = errors;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}
