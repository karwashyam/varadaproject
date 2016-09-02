package com.webapp.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.JQTableUtils;
import com.webapp.daos.BookingDao;
import com.webapp.daos.PaymentSchemeDao;
import com.webapp.daos.ProjectDao;
import com.webapp.models.BookingModel;
import com.webapp.models.PaymentSchemeModel;
import com.webapp.models.ProjectPlotsModel;

@Service("bookingService")
public class BookingService {

	private static final Logger logger = Logger.getLogger(BookingService.class);

	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private PaymentSchemeDao paymentSchemeDao;
	
	@Autowired
	private ProjectDao projectDao;
	

	public List<BookingModel> fetchBookingList(JQTableUtils tableUtils) {
		return bookingDao.fetchBookingList(tableUtils);
	}


	public long fetchTotalBookingList(JQTableUtils tableUtils) {
		return bookingDao.fetchTotalBookingList(tableUtils);
	}


	public List<BookingModel> fetchBookingListByCurrentYear(
			Map<String, Object> newRequestMap) {
		return bookingDao.fetchBookingListByCurrentYear(newRequestMap);
	}
	@Transactional
	public HashMap<String, Object> fetchProjPaymentSchemeANDPlots(String projectId) {
		
		List<PaymentSchemeModel> paymentSchemeList=paymentSchemeDao.fetchPaymentSchemeForProject(projectId);
		List<ProjectPlotsModel> plotList = projectDao.fetchProjectAllPlotsList(projectId);
		
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("paymentSchemeList", paymentSchemeList);
		outputMap.put("plotList", plotList);
		return outputMap;
	}
}