package com.webapp.controllers.secure;

import java.io.IOException;

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

import com.fnf.utils.EncryptionUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.ChangePasswordApiDto;
import com.webapp.models.User;
import com.webapp.services.UserSerivce;
import com.webapp.validator.ChangePasswordValidator;

@Controller
@RequestMapping(value = "/change-password")
public class ChangePasswordController extends BusinessController {

	@Autowired
	private UserSerivce userSerivce;

	@Autowired
	private ChangePasswordValidator changePasswordValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(changePasswordValidator);
	}

	@Value("${DefaultDashBoardURL}")
	private String defaultDashboardUrl;


	@RequestMapping(method = RequestMethod.GET)
	public String addCity(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		ChangePasswordApiDto changePasswordApiDto= new ChangePasswordApiDto();
		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
		changePasswordApiDto.setUserId(userId);
		model.addAttribute("changePasswordApiDto",changePasswordApiDto);
		return "change-password";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(Model model, @Validated ChangePasswordApiDto changePasswordApiDto, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login";
			return "redirect:" + url;
		}
		
		if (result.hasErrors()) {
			model.addAttribute("changePasswordApiDto",changePasswordApiDto);
			return "change-password";
		}
		User userModel= new User();
		userModel.setUserId(changePasswordApiDto.getUserId());
		userModel.setPassword(EncryptionUtils.encryptPassword(changePasswordApiDto.getNewPassword()));
		userSerivce.updatePassword(userModel);
		model.addAttribute("msg","Password Changed Successfully");
		return "home";
	}


}