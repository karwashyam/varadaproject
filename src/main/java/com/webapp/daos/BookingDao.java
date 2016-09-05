package com.webapp.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.models.BookingModel;

public interface BookingDao {

	List<BookingModel> fetchBookingList(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchTotalBookingList(@Param("JQTableUtils") JQTableUtils tableUtils);

	int addBooking(BookingModel bookingModel);
	List<BookingModel> fetchBookingListByCurrentYear(Map<String, Object> inputMap);

	BookingModel getBookingDetailsById(@Param("bookingId") String bookingId);

	void changePaidPayment(@Param("bookingId") String bookingId,@Param("paidAmount") long paidAmount,
			@Param("addRemainingBalance") long addRemainingBalance);

	void changeAllotmentLetterGiven(@Param("bookingId") String bookingId);

	void cancelBooking(BookingModel bookingModel);

	void changePaidPaymentForCancelledBooking(@Param("bookingId") String bookingId);

	boolean transferBookingCheck(@Param("bookingId") String bookingId, @Param("memberId") String memberId);

	List<Map<String, Object>> transferBookingIds(@Param("bookingId") String bookingId, @Param("memberId") String memberId);

}
