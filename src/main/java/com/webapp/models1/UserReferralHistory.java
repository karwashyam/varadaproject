package com.webapp.models1;

public class UserReferralHistory extends AbstractModel {

	private String referralBalanceHistoryId;
	private String userUsed;
	private int amount;
	private int orderId;
	private String userId;
	private String historyNote;
	
	private String usedDate;
	private String userName;

	public String getReferralBalanceHistoryId() {
		return referralBalanceHistoryId;
	}

	public void setReferralBalanceHistoryId(String referralBalanceHistoryId) {
		this.referralBalanceHistoryId = referralBalanceHistoryId;
	}

	public String getUserUsed() {
		return userUsed;
	}

	public void setUserUsed(String userUsed) {
		this.userUsed = userUsed;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHistoryNote() {
		return historyNote;
	}

	public void setHistoryNote(String historyNote) {
		this.historyNote = historyNote;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserReferralHistory [referralBalanceHistoryId=");
		builder.append(referralBalanceHistoryId);
		builder.append(", userUsed=");
		builder.append(userUsed);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", orderId=");
		builder.append(orderId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", historyNote=");
		builder.append(historyNote);
		builder.append("]");
		return builder.toString();
	}

	public String getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(String usedDate) {
		this.usedDate = usedDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}