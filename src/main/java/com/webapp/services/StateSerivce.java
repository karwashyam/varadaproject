package com.webapp.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.daos.StateDao;
import com.webapp.dto.StateDto;
import com.webapp.models.State;

@Service("stateService")
public class StateSerivce {

//	private static final Logger logger = Logger.getLogger(StateSerivce.class);

	@Autowired
	private StateDao stateDao;
	

	public State getStateDetailsById(String stateId) {

		State stateModel = null;

		stateModel = stateDao.getStateDetailsById(stateId);

		return stateModel;
	}


	@Transactional
	public void addState(StateDto stateDto) {
		
		long currentTime = new Date().getTime();
		String stateId = UUIDGenerator.generateUUID();

		// TODO Auto-generated method stub
		State stateModel=new State();
		stateModel.setStateId(stateId);
		stateModel.setStateName(stateDto.getStateName());
		stateModel.setCountryId(stateDto.getCountryId());
		
		
		
		stateModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		stateModel.setCreatedBy("1");
		stateModel.setCreatedAt(currentTime);
		stateModel.setUpdatedBy("1");
		stateModel.setUpdatedAt(currentTime);
		System.out.println("\t\t after serv -"+stateDto.getStateName());

		
		stateDao.addState(stateModel);
	}


	public List<Map<String, Object>> fetchStatesList(int iDisplayLength,
			int iDisplayStart, int serialNo, String sSortDir,
			String columnName, String sSearch) {
		Map<String, Object> inputMap = new HashMap<>();
		inputMap.put("sSortDir", sSortDir);
		inputMap.put("columnName", columnName);
		inputMap.put("sSearch", sSearch);
		inputMap.put("iDisplayStart", iDisplayStart);
		inputMap.put("iDisplayLength", iDisplayLength);


		List<Map<String, Object> > resultList = stateDao.fetchStatesList(inputMap);
		for (Map<String, Object> map : resultList) {
			map.put("srNo", serialNo++);
		}


		return resultList;
	}


	public void editState(StateDto stateDto) {
		
		
		long currentTime = new Date().getTime();

		// TODO Auto-generated method stub
		State stateModel=new State();
		stateModel.setStateId(stateDto.getStateId());
		stateModel.setStateName(stateDto.getStateName());
		stateModel.setCountryId(stateDto.getCountryId());
		
		
		
		stateModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		stateModel.setUpdatedBy("1");
		stateModel.setUpdatedAt(currentTime);
		System.out.println("\t\t after serv -"+stateDto.getStateName());
		
		stateDao.editState(stateModel);
		
	}


}