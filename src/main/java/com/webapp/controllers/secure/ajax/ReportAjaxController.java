package com.webapp.controllers.secure.ajax;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.utils.constant.ProjectConstant;
import com.webapp.controllers.BusinessApiController;
import com.webapp.models.BookingModel;
import com.webapp.services.BookingService;
import com.webapp.services.SessionService;

@Controller
@RequestMapping(value = "/report/ajax")
public class ReportAjaxController extends BusinessApiController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private SessionService sessionService;

	@Value("${SessionCookieName}")
	private String sessionCookieName;

	@RequestMapping(value = "/monthly/bookings", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getLineChartDataMonthly(@RequestParam("year") String year, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
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
			System.out.println("musicModelList.get(musicModelList.size()-1).getMonth()=="+musicModelList.get(musicModelList.size()-1).getMonth());

			int musicSize=(musicModelList.get(musicModelList.size()-1).getMonth());
			musicCount=new long [musicSize];

			for(BookingModel musicModel: musicModelList) {
				
				System.out.println("\t musicModel.getMonth-1=====>"+((musicModel.getMonth())-1));
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

	
	@RequestMapping(value = "/monthly/canbookings", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getCancelledLineChartDataMonthly(@RequestParam("year") String year, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
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

		
		newRequestMap.put("recordStatus", "D");
//		return null;

		List<BookingModel> musicModelList= bookingService.fetchBookingListByCurrentYear(newRequestMap);
		long musicCount [];
			System.out.println("musicModelList=="+musicModelList.size());
		if(musicModelList.size()>0) {
			System.out.println("musicModelList.get(musicModelList.size()-1).getMonth()=="+musicModelList.get(musicModelList.size()-1).getMonth());

			int musicSize=(musicModelList.get(musicModelList.size()-1).getMonth());
			musicCount=new long [musicSize];

			for(BookingModel musicModel: musicModelList) {
				
				System.out.println("\t musicModel.getMonth-1=====>"+((musicModel.getMonth())-1));
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

}