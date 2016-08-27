package com.webapp.dto;

public class PaymentSchemeDto {

	private String paymentSchemeId;
	private String title;
	private Long downPayment;
	private Long noOfMonths;
	private Long interestRate;
	private boolean prepaymentPossible;
	private String paymentSchemeExists;
	
	
	//==========================================
	public String getPaymentSchemeId() {
		return paymentSchemeId;
	}
	public void setPaymentSchemeId(String paymentSchemeId) {
		this.paymentSchemeId = paymentSchemeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getDownPayment() {
		return downPayment;
	}
	public void setDownPayment(Long downPayment) {
		this.downPayment = downPayment;
	}
	public Long getNoOfMonths() {
		return noOfMonths;
	}
	public void setNoOfMonths(Long noOfMonths) {
		this.noOfMonths = noOfMonths;
	}
	public Long getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Long interestRate) {
		this.interestRate = interestRate;
	}
	public boolean isPrepaymentPossible() {
		return prepaymentPossible;
	}
	public void setPrepaymentPossible(boolean prepaymentPossible) {
		this.prepaymentPossible = prepaymentPossible;
	}
	public String getPaymentSchemeExists() {
		return paymentSchemeExists;
	}
	public void setPaymentSchemeExists(String paymentSchemeExists) {
		this.paymentSchemeExists = paymentSchemeExists;
	}
	
	
}
