package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.dto.CityDto;


public interface CityDao {

	List<CityDto> fetchCityList(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchTotalCityList(@Param("JQTableUtils") JQTableUtils tableUtils);

	
}