package com.webapp.controllers.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.controllers.BusinessApiController;
import com.webapp.services.ApiLoginService;

@Controller
public class LogoutApiController extends BusinessApiController {

	@Autowired
	private ApiLoginService apiLoginService;

	@RequestMapping(value = "/api/logout", method = RequestMethod.DELETE)
	@ResponseBody
	public ModelAndView add(@RequestHeader("x-api-key") String sessionKeyHeader, HttpServletRequest req, HttpServletResponse res) {
		preprocessCheck("logout", req, res);
		String userId = apiLoginService.getUserIdBySessionKey(sessionKeyHeader);
		apiLoginService.deleteApiSessionKey(userId);

		return sendSuccessMessage("You are successfully logged out.");

	}

}