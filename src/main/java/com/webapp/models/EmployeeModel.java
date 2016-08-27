package com.webapp.models;

public class EmployeeModel extends AbstractModel{

	private String userId;

	private String userName;

	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private String confirmPassword;
	
	private String fullName;
	
	private String email;

	private Long birthDate;
	
	private String birthDateForModel;
	
	private String address;
	
	private String phone;
	
	private String profileName;

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getBirthDateForModel() {
		return birthDateForModel;
	}

	public void setBirthDateForModel(String birthDateForModel) {
		this.birthDateForModel = birthDateForModel;
	}

	public void setBirthDate(Long birthDate) {
		this.birthDate = birthDate;
	}
	
	public Long getBirthDate(){
		return birthDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
