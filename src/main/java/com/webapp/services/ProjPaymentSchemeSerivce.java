package com.webapp.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utils.constant.ProjectConstant;
import com.webapp.daos.ProjectPaymentSchemeDao;
import com.webapp.models.ProjectPaymentSchemeModel;

@Service("projPaymentSchemeService")
public class ProjPaymentSchemeSerivce {

//	private static final Logger logger = Logger.getLogger(StateSerivce.class);

	@Autowired
	private ProjectPaymentSchemeDao projectPaymentSchemeDao;
	

	public ProjectPaymentSchemeModel getProjectsPaymentSchemeById(String projPaymentSchemeId) {

		ProjectPaymentSchemeModel projectModel = null;

		projectModel = projectPaymentSchemeDao.getProjectsPaymentSchemeById(projPaymentSchemeId);

		return projectModel;
	}



	public List<Map<String, Object>> fetchProjPaymentSchemeList(int iDisplayLength,
			int iDisplayStart, int serialNo, String sSortDir,
			String columnName, String sSearch) {
		Map<String, Object> inputMap = new HashMap<>();
		inputMap.put("sSortDir", sSortDir);
		inputMap.put("columnName", columnName);
		inputMap.put("sSearch", sSearch);
		inputMap.put("iDisplayStart", iDisplayStart);
		inputMap.put("iDisplayLength", iDisplayLength);


		List<Map<String, Object> > resultList = projectPaymentSchemeDao.fetchProjPaymentSchemeList(inputMap);
		for (Map<String, Object> map : resultList) {
			map.put("srNo", serialNo++);
		}


		return resultList;
	}
	public Long fetchTotalProjPaymentSchemeListCount(int iDisplayLength, int iDisplayStart, int serialNo, String sSortDir, String columnName, String sSearch){
		Long count=0l;
		Map<String, Object> inputMap = new HashMap<>();
		inputMap.put("sSortDir", sSortDir);
		inputMap.put("columnName", columnName);
		inputMap.put("sSearch", sSearch);
		inputMap.put("iDisplayStart", iDisplayStart);
		inputMap.put("iDisplayLength", iDisplayLength);


		count=projectPaymentSchemeDao.fetchTotalProjPaymentSchemeListCount(inputMap);
		if(count!=null){
			return count;
		}else{
			return 0l;
		}
		
	}


	public int deleteProjectPaymentScheme(String projectPaymentSchemeId, String userId) {
		int status=-1;
		long currentTime = new Date().getTime();

		ProjectPaymentSchemeModel projectModel=new ProjectPaymentSchemeModel();
		projectModel.setProjectPaymentSchemeId(projectPaymentSchemeId);
		projectModel.setUpdatedBy(userId);
		projectModel.setUpdatedAt(currentTime);
		projectPaymentSchemeDao.deleteProjectPaymentScheme(projectModel);
		
		return status;
	}


/*	public boolean isProjectNameExists(String title) {
		boolean isExists=false;
		isExists=projectDao.isProjectNameExists(title);
		return isExists;
	}*/

	@Transactional
	public void updateProjectPaymentScheme(ProjectPaymentSchemeModel paymentSchemeModel, String userId) {
		
		long currentTime = new Date().getTime();
		
		paymentSchemeModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		paymentSchemeModel.setUpdatedBy(userId);
		paymentSchemeModel.setUpdatedAt(currentTime);
		
		projectPaymentSchemeDao.updateProjectPaymentScheme(paymentSchemeModel);
	}

	@Transactional
	public void addProjectPaymentScheme(ProjectPaymentSchemeModel paymentSchemeModel, String userId) {
		
		long currentTime = new Date().getTime();

		paymentSchemeModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		paymentSchemeModel.setCreatedBy(userId);
		paymentSchemeModel.setCreatedAt(currentTime);
		paymentSchemeModel.setUpdatedBy(userId);
		paymentSchemeModel.setUpdatedAt(currentTime);

		projectPaymentSchemeDao.addProjectPaymentScheme(paymentSchemeModel);
		
		
	}



	public boolean isProjPaymentSchemeExists(String paySchemeId,String projectId) {
		boolean isExists=false;
		isExists=projectPaymentSchemeDao.isProjPaymentSchemeExists(paySchemeId,projectId);
		return isExists;
	}



	public List<ProjectPaymentSchemeModel> fetchPaymentScheme() {
		List<ProjectPaymentSchemeModel> paymentSchemeList=projectPaymentSchemeDao.fetchProjectPayementScheme();
		return paymentSchemeList;
	}



}