package com.webapp.models;

public class TransferModel extends AbstractModel{

	private String bookingId;
	private String memberBookingId;
	private String memberId;
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getMemberBookingId() {
		return memberBookingId;
	}
	public void setMemberBookingId(String memberBookingId) {
		this.memberBookingId = memberBookingId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	


}
