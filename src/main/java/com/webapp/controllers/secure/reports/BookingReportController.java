package com.webapp.controllers.secure.reports;

import java.io.IOException;
import java.util.Calendar;

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
public class BookingReportController extends BusinessController{

	@Autowired
	ProjectSerivce projectSerivce;
	
	@Autowired
	BookingService bookingService;
	

	@Value("${tempDirName}")
	private String tempDirName;
	
	@RequestMapping(value="/booking", method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		System.out.println("\n\t\t book------------");
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		String endYear =  String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		model.addAttribute("startYear", "2015");
		model.addAttribute("endYear", endYear);

//		ProjectModel projectModel=new ProjectModel();
//		ProjectDto projectModel=new ProjectDto();
//		model.addAttribute("projectModel", projectModel);
		return "booking-report";
	}
	
	
	@RequestMapping(value="/bookingdata", method = RequestMethod.GET)
	public String bookingReport(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		System.out.println("\n\t\t bookingdata------------");
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		return "booking-category-report";
	}
	
	
	
	@Override
	protected String[] requiredJs() {
		return new String[] {
				"js/vendor/jquery.validation.min.js",
				"js/chartjs/Chart.min.js",
				"js/vendor/addition-medthods-min.js", 
				"js/viewjs/booking-report.js",
				"js/viewjs/booking-category-report.js", 
				"js/vendor/bootstrap-filestyle.min.js"};
	}


	
	
}
