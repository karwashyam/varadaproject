package com.webapp.controllers.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.controllers.BusinessApiController;
import com.webapp.exceptions.BusinessErrorException;
import com.webapp.models1.AppUser;
import com.webapp.services.ForgotPasswordService;
import com.webapp.validator.ForgotPasswordValidator;


@RestController
@RequestMapping("/api")
public class ForgotPasswordApiController extends BusinessApiController{

	@Autowired
	private ForgotPasswordValidator forgotPasswordValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(forgotPasswordValidator);
	}
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public ModelAndView addOfficialDetails(@Validated @RequestBody AppUser user, HttpServletRequest req, HttpServletResponse res) {
		
		
		int status = forgotPasswordService.setNewPassword(user);
		
		if (status > 0) {
			return sendSuccessMessage("New password sent successfully");
		} else {
			throw new BusinessErrorException("Failed to send new password");
		}
		
	}
	
}
