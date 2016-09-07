package com.webapp.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.JQTableUtils;
import com.utils.constant.ProjectConstant;
import com.webapp.daos.PaymentDao;
import com.webapp.models.PaymentModel;

@Service("paymentService")
public class PaymentService {

	private static final Logger logger = Logger.getLogger(PaymentService.class);

	@Autowired
	private PaymentDao paymentDao;

	@Transactional
	public List<PaymentModel> fetchPaymentList(JQTableUtils tableUtils) {
		return paymentDao.fetchPaymentList(tableUtils);
	}
	public long fetchTotalPaymentList(JQTableUtils tableUtils) {
		return paymentDao.fetchTotalPaymentList(tableUtils);
	}

	public PaymentModel getPaymentDetailsById(String paymentId) {
		// TODO Auto-generated method stub
		return paymentDao.getPaymentDetailsById(paymentId);
	}
	public List<PaymentModel> fetchChequeList(JQTableUtils tableUtils) {
		return paymentDao.fetchChequeList(tableUtils);
	}
	public long fetchTotalChequeList(JQTableUtils tableUtils) {
		// TODO Auto-generated method stub
		return paymentDao.fetchTotalChequeList(tableUtils);
	}
	
	@Transactional
	public int clearCheque(String paymentId, String userId) {
		PaymentModel paymentModel=paymentDao.getPaymentDetailsById(paymentId);
		paymentDao.updatePaymentStatus(paymentId,ProjectConstant.PAYMENT_STATUS_CLEARED);
		return 0;
	}

}