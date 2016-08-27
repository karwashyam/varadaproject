package com.webapp.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fnf.utils.JQTableUtils;
import com.webapp.daos.BookingDao;
import com.webapp.models.BookingModel;

@Service("bookingService")
public class BookingService {

	private static final Logger logger = Logger.getLogger(BookingService.class);

	@Autowired
	private BookingDao bookingDao;
	

	public List<BookingModel> fetchBookingList(JQTableUtils tableUtils) {
		return bookingDao.fetchBookingList(tableUtils);
	}


	public long fetchTotalBookingList(JQTableUtils tableUtils) {
		return bookingDao.fetchTotalBookingList(tableUtils);
	}
}