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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.controllers.DataTablesTO;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.CityDto;
import com.webapp.services.CityService;

@Controller
@RequestMapping("/city")
public class CityController extends BusinessController{


	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	private CityService cityService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
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
			cityDto.setAction("<a href='"+req.getContextPath()+"/city/edit.do?cityId="+cityDto.getCityId()+"'><i class='fa fa-edit font-size-17px'></i></a>  <i class='fa fa-trash font-size-17px' onClick='deleteCity("+cityDto.getCityId()+")'></i>");
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
