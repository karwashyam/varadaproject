package com.webapp.controllers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public abstract class BaseController {

	Model baseModel;

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	protected ServletContext servletContext;

	public Model getBaseModel() {
		return baseModel;
	}

	public void setBaseModel(Model baseModel) {
		this.baseModel = baseModel;
	}

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

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
