package com.webapp.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.JQTableUtils;
import com.webapp.daos.TdsDao;
import com.webapp.models.TdsModel;

@Service("tdsService")
public class TdsService {

	private static final Logger logger = Logger.getLogger(TdsService.class);

	@Autowired
	private TdsDao tdsDao;
	

	@Transactional
	public List<TdsModel> fetchTdsList(JQTableUtils tableUtils) {
		return tdsDao.fetchTdsList(tableUtils);
	}
	public long fetchTotalTdsList(JQTableUtils tableUtils) {
		return tdsDao.fetchTotalTdsList(tableUtils);
	}
	
	public Long fetchTdsDue(){
		return tdsDao.fetchTdsDue();
	}
	public Long fetchTdsCreditDue() {
		return tdsDao.fetchTdsCreditDue();
	}


}