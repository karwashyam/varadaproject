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
	public int addTds(TdsModel tdsModel, String userId) {
		long time = DateUtils.nowAsGmtMillisec();
		
		tdsModel.setTdsId(UUIDGenerator.generateUUID());
		tdsModel.setCreatedBy(userId);
		tdsModel.setUpdatedAt(time);
		tdsModel.setUpdatedBy(userId);
		tdsModel.setCreatedAt(time);
		tdsModel.setStatus(ProjectConstant.PAYMENT_TYPE_DEBIT);
		tdsModel.setChequeDate(DateUtils.getMilesecFromDateStr(tdsModel.getChequeDateString(), "dd/mm/yyyy", "GMT"));
		tdsModel.setPaymentMode("Cheque");
		return tdsDao.addTds(tdsModel);
		
	}


}