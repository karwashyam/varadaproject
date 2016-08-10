package com.webapp.models;

public class CityModel extends AbstractModel{

	private String cityId;

	private String cityName;

	private String stateName;

	private String stateId;
	
	private String validCity;

	public String getValidCity() {
		return validCity;
	}

	public void setValidCity(String validCity) {
		this.validCity = validCity;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}


}
