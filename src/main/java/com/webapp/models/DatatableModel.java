package com.webapp.models;

import java.util.List;

public class DatatableModel<T> implements java.io.Serializable {
	private static final long serialVersionUID = -8220588043068200705L;
	private List<T> aaData;
	private int sEcho;
	private Integer iTotalRecords;
	private Integer iTotalDisplayRecords;

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public Integer getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(Integer iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public Integer getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<T> getAaData() {
		return aaData;
	}

	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}