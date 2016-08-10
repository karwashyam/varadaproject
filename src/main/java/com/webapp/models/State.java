package com.webapp.models;


public class State extends AbstractModel {

	private String stateId;
	private String stateName;
	private String stateExists;

	
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getStateExists() {
		return stateExists;
	}
	public void setStateExists(String stateExists) {
		this.stateExists = stateExists;
	}



}