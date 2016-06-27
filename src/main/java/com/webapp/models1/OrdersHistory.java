package com.webapp.models1;

import com.webapp.models1.AbstractModel;

public class OrdersHistory extends AbstractModel {

	private String orderHistoryId;
	private Integer orderId;
	private String changesDetail;
	private String activityDate;
	private long srNo;
	
	public String getOrderHistoryId() {
		return orderHistoryId;
	}

	public void setOrderHistoryId(String orderHistoryId) {
		this.orderHistoryId = orderHistoryId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getChangesDetail() {
		return changesDetail;
	}

	public void setChangesDetail(String changesDetail) {
		this.changesDetail = changesDetail;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}

	public long getSrNo() {
		return srNo;
	}

	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}
}