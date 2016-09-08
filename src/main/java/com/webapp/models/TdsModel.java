package com.webapp.models;

public class TdsModel extends AbstractModel{

	private String tdsId;
	private String franchiseeCommissionId;
	private String franchiseeId;
	private String franchiseeName;
    private long chequeDate;
    private String chequeDateString;
    private long tdsAmount;
    private String chequeNumber;
    private String bank;
    private String accountHolder;
    private String status;
    private String srNo;
    private long tds;
    private String paymentMode;
    private String createdAtString;
	public String getTdsId() {
		return tdsId;
	}
	public void setTdsId(String tdsId) {
		this.tdsId = tdsId;
	}
	public String getFranchiseeCommissionId() {
		return franchiseeCommissionId;
	}
	public void setFranchiseeCommissionId(String franchiseeCommissionId) {
		this.franchiseeCommissionId = franchiseeCommissionId;
	}
	public String getFranchiseeId() {
		return franchiseeId;
	}
	public void setFranchiseeId(String franchiseeId) {
		this.franchiseeId = franchiseeId;
	}
	public String getFranchiseeName() {
		return franchiseeName;
	}
	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}
	public long getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(long chequeDate) {
		this.chequeDate = chequeDate;
	}
	public String getChequeDateString() {
		return chequeDateString;
	}
	public void setChequeDateString(String chequeDateString) {
		this.chequeDateString = chequeDateString;
	}
	public long getTdsAmount() {
		return tdsAmount;
	}
	public void setTdsAmount(long tdsAmount) {
		this.tdsAmount = tdsAmount;
	}
	public String getChequeNumber() {
		return chequeNumber;
	}
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public long getTds() {
		return tds;
	}
	public void setTds(long tds) {
		this.tds = tds;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getCreatedAtString() {
		return createdAtString;
	}
	public void setCreatedAtString(String createdAtString) {
		this.createdAtString = createdAtString;
	}


}