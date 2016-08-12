package com.webapp.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

import com.fnf.utils.CookieUtils;
import com.webapp.dbsession.DbSession;
import com.webapp.exceptions.ResourceNotFoundException;
import com.webapp.services.SessionService;

public class BusinessController extends BaseController {

	private static final String REDIRECT="redirect:";
	private static final Logger logger = Logger.getLogger(BusinessController.class);
	
	@Value("${SessionCookieName}")
	public String sessionCookieName;

	@Value("${RememberMeCookieName}")
	public String rememberMeCookieName;

	@Autowired
	public SessionService sessionService;

	private DbSession dbSession;

	@ExceptionHandler(Exception.class)
	public String handleException(Throwable t) {
		return "redirect:/errorPages/error.jsp";
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public String handleResourceNotFoundException() {
		return "redirect:/errorPages/page-not-found.jsp";
	}

	public HttpSession getSession() {
		return request.getSession(false);

	}

	public void preprocessRequest(Model model, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		setRequest(request);
		setResponse(response);
		setBaseModel(model);
		noCache(response);
		loadCssAndJs();
		setDbSession(DbSession.getSession(request, response, sessionService, sessionCookieName, false));
		deleteHttpSession();
	}

	private void deleteHttpSession() {
		try {
			if (getRequest() != null) {

				HttpSession session = getRequest().getSession(false);
				if (session != null) {
					session.invalidate();
				}
				CookieUtils.deleteJSessionCookie(getRequest(), getResponse());
			}
		} catch (Exception e) {
			logger.error("error,", e);
		}
	}

	protected void loadCssAndJs() {
		String[] commonCss = commonCss();
		String[] commonJs = commonJs();

		String[] requiredCss = requiredCss();
		String[] requiredJs = requiredJs();

		final String contextPath = request.getContextPath();

		final String cssBasePath = contextPath + "/resources/assets/";
		final String jsBasePath = contextPath + "/resources/assets/";

		String cssStr = "";
		String jsStr = "";
		for (String messageKey : commonCss) {
			cssStr += "<link rel='stylesheet' type='text/css' href='" + cssBasePath + messageKey + "' /> \n ";
		}
		for (String messageKey : requiredCss) {
			cssStr += "<link rel='stylesheet' type='text/css' href='" + cssBasePath + messageKey + "' /> \n ";
		}

		for (String messageKey : commonJs) {
			jsStr += "<script type='text/javascript' src='" + jsBasePath + messageKey + "'> </script>\n ";
		}

		for (String messageKey : requiredJs) {
			jsStr += "<script type='text/javascript' src='" + jsBasePath + messageKey + "'> </script> \n ";
		}
		baseModel.addAttribute("contextPath", contextPath);
		baseModel.addAttribute("cssFile", cssStr);
		baseModel.addAttribute("jsFile", jsStr);
	}

	protected String[] requiredCss() {
		return new String[0];
	}

	protected String[] requiredJs() {
		return new String[0];
	}

	private String[] commonCss() {
		return new String[] { "css/bootstrap.min.css","css/font-awesome.min.css","css/bootstrap-datepicker.min.css",
				"css/buttons.bootstrap.min.css","css/custom.min.css"};
	}

	private String[] commonJs() {
		return new String[] {
			        "js/vendor/jquery-1.11.1.min.js", "js/vendor/bootstrap.min.js", 
			      "js/vendor/jquery-ui.js","js/vendor/jquery.dataTables.min.js",
			      "js/vendor/icheck.min.js","js/vendor/daterangepicker.js","js/vendor/bootstrap-datepicker.min.js","js/vendor/custom.min.js","js/main.js"
		};
	}

	private void noCache(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
		response.setDateHeader("Expires", 1);
	}

	public RedirectView redirect(String url) {
		RedirectView rv = new RedirectView(url);
		rv.setExposeModelAttributes(false);
		return rv;
	}

	public DbSession getDbSession() {
		return dbSession;
	}

	public void setDbSession(DbSession dbSession) {
		this.dbSession = dbSession;
	}

	public String pageRedirect(String url) {
		return REDIRECT + url;
	}

}