package com.webapp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.daos.ProjectDao;
import com.webapp.models.ProjectModel;
import com.webapp.models.ProjectPlotsModel;

@Service("projectSerivce")
public class ProjectSerivce {

//	private static final Logger logger = Logger.getLogger(StateSerivce.class);

	@Autowired
	private ProjectDao projectDao;
	

	public ProjectModel getProjectDetailsById(String projectId) {

		ProjectModel projectModel = null;

		projectModel = projectDao.getProjectDetailsById(projectId);

		return projectModel;
	}



	public List<Map<String, Object>> fetchProjectList(int iDisplayLength,
			int iDisplayStart, int serialNo, String sSortDir,
			String columnName, String sSearch) {
		Map<String, Object> inputMap = new HashMap<>();
		inputMap.put("sSortDir", sSortDir);
		inputMap.put("columnName", columnName);
		inputMap.put("sSearch", sSearch);
		inputMap.put("iDisplayStart", iDisplayStart);
		inputMap.put("iDisplayLength", iDisplayLength);


		List<Map<String, Object> > resultList = projectDao.fetchProjectsList(inputMap);
		for (Map<String, Object> map : resultList) {
			map.put("srNo", serialNo++);
		}


		return resultList;
	}
	public Long fetchTotalProjectListCount(){
		Long count=0l;
		count=projectDao.fetchTotalProjectListCount();
		if(count!=null){
			return count;
		}else{
			return 0l;
		}
		
	}


	public int deleteProjectById(String projectId, String userId) {
		int status=-1;
		ProjectModel projectModel=new ProjectModel();
		projectModel.setProjectId(projectId);
		projectModel.setUpdatedBy(userId);
		status=projectDao.deleteProjectById(projectModel);
		
		return status;
	}


	public boolean isProjectNameExists(String title) {
		boolean isExists=false;
		isExists=projectDao.isProjectNameExists(title);
		return isExists;
	}

	@Transactional
	public void editProject(ProjectModel projectModel, String userId) {
		
		long currentTime = new Date().getTime();
		
		projectModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		projectModel.setUpdatedBy(userId);
		projectModel.setUpdatedAt(currentTime);
		
		projectDao.editProject(projectModel);
	}

	@Transactional
	public void addProject(ProjectModel projectModel, String userId) {
		
		ProjectPlotsModel projectPlotsModel=null;
		long currentTime = new Date().getTime();
		String projectId = UUIDGenerator.generateUUID();

		projectModel.setProjectId(projectId);		
		projectModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		projectModel.setCreatedBy(userId);
		projectModel.setCreatedAt(currentTime);
		projectModel.setUpdatedBy(userId);
		projectModel.setUpdatedAt(currentTime);

		projectDao.addProject(projectModel);
		
		List<ProjectPlotsModel> projectPlotsModels=new ArrayList<ProjectPlotsModel>();

		
		for(int i=1;i<=projectModel.getTotalPlots();i++){
			projectPlotsModel=new ProjectPlotsModel();

			projectPlotsModel.setProjectPlotId(UUIDGenerator.generateUUID());
			projectPlotsModel.setPlotNo(i);
			projectPlotsModel.setPlotName("Default_PlotName");
			projectPlotsModel.setProjectId(projectId);
			projectPlotsModel.setPlotSize(projectModel.getPlotSize());
			projectPlotsModel.setCreatedAt(projectModel.getCreatedAt());
			projectPlotsModel.setCreatedBy(projectModel.getCreatedBy());
			projectPlotsModel.setUpdatedAt(projectModel.getUpdatedAt());
			projectPlotsModel.setUpdatedBy(projectModel.getUpdatedBy());
			projectPlotsModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);

			projectPlotsModels.add(projectPlotsModel);
		}
		
		projectDao.addProjectPlots(projectPlotsModels);
		
		
	}


}