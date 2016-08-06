package com.webapp.controllers.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fnf.utils.CookieUtils;
import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController extends BusinessController {


	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);

		if (DbSession.isValidLogin(getDbSession(), sessionService)) {

			getDbSession().destoryCurrentSession(request, response, sessionService, sessionCookieName);
			Cookie cookie = new Cookie(sessionCookieName, "");
			CookieUtils.deleteCookie(cookie, request, response);
			Cookie rememberedCookie = new Cookie(rememberMeCookieName, "");
			if (rememberedCookie != null) {
				CookieUtils.deleteCookie(rememberedCookie, request, response);
			}

		}
		return "redirect:/login";

	}

}