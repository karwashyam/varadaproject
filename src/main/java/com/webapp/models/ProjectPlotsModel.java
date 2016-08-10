package com.webapp.models;

public class ProjectPlotsModel extends AbstractModel{

	private String projectPlotId;
	private String projectId;
	private String plotName;
	private Long plotSize;
	private Integer plotNo;
	
	
	public String getProjectPlotId() {
		return projectPlotId;
	}
	public void setProjectPlotId(String projectPlotId) {
		this.projectPlotId = projectPlotId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getPlotName() {
		return plotName;
	}
	public void setPlotName(String plotName) {
		this.plotName = plotName;
	}
	public Long getPlotSize() {
		return plotSize;
	}
	public void setPlotSize(Long plotSize) {
		this.plotSize = plotSize;
	}
	public Integer getPlotNo() {
		return plotNo;
	}
	public void setPlotNo(Integer plotNo) {
		this.plotNo = plotNo;
	}

}
