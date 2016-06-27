package com.webapp.models1;

public class AppUser extends AbstractModel {

	private String userId;
	private String fullName;
	private String userName;
	private String password;
	private String email;
	private String phone;
	private String city;
	private String birthDate;
	private boolean saveToGallery;
	private boolean autoDeleteFromGallery;
	private boolean notification;
	private boolean notificationOnEmail;
	private long walletBalance;
	private long passiveWalletBalance;
	private String referralCode;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public boolean isSaveToGallery() {
		return saveToGallery;
	}
	public void setSaveToGallery(boolean saveToGallery) {
		this.saveToGallery = saveToGallery;
	}
	public boolean isAutoDeleteFromGallery() {
		return autoDeleteFromGallery;
	}
	public void setAutoDeleteFromGallery(boolean autoDeleteFromGallery) {
		this.autoDeleteFromGallery = autoDeleteFromGallery;
	}
	public boolean isNotification() {
		return notification;
	}
	public void setNotification(boolean notification) {
		this.notification = notification;
	}
	public boolean isNotificationOnEmail() {
		return notificationOnEmail;
	}
	public void setNotificationOnEmail(boolean notificationOnEmail) {
		this.notificationOnEmail = notificationOnEmail;
	}
	public long getWalletBalance() {
		return walletBalance;
	}
	public void setWalletBalance(long walletBalance) {
		this.walletBalance = walletBalance;
	}
	public long getPassiveWalletBalance() {
		return passiveWalletBalance;
	}
	public void setPassiveWalletBalance(long passiveWalletBalance) {
		this.passiveWalletBalance = passiveWalletBalance;
	}
	public String getReferralCode() {
		return referralCode;
	}
	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

}