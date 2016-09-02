package com.webapp.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.models.BookingModel;

public interface BookingDao {

	List<BookingModel> fetchBookingList(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchTotalBookingList(@Param("JQTableUtils") JQTableUtils tableUtils);

	List<BookingModel> fetchBookingListByCurrentYear(Map<String, Object> inputMap);

}
