package com.webapp.models;

public class FranchiseModel extends AbstractModel{
	
	private long srNo;

	private String franchiseeId;

	private String franchiseeName;
	
	private String email;
	
	private String address;
	
	private String state;
	
	private String city;
	
	private String pincode;
	
	private String phone1;
	
	private String phone2;
	
	private String fax;
	
	private String landLine1;
	
	private String landLine2;
	
	private String pan;
	
	private long tds;
	
	private int commissionPercentage;
	
	private long commissionPaid;
	
	private long tdsPaid;
	
	private long commissionUnpaid;
	
	private String action;
	
	public long getSrNo() {
		return srNo;
	}

	public void setSrNo(long srNo) {
		this.srNo = srNo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLandLine1() {
		return landLine1;
	}

	public void setLandLine1(String landLine1) {
		this.landLine1 = landLine1;
	}

	public String getLandLine2() {
		return landLine2;
	}

	public void setLandLine2(String landLine2) {
		this.landLine2 = landLine2;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public long getTds() {
		return tds;
	}

	public void setTds(long tds) {
		this.tds = tds;
	}

	public int getCommissionPercentage() {
		return commissionPercentage;
	}

	public void setCommissionPercentage(int commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}

	public long getCommissionPaid() {
		return commissionPaid;
	}

	public void setCommissionPaid(long commissionPaid) {
		this.commissionPaid = commissionPaid;
	}

	public long getTdsPaid() {
		return tdsPaid;
	}

	public void setTdsPaid(long tdsPaid) {
		this.tdsPaid = tdsPaid;
	}

	public long getCommissionUnpaid() {
		return commissionUnpaid;
	}

	public void setCommissionUnpaid(long commissionUnpaid) {
		this.commissionUnpaid = commissionUnpaid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
