package com.webapp.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.daos.CityDao;
import com.webapp.dto.CityDto;
import com.webapp.models.CityModel;

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


	public int postAddCity(CityModel cityModel, String userId) {
		long time=DateUtils.nowAsGmtMillisec();
		cityModel.setCityId(UUIDGenerator.generateUUID());
		cityModel.setCreatedBy(userId);
		cityModel.setUpdatedBy(userId);
		cityModel.setCreatedAt(time);
		cityModel.setUpdatedAt(time);
		cityModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		return cityDao.postAddCity(cityModel);
	}
	
	


}