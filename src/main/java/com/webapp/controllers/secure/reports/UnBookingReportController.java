package com.webapp.controllers.secure.reports;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.services.BookingService;
import com.webapp.services.ProjectSerivce;

@Controller
@RequestMapping(value="/report")
public class UnBookingReportController extends BusinessController{

	@Autowired
	ProjectSerivce projectSerivce;
	
	@Autowired
	BookingService bookingService;
	

	@Value("${tempDirName}")
	private String tempDirName;
	
//	private static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

	@RequestMapping(value="/unbooking", method = RequestMethod.GET)
	public String unbookingReport(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		return "unbooking-report";
		
	}
	
	
	
	@Override
	protected String[] requiredJs() {
		return new String[] {
				"js/viewjs/unbooking-report.js",
				"js/vendor/bootstrap-filestyle.min.js"};
	}

	
	
}
