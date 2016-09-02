package com.webapp.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.webapp.models.ProjectPlotsModel;

public class ProjectDto{

	private String projectId;
	private String title;
	private String bookingPrefix;
	private String projectOverview;
	private Integer totalPlots;
	private String completionDate;
	private Integer superBuildupPercentage;
	private String projectExists;
//	private String excelFile;
	private MultipartFile excelFile;
	private List<ProjectPlotsModel> projPlotsList;

	
	
	
	public List<ProjectPlotsModel> getProjPlotsList() {
		return projPlotsList;
	}
	public void setProjPlotsList(List<ProjectPlotsModel> projPlotsList) {
		this.projPlotsList = projPlotsList;
	}
	public MultipartFile getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(MultipartFile excelFile) {
		this.excelFile = excelFile;
	}
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
	
	
	public Integer getSuperBuildupPercentage() {
		return superBuildupPercentage;
	}
	public void setSuperBuildupPercentage(Integer superBuildupPercentage) {
		this.superBuildupPercentage = superBuildupPercentage;
	}
	public String getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

}
