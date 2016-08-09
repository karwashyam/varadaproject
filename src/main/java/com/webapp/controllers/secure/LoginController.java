package com.webapp.controllers.secure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fnf.utils.CookieUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.dto.Login;
import com.webapp.models.User;
import com.webapp.services.UserSerivce;
import com.webapp.validator.LoginValidator;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends BusinessController {

	private static final Logger logger = Logger.getLogger(LoginController.class);

	private static final String LOGIN = "login";

	@Autowired
	private UserSerivce userSerivce;

	@Autowired
	private LoginValidator loginValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(loginValidator);
	}

	@Value("${DefaultDashBoardURL}")
	private String defaultDashboardUrl;

	@Autowired
	private MessageSource messageSource;

	

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);

		Login loginForm = new Login();

		model.addAttribute(LOGIN, loginForm);

		if (DbSession.isValidLogin(getDbSession(), sessionService)) {
			DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);

			String url = dbSession.getAttribute(DbSession.DASHBOARD_URL, sessionService);
			logger.info("in login action");
			if (url == null) {
				url = "/error.jsp";
			}

			return "redirect:"+url;

		} else {
			Cookie rememberedCookie = CookieUtils.getCookie(rememberMeCookieName, request);
			if (rememberedCookie != null) {
				String userId = DbSession.getUserIdByRememberMeId(rememberedCookie.getValue(), sessionService);

				if (userId != null && userId.length() > 0) {
					User userModel = userSerivce.getUserAccountDetailsById(userId);
					DbSession session = DbSession.createSession(userModel.getUserId(), request, response, sessionService, sessionCookieName);
					String url = DbSession.processPostLogin(userModel, session, sessionService, defaultDashboardUrl);

					return "redirect:"+url;
				}
			}
		}

		return LOGIN;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(Model model, @Validated Login login, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);

		model.addAttribute(LOGIN, login);

		if (DbSession.isValidLogin(getDbSession(), sessionService)) {
			DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);

			String url = dbSession.getAttribute(DbSession.DASHBOARD_URL, sessionService);
			logger.info("in login action");
			if (url == null) {
				url = "/error.jsp";
			}
			return pageRedirect(url);
		}

		if (result.hasErrors()) {

			return LOGIN;
		} else {
			model.addAttribute(LOGIN, login);
		}

		User userModel = userSerivce.getUserAccountDetailsById(login.getUserName());

		DbSession session = DbSession.createSession(userModel.getUserId(), request, response, sessionService, sessionCookieName);
		String url = DbSession.processPostLogin(userModel, session, sessionService, defaultDashboardUrl);
		if (url == null) {
			return pageRedirect(url);
		}
		/*if (login.isRememberme()) {
			if (userModel != null) {
				String rememberMeId = DbSession.addRememberMeKey(userModel.getUserId(), sessionService);
				logger.debug(" rememberMeId--> " + rememberMeId);
				Cookie userCookie = new Cookie(rememberMeCookieName, rememberMeId + "");

				String ctxPath = request.getContextPath();
				if (ctxPath.length() == 0) {
					ctxPath = "/";
				}
				userCookie.setPath(ctxPath);
				CookieUtils.setCookie(userCookie, request, response);
				userCookie.setMaxAge(7 * 24 * 60 * 60);
				response.addCookie(userCookie);
			}
		} else {
			if (userModel != null) {
				DbSession.deleteRememberMeKey(userModel.getUserId(), sessionService);
			}
		}*/
		return pageRedirect(url);
	}

	public String getMessage(FieldError field) {
		return messageSource.getMessage(field, null);
	}
	
	

}