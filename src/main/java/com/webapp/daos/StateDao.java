package com.webapp.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.webapp.models.State;

public interface StateDao {


	State getStateDetailsById(String stateId);

	int addState(State stateModel);

	List<Map<String, Object>> fetchStatesList(Map<String, Object> inputMap);

	void editState(State stateModel);

	int deleteStateById(State stateModel);

	Long fetchTotalStatesListCount(Map<String, Object> inputMap);
	
	List<State> fetchAllStateList();

	boolean isStateNameExists(@Param("stateName") String stateName, @Param("stateId") String stateId);

}