package com.webapp.controllers.api;

import java.util.HashMap;
import java.util.Map;

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
import com.webapp.dto.LoginApi;
import com.webapp.models1.AppUser;
import com.webapp.services.ApiLoginService;
import com.webapp.services.UserSerivce;
import com.webapp.validator.LoginApiValidator;

@RestController
@RequestMapping("/api")
public class LoginApiController extends BusinessApiController {

//	private static final Logger logger = Logger.getLogger(LoginApiController.class);
	
	@Autowired
	private UserSerivce userSerivce;

	@Autowired
	private ApiLoginService apiLoginService;
	

	@Autowired
	private LoginApiValidator loginApiValidator;

	@InitBinder("loginApi")
	private void initLoginBinder(WebDataBinder binder) {
		binder.setValidator(loginApiValidator);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	// @ResponseBody
	public ModelAndView add(@Validated @RequestBody LoginApi loginApi, HttpServletRequest req, HttpServletResponse res) {

		AppUser userModel = userSerivce.getUserFromCredentialsForApi(loginApi.getEmail(), loginApi.getPassword());
		
		String apiSessionKey = apiLoginService.createApiSessionKey(userModel.getUserId(), loginApi.getDeviceUid());
		Map<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("userId", userModel.getUserId());
		outputMap.put("appUser", userModel);
		outputMap.put("apiSessionKey", apiSessionKey);
		return getOutputResponse(outputMap);

	}

}