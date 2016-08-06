package com.webapp.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.JQTableUtils;
import com.webapp.daos.CityDao;
import com.webapp.dto.CityDto;

@Service("cityService")
public class CityService {

	private static final Logger logger = Logger.getLogger(CityService.class);

	@Autowired
	private CityDao cityDao;
	
	
	@Transactional
	public List<CityDto> fetchCityList(JQTableUtils tableUtils) {
		return cityDao.fetchCityList(tableUtils);
	}


	public long fetchTotalCityList(JQTableUtils tableUtils) {
		return cityDao.fetchTotalCityList(tableUtils);
	}
	
	


}