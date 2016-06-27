package com.webapp.services;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fnf.utils.JQTableUtils;
import com.webapp.daos.CouponsDao;
import com.webapp.models1.Coupons;

@Service("couponsService")
public class CouponsService {

	protected static Logger logger = Logger.getLogger(CouponsService.class);

	@Autowired
	CouponsDao couponsDao;

	public List<Coupons> fetchCouponsList(
			@Param("JQTableUtils") JQTableUtils tableUtils) {
		return couponsDao.fetchCouponsList(tableUtils);
	}

	public long fetchTotalCoupons(@Param("JQTableUtils") JQTableUtils tableUtils) {
		return couponsDao.fetchTotalCoupons(tableUtils);
	}

	public Coupons getCouponDetails(String couponId) {
		return couponsDao.getCouponDetails(couponId);
	}

	public int updateCoupon(Coupons couponModel) {
		return couponsDao.updateCoupon(couponModel);
	}

	public int addCoupon(Coupons couponModel) {
		return couponsDao.addCoupon(couponModel);
	}
}