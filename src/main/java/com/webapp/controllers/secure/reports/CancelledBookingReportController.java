package com.webapp.controllers.secure.reports;

import java.io.IOException;
import java.util.Calendar;
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
import org.springframework.web.servlet.ModelAndView;

import com.utils.constant.ProjectConstant;
import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.models.BookingModel;
import com.webapp.services.BookingService;
import com.webapp.services.ProjectSerivce;

@Controller
@RequestMapping(value="/report")
public class CancelledBookingReportController extends BusinessController{

	@Autowired
	ProjectSerivce projectSerivce;
	
	@Autowired
	BookingService bookingService;
	
	
	@RequestMapping(value="/cancelledbooking", method = RequestMethod.GET)
	public String cancelledForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
		return "cancelled-booking-report";
	}

	
	
	
	/*
	@RequestMapping(value = "/monthly/bookings", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getLineChartDataMonthly(@RequestParam("year") String year, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

//		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		System.out.println("\n\t\t book--year----------"+year);

		Map<String, Object> outputMap = new HashMap<String, Object>();

//		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int currentYear = Integer.valueOf(year);
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.YEAR, currentYear);
		calendar.set(Calendar.HOUR_OF_DAY,00);
		calendar.set(Calendar.MINUTE,00);

		long startDate = calendar.getTimeInMillis();

		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.YEAR, currentYear);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);

		long endDate = calendar.getTimeInMillis();

		// new quest requests

		Map<String,Object> newRequestMap = new HashMap<String,Object>();
		newRequestMap.put("startDate", startDate);
		newRequestMap.put("endDate", endDate);
		System.out.println("\n\n\n\t startDate=="+startDate+"\t\t endDate=="+endDate);

		
		newRequestMap.put("recordStatus", ProjectConstant.ACTIVE_RECORD_STATUS);
//		return null;

		List<BookingModel> musicModelList= bookingService.fetchBookingListByCurrentYear(newRequestMap);
		long musicCount [];
			System.out.println("musicModelList=="+musicModelList.size());
		if(musicModelList.size()>0) {
			int musicSize=(musicModelList.get(musicModelList.size()-1).getMonth());
			musicCount=new long [musicSize];

			for(BookingModel musicModel: musicModelList) {
				musicCount[(musicModel.getMonth())-1]=musicModel.getBookingCount();
			}
		}else {
			musicCount=new long [0];
		}

		String [] labelsArray={"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug","Sept","Oct","Nov","Dec"};

		outputMap.put("labelsArray", labelsArray);
		outputMap.put("musicCounts", musicCount);

		return getOutputResponse(outputMap);
	}
*/
	
	
	@Override
	protected String[] requiredJs() {
		return new String[] {
				"js/vendor/jquery.validation.min.js",
				"js/chartjs/Chart.min.js",
				"js/vendor/addition-medthods-min.js", 
				"js/viewjs/cancelled-booking-report.js",
				"js/vendor/bootstrap-filestyle.min.js"};
	}


	
	
}
