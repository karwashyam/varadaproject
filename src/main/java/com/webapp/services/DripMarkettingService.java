package com.webapp.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.daos.UsersDao;
import com.webapp.dto.DripMarkettingDto;

@Service("dripMarkettingService")
public class DripMarkettingService {

	protected static Logger logger = Logger.getLogger(DripMarkettingService.class);

	@Autowired
	private UsersDao usersDao;
	

	public List<DripMarkettingDto> getDripMarketDetails() {
		return usersDao.getDripMarketDetails();
	}
	
	
	
}