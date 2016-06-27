package com.webapp.controllers.api;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.controllers.BusinessApiController;
import com.webapp.exceptions.BusinessErrorException;
import com.webapp.models1.AppUser;
import com.webapp.services.ApiLoginService;
import com.webapp.services.AppUserService;
import com.webapp.services.ForgotPasswordService;
import com.webapp.validator.ProfileValidator;


@RestController
@RequestMapping("/api/profile")
public class ProfileApiController extends BusinessApiController{

	@Autowired
	private ProfileValidator profileValidator;
	
	@Autowired
	private ApiLoginService apiLoginService;
	
	@Autowired
	private AppUserService appUserService;

	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(profileValidator);
	}
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getProfile(@RequestHeader("x-api-key") String sessionKeyHeader,@RequestParam(value = "updatedAt", required = false) Long updatedAt, HttpServletRequest req, HttpServletResponse res) {
		preprocessCheck("get cities", req, res);
		if(updatedAt==null){
			updatedAt=0l;
		}
		String userId = apiLoginService.getUserIdBySessionKey(sessionKeyHeader);
		HashMap<String, Object> outputMap = appUserService.getProfiles(updatedAt, userId);
		return getOutputResponse(outputMap);

	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView updateProfile(@RequestHeader("x-api-key") String sessionKeyHeader,@Validated @RequestBody AppUser appUser, HttpServletRequest req, HttpServletResponse res) {
		preprocessCheck("get cities", req, res);
		String userId = apiLoginService.getUserIdBySessionKey(sessionKeyHeader);
		int status = appUserService.updateProfile(appUser,userId);
		if (status > 0) {
			return sendSuccessMessage("Profile Details updated successfully");
		} else {
			throw new BusinessErrorException("Failed to update profile details");
		}

	}

	
}
