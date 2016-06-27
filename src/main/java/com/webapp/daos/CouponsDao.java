package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.models1.Coupons;

public interface CouponsDao {

	List<Coupons> fetchCouponsList(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchTotalCoupons(@Param("JQTableUtils") JQTableUtils tableUtils);

	Coupons getCouponDetails(String couponId);

	int updateCoupon(Coupons couponModel);
	
	int addCoupon(Coupons couponModel);
}