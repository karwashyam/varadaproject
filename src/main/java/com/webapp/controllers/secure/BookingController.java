package com.webapp.controllers.secure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fnf.utils.JQTableUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.CityDto;
import com.webapp.models.BookingModel;
import com.webapp.services.BookingService;
import com.webapp.validator.CityValidator;

@Controller
@RequestMapping("/booking")
public class BookingController extends BusinessController{


	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private BookingService bookingService;
		
	@Autowired
	private CityValidator cityValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(cityValidator);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		req.setAttribute("isAddAccess", true);
		req.setAttribute("isEditAccess", true);
		req.setAttribute("isDeleteAccess", true);
		return "booking";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public String addCity(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		BookingModel bookingModel= new BookingModel();
		model.addAttribute("bookingModel",bookingModel);
//		List<State> stateList = stateService.fetchAllStateList();
//		model.addAttribute("stateModel", stateList);
		return "add-booking";
	}
	
//	@RequestMapping(value = "/add",method = RequestMethod.POST)
//	public String postAddCity(Model model,@Validated CityModel cityModel,BindingResult result,
//			 HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
//
//		preprocessRequest(model, req, res);
//		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
//			String url = "/login";
//			return "redirect:" + url;
//		}
//		
//		if (result.hasErrors()) {
//			model.addAttribute("cityModel",cityModel);
//			List<State> stateList = stateService.fetchAllStateList();
//			model.addAttribute("stateModel", stateList);
//			return "add-city";
//		}
//		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
//		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
//		int status =cityService.postAddCity(cityModel,userId);
//		if(status>0){
//				model.addAttribute("msg", "Daily Update sent successfully");
//		}else{
//			model.addAttribute("msg", "Failed to send updates");
//		}
//		return "city";
//	}
	
	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<BookingModel> showOrders(@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<BookingModel> dt = new DataTablesTO<BookingModel>();
		
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
//		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		List<BookingModel> accts = bookingService.fetchBookingList(tableUtils);

		long count =  bookingService.fetchTotalBookingList(tableUtils);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		return dt;
	}
	
//	@RequestMapping(value = "/edit-city/{cityId}", method = RequestMethod.GET)
//	public String editState(Model model, @PathVariable("cityId") String cityId, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//
//		preprocessRequest(model, req, res);
//
//		
//		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
//			String url ="/login.do";
//			return "redirect:" + url;
//		}
//
//
//		CityModel cityModel = cityService.fetchCityDetailsById(cityId);
//		
//		model.addAttribute("cityModel", cityModel);
//		
//		model.addAttribute("stateId", cityModel.getStateId());
//		List<State> stateList = stateService.fetchAllStateList();
//		model.addAttribute("stateModel", stateList);
//		return "/edit-city";
//	}
//	
//	@RequestMapping(value = "/edit-city", method = RequestMethod.POST)
//	public String editStatePost(Model model, @Validated CityModel cityModel, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//
//		preprocessRequest(model, req, res);
//		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
//		if (!dbSession.isValidLogin(getDbSession(), sessionService)) {
//			String url ="/login.do";
//			return "redirect:" + url;
//		}
//		if (result.hasErrors()) {
//			model.addAttribute("cityModel",cityModel);
//			model.addAttribute("stateId", cityModel.getStateId());
//			List<State> stateList = stateService.fetchAllStateList();
//			model.addAttribute("stateModel", stateList);
//			return "edit-city";
//		}
//		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
//		cityService.editCity(cityModel,userId);
//
//		return pageRedirect("/city");
//	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/viewjs/booking.js" };
	}

	
}
