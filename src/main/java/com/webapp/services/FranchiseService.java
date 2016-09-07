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
import com.webapp.daos.FranchiseDao;
import com.webapp.dto.FranchiseDto;
import com.webapp.models.FranchiseModel;

@Service("franchiseService")
public class FranchiseService {

	private static final Logger logger = Logger.getLogger(FranchiseService.class);

	@Autowired
	private FranchiseDao franchiseDao;

	@Transactional
	public List<FranchiseDto> fetchFranchiseList(JQTableUtils tableUtils) {
		return franchiseDao.fetchFranchiseList(tableUtils);
	}

	public long fetchTotalEmployeeList(JQTableUtils tableUtils) {
		return franchiseDao.fetchTotalFranchiseList(tableUtils);
	}

	public int postAddFranchise(FranchiseModel franchiseModel, String userId) {
		long time = DateUtils.nowAsGmtMillisec();
		franchiseModel.setFranchiseeId(UUIDGenerator.generateUUID());
		franchiseModel.setCreatedBy(userId);
		franchiseModel.setUpdatedBy(userId);
		franchiseModel.setCreatedAt(time);
		franchiseModel.setUpdatedAt(time);
		franchiseModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		return franchiseDao.postAddFranchise(franchiseModel);
	}

	public boolean fetchFranByEmail(FranchiseModel model) {
		return franchiseDao.fetchFranByEmail(model);
	}

	public boolean fetchFranByPhone(FranchiseModel model) {
		return franchiseDao.fetchFranByPhone(model);
	}

	public boolean fetchFranByPan(FranchiseModel model) {
		return franchiseDao.fetchFranByPan(model);
	}

	public int editFranchise(FranchiseModel franchiseModel) {
		return franchiseDao.editFranchise(franchiseModel);
	}

	public FranchiseModel fetchFranchiseDetail(String franchiseeId) {
		return franchiseDao.fetchFranchiseDetail(franchiseeId);
	}

	public int changeFranchiseStatus(FranchiseModel franchiseModel) {
		return franchiseDao.changeFranchiseStatus(franchiseModel);
	}

	public List<FranchiseDto> fetchAllFranchiseList() {
		return franchiseDao.fetchAllFranchiseList();
	}

	public List<FranchiseModel> fetchFranchiseCommissionList(JQTableUtils tableUtils) {
		return franchiseDao.fetchFranchiseCommissionList(tableUtils);
	}
}