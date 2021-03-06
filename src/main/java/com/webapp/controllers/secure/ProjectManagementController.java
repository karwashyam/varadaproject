package com.webapp.controllers.secure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webapp.controllers.BusinessController;


@Controller
@RequestMapping(value = "/projects")
public class ProjectManagementController extends BusinessController {

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
/*
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}
*/
		/*DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		if (!dbSession.checkUrlAccess(sessionService, roleAccessService, FunctionConstant.LESSONS_VIEW)) {
			String url = "/access-denied.do";
			return "redirect:" + url;
		}
*/
		req.setAttribute("title", "Project Management");

		req.setAttribute("isAddAccess", true);
		req.setAttribute("isEditAccess", true);
		req.setAttribute("isDeleteAccess", true);

		return "projects";
	}

	@Override
	protected String[] requiredJs() {

		List<String> requiredJS = new ArrayList<String>();
		requiredJS.add("js/viewjs/project_management.js");
		requiredJS.add("js/bootstrap/bootstrap-dialog.js");
		return requiredJS.toArray(new String[requiredJS.size()]);
	}

}