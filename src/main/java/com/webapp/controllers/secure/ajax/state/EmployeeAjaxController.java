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
import com.webapp.models.EmployeeModel;
import com.webapp.services.EmployeeService;
import com.webapp.services.SessionService;


@Controller
public class EmployeeAjaxController extends BusinessApiController {


	@Autowired
	private SessionService sessionService;

	@Value("${SessionCookieName}")
	private String sessionCookieName;


	@Autowired
	private EmployeeService employeeSerivce;


	@RequestMapping(value = "/ajax/employee/changeStatus", method = RequestMethod.DELETE)
	public @ResponseBody ModelAndView deleteCity(@RequestParam("employeeId") String employeeId, HttpServletRequest req, HttpServletResponse res){

		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);

		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		Map<String, Object> outputFinalMap= new HashMap<String,Object>();
		
		EmployeeModel employeeModel=new EmployeeModel();
		
		employeeModel.setUserId(employeeId);
		employeeModel.setUpdatedBy(userId);

		int status = employeeSerivce.changeEmployeeStatus(employeeModel);

		if (status ==0) {
			outputFinalMap.put("error", "done");
		} else {
			outputFinalMap.put("success", "done");
		}

		return getOutputResponse(outputFinalMap );

	}
}