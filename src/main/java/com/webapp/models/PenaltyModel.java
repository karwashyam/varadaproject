package com.webapp.models;

public class PenaltyModel extends AbstractModel{

	private String bookingId;
	private String type;
	private String description;
	private long amount1;
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getAmount1() {
		return amount1;
	}
	public void setAmount1(long amount1) {
		this.amount1 = amount1;
	}
	


}
