package com.webapp.models;

public class ProjectModel extends AbstractModel{

	private String projectId;
	private String title;
	private String bookingPrefix;
	private String projectOverview;
	private Integer totalPlots;
	private Integer superBuildupPercentage;
	private Long completionDate;
	private String projectExists;
	
	
	
	
	public String getProjectExists() {
		return projectExists;
	}
	public void setProjectExists(String projectExists) {
		this.projectExists = projectExists;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBookingPrefix() {
		return bookingPrefix;
	}
	public void setBookingPrefix(String bookingPrefix) {
		this.bookingPrefix = bookingPrefix;
	}
	public String getProjectOverview() {
		return projectOverview;
	}
	public void setProjectOverview(String projectOverview) {
		this.projectOverview = projectOverview;
	}
	public Integer getTotalPlots() {
		return totalPlots;
	}
	public void setTotalPlots(Integer totalPlots) {
		this.totalPlots = totalPlots;
	}
	public Long getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(Long completionDate) {
		this.completionDate = completionDate;
	}
	public Integer getSuperBuildupPercentage() {
		return superBuildupPercentage;
	}
	public void setSuperBuildupPercentage(Integer superBuildupPercentage) {
		this.superBuildupPercentage = superBuildupPercentage;
	}
	

}
