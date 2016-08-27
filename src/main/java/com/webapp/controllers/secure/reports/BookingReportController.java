package com.webapp.controllers.secure.reports;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
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
import com.webapp.services.ProjectSerivce;

@Controller
@RequestMapping(value="/report")
public class BookingReportController extends BusinessController{

	@Autowired
	ProjectSerivce projectSerivce;
	
	@RequestMapping(value="/booking", method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		System.out.println("\n\t\t book------------");
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
//		ProjectModel projectModel=new ProjectModel();
//		ProjectDto projectModel=new ProjectDto();
//		model.addAttribute("projectModel", projectModel);
		return "booking-report";
	}

	
	
	@RequestMapping(value = "/monthly/bookings", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getLineChartDataMonthly(@RequestParam("year") String year, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

//		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);

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
		newRequestMap.put("recordStatus", ProjectConstant.ACTIVE_RECORD_STATUS);
		return null;

	/*	List<MusicModel> musicModelList= musicService.fetchMusicListByCurrentYear(newRequestMap);
		long musicCount [];

		if(musicModelList.size()>0) {
			int musicSize=(musicModelList.get(musicModelList.size()-1).getMonth());
			musicCount=new long [musicSize];

			for(MusicModel musicModel: musicModelList) {
				musicCount[(musicModel.getMonth())-1]=musicModel.getSongsCount();
			}
		}else {
			musicCount=new long [0];
		}

		String [] labelsArray={"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug","Sept","Oct","Nov","Dec"};

		outputMap.put("labelsArray", labelsArray);
		outputMap.put("musicCounts", musicCount);

		return getOutputResponse(outputMap);*/
	}

	
	
	@Override
	protected String[] requiredJs() {
		return new String[] {"js/vendor/jquery.validation.min.js", "js/vendor/addition-medthods-min.js", "js/viewjs/booking-report.js","js/vendor/bootstrap-filestyle.min.js"};
	}


	
	
}
