package com.webapp.models;

public class BookingModel extends AbstractModel{

	private String bookingId;
	private String bookingCode;
	private String projectId;
	private String projectName;
	private String paymentSchemeId;
 	private String plotId;
	private String plotName;
	private String plotSize;
	private long downPayment;
	private long ratePerYard;
	private String franchiseeId;
	private String franchiseeName;
	private String memberId;
	private String memberName;
	private String fatherName;
	private String nomineeName;
	private String nomineeFather;
	private String nomineeAddress;
	private String nomineeRelation;
	private String nomineeDob;
	private long nomineeDobLong;
	private String remarks;
	private long price;
	private long paymentMadeTillNow;
	private long emi;
	private long noOfEmi;
	private long emiPrice;
	private long nextEmiOn;
	private long interestRate;
	private String memberCode;
	private String email;
	private String phone2;
	private String phone1;
	private String paymentMethod;
	private String paymentDate;
	private String description;
	private String chequeNo;
	private String issueDate;
	private String bank;
	private String accountHolder;
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getBookingCode() {
		return bookingCode;
	}
	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getPlotId() {
		return plotId;
	}
	public void setPlotId(String plotId) {
		this.plotId = plotId;
	}
	public String getPlotName() {
		return plotName;
	}
	public void setPlotName(String plotName) {
		this.plotName = plotName;
	}
	public String getPlotSize() {
		return plotSize;
	}
	public void setPlotSize(String plotSize) {
		this.plotSize = plotSize;
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
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getNomineeName() {
		return nomineeName;
	}
	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}
	public String getNomineeFather() {
		return nomineeFather;
	}
	public void setNomineeFather(String nomineeFather) {
		this.nomineeFather = nomineeFather;
	}
	public String getNomineeAddress() {
		return nomineeAddress;
	}
	public void setNomineeAddress(String nomineeAddress) {
		this.nomineeAddress = nomineeAddress;
	}
	public String getNomineeRelation() {
		return nomineeRelation;
	}
	public void setNomineeRelation(String nomineeRelation) {
		this.nomineeRelation = nomineeRelation;
	}
	public String getNomineeDob() {
		return nomineeDob;
	}
	public void setNomineeDob(String nomineeDob) {
		this.nomineeDob = nomineeDob;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getPaymentMadeTillNow() {
		return paymentMadeTillNow;
	}
	public void setPaymentMadeTillNow(long paymentMadeTillNow) {
		this.paymentMadeTillNow = paymentMadeTillNow;
	}
	public long getEmi() {
		return emi;
	}
	public void setEmi(long emi) {
		this.emi = emi;
	}
	public long getNoOfEmi() {
		return noOfEmi;
	}
	public void setNoOfEmi(long noOfEmi) {
		this.noOfEmi = noOfEmi;
	}
	public long getEmiPrice() {
		return emiPrice;
	}
	public void setEmiPrice(long emiPrice) {
		this.emiPrice = emiPrice;
	}
	public long getNextEmiOn() {
		return nextEmiOn;
	}
	public void setNextEmiOn(long nextEmiOn) {
		this.nextEmiOn = nextEmiOn;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPaymentSchemeId() {
		return paymentSchemeId;
	}
	public void setPaymentSchemeId(String paymentSchemeId) {
		this.paymentSchemeId = paymentSchemeId;
	}
	public long getRatePerYard() {
		return ratePerYard;
	}
	public void setRatePerYard(long ratePerYard) {
		this.ratePerYard = ratePerYard;
	}
	public long getDownPayment() {
		return downPayment;
	}
	public void setDownPayment(long downPayment) {
		this.downPayment = downPayment;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
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
	public long getNomineeDobLong() {
		return nomineeDobLong;
	}
	public void setNomineeDobLong(long nomineeDobLong) {
		this.nomineeDobLong = nomineeDobLong;
	}
	public long getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(long interestRate) {
		this.interestRate = interestRate;
	}
	


}
