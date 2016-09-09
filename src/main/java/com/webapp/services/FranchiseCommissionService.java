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
import com.webapp.daos.FranchiseCommissionDao;
import com.webapp.daos.FranchiseDao;
import com.webapp.dto.FranchiseDto;
import com.webapp.models.FranchiseCommissionModel;
import com.webapp.models.FranchiseModel;

@Service("franchiseCommissionService")
public class FranchiseCommissionService {

	private static final Logger logger = Logger.getLogger(FranchiseCommissionService.class);

	@Autowired
	private FranchiseCommissionDao franchiseCommissionDao;

	@Transactional
	public List<FranchiseCommissionModel> fetchFranchiseCommissionList(String franchiseeId) {
		return franchiseCommissionDao.fetchFranchiseCommissionList(franchiseeId);
	}

	public long fetchTotalEmployeeList() {
		return franchiseCommissionDao.fetchTotalFranchiseList();
	}

	public List<FranchiseCommissionModel> fetchAllFranchiseCommissionList() {
		return franchiseCommissionDao.fetchAllFranchiseCommissionList();
	}

}