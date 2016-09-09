package com.webapp.controllers.secure.reports;

import java.io.IOException;

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
	

	@RequestMapping(value="/unbooking", method = RequestMethod.GET)
	public String unbookingReport(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		System.out.println("\n\t\t bookingdata------------");
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		return "unbooking-report";
	}
	
	/*@RequestMapping(value="/unbooking/export", method = RequestMethod.GET,produces="application/json")
	public @ResponseBody ModelAndView manageusersListExport(Model model, HttpServletRequest req, HttpServletResponse res,
			@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate,
			@RequestParam("start") int start,@RequestParam("end") int end,@RequestParam("searchString") String searchString) throws ServletException, IOException {

	
		Map<String,Object> inputmap=new HashMap<String,Object>();
		if(!toDate.equals("")){
		long todatelong=DateUtils.changeSimpleDateToLong(toDate);
		long fdate=DateUtils.changeSimpleDateToLong(fromDate);
		
		inputmap.put("fromDate", fdate);
		inputmap.put("toDate", todatelong+MILLIS_PER_DAY-1);
		
		}

		
//		JQTableUtils tableUtils = new JQTableUtils(req);
		if(searchString!=null){
			inputmap.put("searchParams", searchString.trim());
		}
		
		
		inputmap.put("iDisplayStart", start);
		inputmap.put("iDisplayLength", end);
		UserModel useModel=new UserModel();
		List<UserModel> userModelList = new ArrayList<UserModel>();
		userModelList=useModel.fetchUsersList(inputmap);
		
		String filePath = servletContext.getRealPath("tempStorage");
		String fileName ="Users_"+System.currentTimeMillis()+".xls";

		String fileLocation = filePath + "/" + fileName;
		String contentType = "application/vnd.ms-excel";
		inputmap.put("fromDate", fromDate);
		inputmap.put("toDate", toDate);
		generateExcelSheetForUsers(userModelList, fileLocation,inputmap);
		WriteFileToStream(response, contentType, fileName, fileLocation);


		return Response.ok().entity(response.toString()).build();
	}*/
	@Override
	protected String[] requiredJs() {
		return new String[] {
				"js/viewjs/unbooking-report.js",
				"js/vendor/bootstrap-filestyle.min.js"};
	}

	
	
}
