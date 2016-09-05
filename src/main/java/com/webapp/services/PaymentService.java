package com.webapp.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.JQTableUtils;
import com.webapp.daos.PaymentDao;
import com.webapp.dto.CityDto;
import com.webapp.models.PaymentModel;

@Service("paymentService")
public class PaymentService {

	private static final Logger logger = Logger.getLogger(PaymentService.class);

	@Autowired
	private PaymentDao paymentDao;

	@Transactional
	public List<CityDto> fetchPaymentList(JQTableUtils tableUtils) {
		return null;//cityDao.fetchCityList(tableUtils);
	}

	public long fetchTotalPaymentList(JQTableUtils tableUtils) {
		return 0;//cityDao.fetchTotalCityList(tableUtils);
	}

	public PaymentModel getPaymentDetailsById(String paymentId) {
		// TODO Auto-generated method stub
		return paymentDao.getPaymentDetailsById(paymentId);
	}

}