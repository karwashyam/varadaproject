package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.dto.CityDto;
import com.webapp.models.CityModel;


public interface CityDao {

	List<CityDto> fetchCityList(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchTotalCityList(@Param("JQTableUtils") JQTableUtils tableUtils);

	int postAddCity(CityModel cityModel);
	
	boolean fetchCityByName(CityModel cityModel);
	
	CityModel fetchCityDetailsById(String cityId);
	
	void editCity(CityModel cityModel);
	
	int deleteCityById(CityModel cityModel);

	List<CityDto> fetchCityFromStateId(@Param("stateId") String stateId);
	
}