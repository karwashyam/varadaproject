package com.webapp.controllers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.fnf.utils.SuccessMessage;

public class BaseApiController {

	public static final String SUCCESS_MESSAGE_TYPE = "SUCCESS";

	//	public static final String BUSSINES_ERROR_TYPE = "ERR-BUSS";

	public final String SINGLE_OBJECT_RESPONSE = "responseObject";

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	protected String languageCode;

	protected ServletContext applicationContext;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public ServletContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ServletContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	protected SuccessMessage ouputSuccesssMessage(String message) {
		SuccessMessage messageModel = new SuccessMessage();
		messageModel.setType(SUCCESS_MESSAGE_TYPE);
		messageModel.setMessage(message);
		return messageModel;
	}

	/*
	 * protected SuccessMessage ouputErrorMessage(String message) {
	 * SuccessMessage messageModel = new SuccessMessage();
	 * messageModel.setType(BUSSINES_ERROR_TYPE);
	 * messageModel.setMessage(message); return messageModel;
	 * 
	 * }
	 */
	protected ModelAndView getOutputResponse(Object obj) {
		return new ModelAndView("", SINGLE_OBJECT_RESPONSE, obj);
	}
}
