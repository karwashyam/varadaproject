package com.webapp.daos;

import java.util.List;
import java.util.Map;

import com.webapp.models1.State;

public interface StateDao {


	State getStateDetailsById(String stateId);

	int addState(State stateModel);

	List<Map<String, Object>> fetchStatesList(Map<String, Object> inputMap);

	void editState(State stateModel);

	int deleteStateById(State stateModel);


	Long fetchTotalStatesListCount();

}