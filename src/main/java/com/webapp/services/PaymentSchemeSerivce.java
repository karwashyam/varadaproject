package com.webapp.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utils.constant.ProjectConstant;
import com.webapp.daos.PaymentSchemeDao;
import com.webapp.models.PaymentSchemeModel;

@Service("paymentSchemeService")
public class PaymentSchemeSerivce {

//	private static final Logger logger = Logger.getLogger(StateSerivce.class);

	@Autowired
	private PaymentSchemeDao paymentSchemeDao;
	

	public PaymentSchemeModel getPaymentSchemeDetailsById(String paymentSchemeId) {

		PaymentSchemeModel projectModel = null;

		projectModel = paymentSchemeDao.getPaymentSchemeDetailsById(paymentSchemeId);

		return projectModel;
	}



	public List<Map<String, Object>> fetchPaymentSchemeList(int iDisplayLength,
			int iDisplayStart, int serialNo, String sSortDir,
			String columnName, String sSearch) {
		Map<String, Object> inputMap = new HashMap<>();
		inputMap.put("sSortDir", sSortDir);
		inputMap.put("columnName", columnName);
		inputMap.put("sSearch", sSearch);
		inputMap.put("iDisplayStart", iDisplayStart);
		inputMap.put("iDisplayLength", iDisplayLength);


		List<Map<String, Object> > resultList = paymentSchemeDao.fetchPaymentSchemeList(inputMap);
		for (Map<String, Object> map : resultList) {
			map.put("srNo", serialNo++);
		}


		return resultList;
	}
	public Long fetchTotalPaymentSchemeListCount(){
		Long count=0l;
		count=paymentSchemeDao.fetchTotalPaymentSchemeListCount();
		if(count!=null){
			return count;
		}else{
			return 0l;
		}
		
	}


	public int deletePaymentScheme(String paymentScemeId, String userId) {
		int status=-1;
		PaymentSchemeModel projectModel=new PaymentSchemeModel();
		projectModel.setPaymentSchemeId(paymentScemeId);
		projectModel.setUpdatedBy(userId);
			paymentSchemeDao.deletePaymentScheme(paymentScemeId);
		
		return status;
	}


/*	public boolean isProjectNameExists(String title) {
		boolean isExists=false;
		isExists=projectDao.isProjectNameExists(title);
		return isExists;
	}*/

	@Transactional
	public void updatePaymentScheme(PaymentSchemeModel paymentSchemeModel, String userId) {
		
		long currentTime = new Date().getTime();
		
		paymentSchemeModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		paymentSchemeModel.setUpdatedBy(userId);
		paymentSchemeModel.setUpdatedAt(currentTime);
		
		paymentSchemeDao.updatePaymentScheme(paymentSchemeModel);
	}

	@Transactional
	public void addPaymentScheme(PaymentSchemeModel paymentSchemeModel, String userId) {
		
		long currentTime = new Date().getTime();

		paymentSchemeModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		paymentSchemeModel.setCreatedBy(userId);
		paymentSchemeModel.setCreatedAt(currentTime);
		paymentSchemeModel.setUpdatedBy(userId);
		paymentSchemeModel.setUpdatedAt(currentTime);

		paymentSchemeDao.addPaymentScheme(paymentSchemeModel);
		
		
	}



	public boolean isPaymentSchemeExists(String title) {
		boolean isExists=false;
		isExists=paymentSchemeDao.isPaymentSchemeExists(title);
		return isExists;
	}

}