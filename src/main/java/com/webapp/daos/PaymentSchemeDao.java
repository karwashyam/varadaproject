package com.webapp.daos;

import java.util.List;
import java.util.Map;

import com.webapp.models.PaymentSchemeModel;

public interface PaymentSchemeDao {

	public void addPaymentScheme(PaymentSchemeModel projectPaymentSchemeModel);
	public void updatePaymentScheme(PaymentSchemeModel p);
	public List<PaymentSchemeModel> fetchPaymentScheme();
	public void deletePaymentScheme(PaymentSchemeModel projectModel);
	public PaymentSchemeModel getPaymentSchemeDetailsById(String paymentSchemeId);
	public List<Map<String, Object>> fetchPaymentSchemeList(Map<String, Object> inputMap);
	public Long fetchTotalPaymentSchemeListCount();
	public boolean isPaymentSchemeExists(String title);

}
