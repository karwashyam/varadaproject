package com.webapp.controllers.api;

import java.util.HashMap;

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
import com.webapp.dto.SignupDto;
import com.webapp.models1.AppUser;
import com.webapp.services.ApiLoginService;
import com.webapp.services.SignupService;
import com.webapp.validator.SignupApiValidator;

@RestController
@RequestMapping("/api/signup")
public class SignupApiController extends BusinessApiController {

	@Autowired
	private ApiLoginService apiLoginService;

	@Autowired
	private SignupService signupService;

	@Autowired
	private SignupApiValidator signupApiValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(signupApiValidator);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addUser(@Validated @RequestBody SignupDto signupDto, HttpServletRequest req, HttpServletResponse res) {
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		AppUser appUser = signupService.addUser(signupDto);
		String apiSessionKey = apiLoginService.createApiSessionKey(appUser.getUserId(), signupDto.getDeviceUid());
		outputMap.put("userId", appUser.getUserId());
		outputMap.put("apiSessionKey", apiSessionKey);
		outputMap.put("appUser", appUser);
		return getOutputResponse(outputMap);

	}
	
	
	
}
