package com.webapp.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.webapp.models.ProjectModel;
import com.webapp.models.ProjectPlotsModel;

public interface ProjectDao {

	public void addProject(ProjectModel projModel);
	public ProjectModel getProjectDetailsById(String projectId);
	public boolean isProjectNameExists(String title);
	public int deleteProjectById(ProjectModel projectModel);
	public List<Map<String, Object>> fetchProjectsList(Map<String, Object> inputMap);
	public Long fetchTotalProjectListCount(Map<String, Object> inputMap);
	public void editProject(ProjectModel projectModel);
	
	int addProjectPlots(@Param("projectPlotsList") List<ProjectPlotsModel> projPlotsModels);
	public List<Map<String, Object>> fetchProjectPlotsList(
			Map<String, Object> inputMap);
	public Long fetchTotalProjectPlotsListCount();
	public List<ProjectModel> fetchProjects();
	public List<ProjectPlotsModel> fetchProjectPlots(String projectId);
	
	public String fetchPlotIdFromPlotName(@Param("projectId") String projectId,@Param("plotName") String plotName);
	
	public List<ProjectPlotsModel> fetchProjectAllPlotsList(@Param("projectId") String projectId);
	
	public void updateLastBookingCode(ProjectModel projectModel);
	
	int updatePlotStatus(@Param("plotId") String plotId);
	
	int updatePlotStatusToAvailable(@Param("plotId") String plotId);
	public List<Map<String, Object>> fetchUnBookedPlotsBYdate(
			Map<String, Object> inputMap);
	public long fetchTotalUnBookedPlotsBYdate(Map<String, Object> inputMap);

}
