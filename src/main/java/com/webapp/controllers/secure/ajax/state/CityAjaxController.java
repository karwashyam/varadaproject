package com.webapp.controllers.secure.ajax.state;

import java.util.HashMap;
import java.util.Map;

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

import com.webapp.controllers.BusinessApiController;
import com.webapp.dbsession.DbSession;
import com.webapp.models.CityModel;
import com.webapp.services.CityService;
import com.webapp.services.SessionService;


@Controller
public class CityAjaxController extends BusinessApiController {


	@Autowired
	private SessionService sessionService;

	@Value("${SessionCookieName}")
	private String sessionCookieName;


	@Autowired
	private CityService citySerivce;


	@RequestMapping(value = "/ajax/city/delete", method = RequestMethod.DELETE)
	public @ResponseBody ModelAndView deleteCity(@RequestParam("cityId") String cityId, HttpServletRequest req, HttpServletResponse res){

		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);

		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		Map<String, Object> outputFinalMap= new HashMap<String,Object>();
		
		CityModel cityModel=new CityModel();
		
		cityModel.setCityId(cityId);
		cityModel.setUpdatedBy(userId);

		int status = citySerivce.deleteCityById(cityModel);

		if (status ==0) {

			outputFinalMap.put("error", "done");
		} else {

			outputFinalMap.put("success", "done");
		}

		return getOutputResponse(outputFinalMap );

	}
}