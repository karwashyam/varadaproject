package com.webapp.models1;

public class SignupModel extends AbstractModel{

	private String userId;
	private String email;
	private String fullName;
	private String password;
	private String phone;
	private String city;
	private String invitedBy;
	private long walletBalance;
	private String inviteAndEarnId;
	private String referralCode;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getWalletBalance() {
		return walletBalance;
	}
	public void setWalletBalance(long walletBalance) {
		this.walletBalance = walletBalance;
	}
	public String getInvitedBy() {
		return invitedBy;
	}
	public void setInvitedBy(String invitedBy) {
		this.invitedBy = invitedBy;
	}
	public String getInviteAndEarnId() {
		return inviteAndEarnId;
	}
	public void setInviteAndEarnId(String inviteAndEarnId) {
		this.inviteAndEarnId = inviteAndEarnId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getReferralCode() {
		return referralCode;
	}
	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}
	
	
}
