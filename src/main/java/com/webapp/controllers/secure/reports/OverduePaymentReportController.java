package com.webapp.controllers.secure.reports;

import java.io.IOException;
import java.util.List;

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

import com.fnf.utils.JQTableUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.FranchiseDto;
import com.webapp.models.BookingModel;
import com.webapp.models.MemberModel;
import com.webapp.services.BookingService;
import com.webapp.services.FranchiseService;
import com.webapp.services.MemberService;
import com.webapp.services.ProjectSerivce;

@Controller
@RequestMapping(value="/report")
public class OverduePaymentReportController extends BusinessController{

	@Autowired
	ProjectSerivce projectSerivce;
	
	@Autowired
	BookingService bookingService;
	
	@Autowired
	private FranchiseService franchiseService;
	
	@Autowired
	private MemberService memberService;
		
	
	@RequestMapping(value="/overdue", method = RequestMethod.GET)
	public String overdue(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		
		List<MemberModel> memberModelList = memberService.fetchMembersList();
		model.addAttribute("memberModelList",memberModelList);
		
		List<FranchiseDto> franchiseeModelList = franchiseService.fetchAllFranchiseList();
		model.addAttribute("franchiseeModelList",franchiseeModelList);
		
		req.setAttribute("isAddAccess", true);
		req.setAttribute("isEditAccess", true);
		req.setAttribute("isDeleteAccess", true);
		return "overdue-payment-report";
	}

	
	@RequestMapping(value = "/overdue/list", produces = "application/json")
	public @ResponseBody DataTablesTO<BookingModel> showOrders(@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {
		System.out.println("\n\t\t overdue list---->");
		DataTablesTO<BookingModel> dt = new DataTablesTO<BookingModel>();
		
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		List<BookingModel> accts = bookingService.fetchOverduePaymentBookingList(tableUtils);

		long count =  bookingService.fetchTotalOverduePaymentBooking(tableUtils);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		return dt;
	}
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/bootstrap/bootstrap-dialog.js","js/viewjs/overdue-payment-report.js" };
	}


	
	
}
