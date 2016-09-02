package com.webapp.dto;

public class ProjectPaymentSchemeDto {

	private String projectPaymentSchemeId;
	private String paymentSchemeId;
	private String projectId;
	private String projPaymentSchemeExists;
	private String paymentSchemeTitle;
	private String projectTitle;
	
	public String getProjPaymentSchemeExists() {
		return projPaymentSchemeExists;
	}
	public void setProjPaymentSchemeExists(String projPaymentSchemeExists) {
		this.projPaymentSchemeExists = projPaymentSchemeExists;
	}
	public String getProjectPaymentSchemeId() {
		return projectPaymentSchemeId;
	}
	public void setProjectPaymentSchemeId(String projectPaymentSchemeId) {
		this.projectPaymentSchemeId = projectPaymentSchemeId;
	}
	public String getPaymentSchemeId() {
		return paymentSchemeId;
	}
	public void setPaymentSchemeId(String paymentSchemeId) {
		this.paymentSchemeId = paymentSchemeId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getPaymentSchemeTitle() {
		return paymentSchemeTitle;
	}
	public void setPaymentSchemeTitle(String paymentSchemeTitle) {
		this.paymentSchemeTitle = paymentSchemeTitle;
	}
	public String getProjectTitle() {
		return projectTitle;
	}
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	
	
}
