package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.models.BookingModel;

public interface BookingDao {

	List<BookingModel> fetchBookingList(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchTotalBookingList(@Param("JQTableUtils") JQTableUtils tableUtils);

	int addBooking(BookingModel bookingModel);

}
