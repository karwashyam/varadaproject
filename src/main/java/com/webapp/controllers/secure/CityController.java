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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
import com.webapp.models.CityModel;
import com.webapp.services.CityService;
import com.webapp.validator.CityValidator;

@Controller
@RequestMapping("/city")
public class CityController extends BusinessController{


	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private CityService cityService;
	
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
		return "city";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public String addCity(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		CityModel cityModel= new CityModel();
		model.addAttribute("cityModel",cityModel);
		
		return "add-city";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public String postAddCity(Model model,@Validated CityModel cityModel,BindingResult result,
			 HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		
		if (result.hasErrors()) {
			model.addAttribute("cityModel",cityModel);
			return "add-city";
		}
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		
		int status =cityService.postAddCity(cityModel,userId);
		if(status>0){
				model.addAttribute("msg", "Daily Update sent successfully");
		}else{
			model.addAttribute("msg", "Failed to send updates");
		}
		return "city";
	}
	
	@RequestMapping(value = "/list", produces = "application/json")
	public @ResponseBody DataTablesTO<CityDto> showOrders(@RequestParam int sEcho, @RequestParam String sSearch, HttpServletRequest req, HttpServletResponse res) {

		DataTablesTO<CityDto> dt = new DataTablesTO<CityDto>();
		
		JQTableUtils tableUtils = new JQTableUtils(req);
		tableUtils.setSearchParams("%" + sSearch.trim() + "%");
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
//		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		List<CityDto> accts = cityService.fetchCityList(tableUtils);
		for (CityDto cityDto : accts) {
			cityDto.setAction("<a href='"+req.getContextPath()+"/city/edit.do?cityId="+cityDto.getCityId()+"'>Edit&nbsp;<i class='fa fa-edit font-size-17px'></i></a>&nbsp;&nbsp;&nbsp;Delete&nbsp;<i class='fa fa-trash font-size-17px' onClick='deleteCity("+cityDto.getCityId()+")'></i>");
		}
		long count =  cityService.fetchTotalCityList(tableUtils);
		dt.setAaData(accts); // this is the dataset reponse to client
		dt.setiTotalDisplayRecords(Integer.valueOf(String.valueOf(count))); // // the total data in db
		dt.setiTotalRecords(Integer.valueOf(String.valueOf(count))); // the total data in db for
		dt.setsEcho(sEcho);
		return dt;
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/viewjs/city.js" };
	}

	
}
