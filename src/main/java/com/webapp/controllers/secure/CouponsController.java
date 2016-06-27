package com.webapp.controllers.secure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.fnf.utils.UUIDGenerator;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.models1.Coupons;
import com.webapp.services.CouponsService;

@Controller
@RequestMapping("/coupons")
public class CouponsController extends BusinessController {

	protected static Logger logger = Logger.getLogger(CouponsController.class);

	@Autowired
	CouponsService couponsService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);

		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		return "coupons-list";
	}
	
	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<Coupons> getCouponsList(
			@RequestParam int sEcho, @RequestParam String sSearch,
			HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<Coupons> dt = new DataTablesTO<Coupons>();

		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");

		// DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);

		// String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		List<Coupons> accts = couponsService.fetchCouponsList(tableUtils);
		for (Coupons coupon : accts) {
			coupon.setAction("<a href='" + req.getContextPath()
					+ "/coupons/edit.do?couponId=" + coupon.getCouponId()
					+ "'>Edit</a>");

			coupon.setStartDateDisplay(DateUtils.dbTimeStampToSesionDate(coupon.getStartDate(), "IST", "dd-MM-yyyy"));
			coupon.setEndDateDisplay(DateUtils.dbTimeStampToSesionDate(coupon.getEndDate(), "IST", "dd-MM-yyyy"));
		}

		long count = couponsService.fetchTotalCoupons(tableUtils);

		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);

		return dt;
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String getEditCouponForm(@RequestParam("couponId") String couponId,
			Model model, HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		Coupons coupon = couponsService.getCouponDetails(couponId);
		coupon.setCouponId(couponId);
		coupon.setStartDateDisplay(DateUtils.dbTimeStampToSesionDate(coupon.getStartDate(), "IST", "dd-MM-yyyy"));
		coupon.setEndDateDisplay(DateUtils.dbTimeStampToSesionDate(coupon.getEndDate(), "IST", "dd-MM-yyyy"));
		model.addAttribute("couponModel", coupon);

		return "edit-coupon";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String editCoupon(Coupons couponModel, Model model,
			HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		long couponStartDate = DateUtils.getMilesecFromDateStr(couponModel.getStartDateDisplay(), "dd-MM-yyyy", "GMT");
		couponModel.setStartDate(couponStartDate);
		long couponEndDate = DateUtils.getMilesecFromDateStr(couponModel.getEndDateDisplay(), "dd-MM-yyyy", "GMT");
		couponModel.setEndDate(couponEndDate);
		
		if (couponModel.getCouponId() != null && !couponModel.getCouponId().equals("")) {
			int updateStatus = couponsService.updateCoupon(couponModel);
			logger.debug("updateStatus = " + updateStatus);
		}
		else
		{
			couponModel.setCouponId(UUIDGenerator.generateUUID());
			
			int addStatus = couponsService.addCoupon(couponModel);
			logger.debug("addStatus = " + addStatus);
		}
		
		return "redirect:/coupons.do";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String getAddCouponForm(Model model, HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		/*Coupons coupon = couponsService.getCouponDetails(couponId);
		coupon.setStartDateDisplay(DateUtils.dbTimeStampToSesionDate(coupon.getStartDate(), "IST", "dd-MM-yyyy"));
		coupon.setEndDateDisplay(DateUtils.dbTimeStampToSesionDate(coupon.getEndDate(), "IST", "dd-MM-yyyy"));*/
		
		Coupons coupon = new Coupons();
		model.addAttribute("couponModel", coupon);

		return "edit-coupon";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addCoupon(Coupons couponModel, Model model,
			HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}

		long couponStartDate = DateUtils.getMilesecFromDateStr(couponModel.getStartDateDisplay(), "dd-MM-yyyy", "GMT");
		couponModel.setStartDate(couponStartDate);
		long couponEndDate = DateUtils.getMilesecFromDateStr(couponModel.getEndDateDisplay(), "dd-MM-yyyy", "GMT");
		couponModel.setEndDate(couponEndDate);
		
		couponModel.setCouponId(UUIDGenerator.generateUUID());
		
		int addStatus = couponsService.addCoupon(couponModel);
		logger.debug("addStatus = " + addStatus);
		
		return "redirect:/coupons.do";
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/coupons.js" };
	}
}