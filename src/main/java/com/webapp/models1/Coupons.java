package com.webapp.models1;

public class Coupons extends AbstractModel {

	private String couponId;
	private String couponCode;
	private long startDate;
	private long endDate;
	private int discountInPercentage;
	private int maximumTimeUsed;
	private long discountOnMinimumPayablePrice;

	private int maxDisocunt;
	private boolean canUsedWithReferral;

	private String action;
	private String startDateDisplay;
	private String endDateDisplay;
	
	private String srNo;
	
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public long getStartDate() {
		return startDate;
	}
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	public long getEndDate() {
		return endDate;
	}
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	public int getDiscountInPercentage() {
		return discountInPercentage;
	}
	public void setDiscountInPercentage(int discountInPercentage) {
		this.discountInPercentage = discountInPercentage;
	}
	public int getMaximumTimeUsed() {
		return maximumTimeUsed;
	}
	public void setMaximumTimeUsed(int maximumTimeUsed) {
		this.maximumTimeUsed = maximumTimeUsed;
	}
	public int getMaxDisocunt() {
		return maxDisocunt;
	}
	public void setMaxDisocunt(int maxDisocunt) {
		this.maxDisocunt = maxDisocunt;
	}
	public boolean isCanUsedWithReferral() {
		return canUsedWithReferral;
	}
	public void setCanUsedWithReferral(boolean canUsedWithReferral) {
		this.canUsedWithReferral = canUsedWithReferral;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getStartDateDisplay() {
		return startDateDisplay;
	}
	public void setStartDateDisplay(String startDateDisplay) {
		this.startDateDisplay = startDateDisplay;
	}
	public String getEndDateDisplay() {
		return endDateDisplay;
	}
	public void setEndDateDisplay(String endDateDisplay) {
		this.endDateDisplay = endDateDisplay;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Coupons [couponId=");
		builder.append(couponId);
		builder.append(", couponCode=");
		builder.append(couponCode);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", discountInPercentage=");
		builder.append(discountInPercentage);
		builder.append(", maximumTimeUsed=");
		builder.append(maximumTimeUsed);
		builder.append(", maxDisocunt=");
		builder.append(maxDisocunt);
		builder.append(", canUsedWithReferral=");
		builder.append(canUsedWithReferral);
		builder.append(", action=");
		builder.append(action);
		builder.append(", startDateDisplay=");
		builder.append(startDateDisplay);
		builder.append(", endDateDisplay=");
		builder.append(endDateDisplay);
		builder.append("]");
		return builder.toString();
	}
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public long getDiscountOnMinimumPayablePrice() {
		return discountOnMinimumPayablePrice;
	}
	public void setDiscountOnMinimumPayablePrice(long discountOnMinimumPayablePrice) {
		this.discountOnMinimumPayablePrice = discountOnMinimumPayablePrice;
	}
	
}