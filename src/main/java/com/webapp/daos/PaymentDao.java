package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapp.models.PaymentModel;

public interface PaymentDao {

	int addPayments(@Param("list") List<PaymentModel> paymentModelList);

	

}
