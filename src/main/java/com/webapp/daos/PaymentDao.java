package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.models.PaymentModel;

public interface PaymentDao {

	int addPayments(@Param("list") List<PaymentModel> paymentModelList);

	List<PaymentModel> getPaymentDetailsByBookingId(@Param("bookingId") String bookingId);

	int disableFuturePayment(PaymentModel paymentModel);

	Long getLatestReceiptNo();

	PaymentModel getPaymentDetailsById(@Param("paymentId") String paymentId);

	List<PaymentModel> fetchPaymentList(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchTotalPaymentList(@Param("JQTableUtils") JQTableUtils tableUtils);

	void editPayment(PaymentModel paymentModel);

	List<PaymentModel> fetchChequeList(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchTotalChequeList(@Param("JQTableUtils") JQTableUtils tableUtils);

	int updatePaymentStatus(@Param("paymentId") String paymentId,@Param("paymentStatusCleared")  String paymentStatusCleared);

	List<PaymentModel> getFutureEmi(@Param("todayDate") long todayDate);

	void updateFutureEmi(@Param("list") List<PaymentModel> paymentModelList);

	List<PaymentModel> fetchFranchiseeCollectionPayment(@Param("JQTableUtils") JQTableUtils tableUtils,@Param("startDate") long startDate,@Param("endDate")long endDate);

	long fetchTotalFranchiseeCollectionPayment(@Param("JQTableUtils") JQTableUtils tableUtils,@Param("startDate") long fdate, @Param("endDate") long todatelong);


	

}
