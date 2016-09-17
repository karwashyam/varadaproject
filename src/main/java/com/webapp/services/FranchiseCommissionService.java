package com.webapp.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.DateUtils;
import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.daos.FranchiseCommissionDao;
import com.webapp.daos.FranchiseDao;
import com.webapp.daos.TdsDao;
import com.webapp.models.FranchiseCommissionModel;
import com.webapp.models.TdsModel;

@Service("franchiseCommissionService")
public class FranchiseCommissionService {

	private static final Logger logger = Logger.getLogger(FranchiseCommissionService.class);

	@Autowired
	private FranchiseCommissionDao franchiseCommissionDao;
	
	@Autowired
	private FranchiseDao franchiseDao;
	
	@Autowired
	private TdsDao tdsDao;

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
	
	@Transactional
	public int addFranchiseCommission(FranchiseCommissionModel franchiseCommissionModel,String userId) {
		long time = DateUtils.nowAsGmtMillisec();
		long amount=franchiseCommissionModel.getPaymentAmount();
		long tdsAmount= Math.round((franchiseCommissionModel.getPaymentAmount()*franchiseCommissionModel.getTds())/100);
		
		//adding in franchisee commission table
		franchiseCommissionModel.setFranchiseeCommissionId(UUIDGenerator.generateUUID());
		franchiseCommissionModel.setCreatedAt(time);
		franchiseCommissionModel.setUpdatedAt(time);
		franchiseCommissionModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		franchiseCommissionModel.setCreatedBy(userId);
		franchiseCommissionModel.setUpdatedBy(userId);
		franchiseCommissionModel.setStatus("D");
		franchiseCommissionModel.setCommissionAmount(amount-tdsAmount);
		franchiseCommissionModel.setTdsAmount(tdsAmount);
		int status=franchiseCommissionDao.addFranchiseCommission(franchiseCommissionModel);
		//--
		String franchiseeId=franchiseCommissionModel.getFranchiseeId();
		String franchiseeName=franchiseCommissionModel.getFranchiseeName();
		status = franchiseDao.updateCommission(amount,tdsAmount,franchiseeId);
		TdsModel tdsModel=new TdsModel();
		tdsModel.setTdsId(UUIDGenerator.generateUUID());
		tdsModel.setFranchiseeId(franchiseeId);
		tdsModel.setFranchiseeName(franchiseeName);
		tdsModel.setFranchiseeCommissionId(franchiseCommissionModel.getFranchiseeCommissionId());
		tdsModel.setTds(franchiseCommissionModel.getTds());
		tdsModel.setTdsAmount(tdsAmount);
		tdsModel.setStatus("C");
		tdsModel.setCreatedAt(time);
		tdsModel.setUpdatedAt(time);
		tdsModel.setCreatedBy(userId);
		tdsModel.setUpdatedBy(userId);
		status = tdsDao.addTds(tdsModel);
		return status;
	}

}