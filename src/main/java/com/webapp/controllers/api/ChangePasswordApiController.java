package com.webapp.controllers.api;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fnf.utils.EncryptionUtils;
import com.webapp.controllers.BusinessApiController;
import com.webapp.dto.ChangePasswordApiDto;
import com.webapp.exceptions.BusinessErrorException;
import com.webapp.services.ApiLoginService;
import com.webapp.services.AppUserService;
import com.webapp.validator.ChangePasswordApiValidator;

@RestController
@RequestMapping("/api/change-password")
public class ChangePasswordApiController extends BusinessApiController {

	@Autowired
	private ApiLoginService apiLoginService;

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private ChangePasswordApiValidator changePasswordApiValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(changePasswordApiValidator);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView changePassword(@RequestHeader("x-api-key") String sessionKeyHeader, @Validated @RequestBody ChangePasswordApiDto changePasswordApi, HttpServletRequest req, HttpServletResponse res) {

		preprocessCheck("changePassword", req, res);
		String userId = apiLoginService.getUserIdBySessionKey(sessionKeyHeader);
		int status = 0;

		status = appUserService.updatePassword(userId, EncryptionUtils.encryptPassword(changePasswordApi.getNewPassword()));
		if (status > 0) {
			return sendSuccessMessage("Password changed successfully");
		} else {
			throw new BusinessErrorException("Failed to change password");
		}

	}
}
