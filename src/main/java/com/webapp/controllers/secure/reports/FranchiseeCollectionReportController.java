package com.webapp.controllers.secure.reports;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.models.PaymentModel;
import com.webapp.services.BookingService;
import com.webapp.services.PaymentService;
import com.webapp.services.ProjectSerivce;

@Controller
@RequestMapping(value="/report")
public class FranchiseeCollectionReportController extends BusinessController{

	@Autowired
	ProjectSerivce projectSerivce;
	
	@Autowired
	BookingService bookingService;
	
	@Autowired
	PaymentService paymentService;
	
	
	@RequestMapping(value="/franchisee", method = RequestMethod.GET)
	public String overdue(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		req.setAttribute("isAddAccess", true);
		req.setAttribute("isEditAccess", true);
		req.setAttribute("isDeleteAccess", true);
		return "franchisee-collection-report";
	}

	
	@RequestMapping(value = "/franchisee/list", produces = "application/json")
	public @ResponseBody DataTablesTO<PaymentModel> showOrders(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate,@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {
		DataTablesTO<PaymentModel> dt = new DataTablesTO<PaymentModel>();
		
		Map<String,Object> inputmap=new HashMap<String,Object>();
		long todatelong=0l,fdate=0l;
		if(!endDate.equals("")){
			 todatelong=DateUtils.getMilesecFromDateStr(endDate, DateUtils.SiMPLE_DATE_FORMAT, DateUtils.GMT);
			 fdate=DateUtils.getMilesecFromDateStr(startDate, DateUtils.SiMPLE_DATE_FORMAT, DateUtils.GMT);
			
			inputmap.put("startDate", fdate);
			inputmap.put("endDate", todatelong+MILLIS_PER_DAY-1);
			
			}
		JQTableUtils tableUtils = new JQTableUtils(req);
		System.out.println("\n\t\t franchisee list---->"+tableUtils.getiDisplayLength());

		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		List<PaymentModel> accts = paymentService.fetchFranchiseeCollectionPayment(tableUtils,fdate,todatelong);
		
		long count =  paymentService.fetchTotalFranchiseeCollectionPayment(tableUtils,fdate,todatelong);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		return dt;
	}
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/bootstrap/bootstrap-dialog.js","js/viewjs/franchisee-collection-report.js" };
	}


	
	
}
