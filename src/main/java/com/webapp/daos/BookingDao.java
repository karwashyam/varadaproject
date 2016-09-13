package com.webapp.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.models.BookingModel;
import com.webapp.models.PaymentModel;

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
	List<Map<String, Object>> fetchBookingListByDate(Map<String, Object> inputMap);
	public List<String> fethBookedPlotsIdListOfProjects();
	long fetchTotalBookingListByDate(Map<String, Object> inputMap);

	List<BookingModel> getBookings();

	int changeDiscount(@Param("bookingId") String bookingId,@Param("discount")  long discount,@Param("penalty")  long penalty,@Param("remainingBalance")  long remainingBalance);

	Long getUnclearAmount(@Param("bookingId") String bookingId);

	int changePaidPaymentForFutureEmi(@Param("list") List<PaymentModel> paymentModelList);

	List<BookingModel> fetchOverduePaymentBookingList(@Param("JQTableUtils") JQTableUtils tableUtils,@Param("startDate") long fdate,@Param("endDate") long todatelong,@Param("memberId") String memberId,@Param("franchiseeId") String franchiseeId);

	long fetchTotalOverduePaymentBooking(@Param("JQTableUtils") JQTableUtils tableUtils, @Param("startDate") long fdate,@Param("endDate") long todatelong,@Param("memberId") String memberId,@Param("franchiseeId") String franchiseeId);

	List<BookingModel> fetchCustomerFilteredBookingList(
			@Param("JQTableUtils") JQTableUtils tableUtils, @Param("startDate") long fdate,@Param("endDate") long todatelong,
			 @Param("reportType")	String reportType);

	long fetchTotalCustomerFilteredBooking(@Param("JQTableUtils") JQTableUtils tableUtils, @Param("startDate") long fdate,@Param("endDate") long todatelong,
			 @Param("reportType")	String reportType);

}
