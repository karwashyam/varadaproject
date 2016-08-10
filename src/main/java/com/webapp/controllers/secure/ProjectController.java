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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.models.ProjectModel;
import com.webapp.services.ProjectSerivce;
import com.webapp.validator.ProjectValidator;

@Controller
@RequestMapping(value="/project")
public class ProjectController extends BusinessController{

	@Autowired
	private ProjectValidator projectValidator;
	
	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	ProjectSerivce projectSerivce;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(projectValidator);
	}
	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("in proj controller ---");
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		ProjectModel projectModel=new ProjectModel();
		
		model.addAttribute("projectModel", projectModel);
		return "add-projects";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addLesson(Model model, @Validated ProjectModel projectModel, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);


			if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}

		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		model.addAttribute("projectModel",projectModel);
		if (result.hasErrors()) {
			model.addAttribute("projectModel",projectModel);
			return "add-projects";
		}

		projectSerivce.addProject(projectModel,userId);

		return pageRedirect("/projects.do");
	}
	
	@RequestMapping(value = "/edit/{projectId}", method = RequestMethod.GET)
	public String editState(Model model, @PathVariable("projectId") String projectId, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);

		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}


		ProjectModel projectModel = projectSerivce.getProjectDetailsById(projectId);

		
		
		model.addAttribute("editProjectFrm", projectModel);
		
		model.addAttribute("title", projectModel.getTitle());
		model.addAttribute("projectId", projectModel.getProjectId());
		model.addAttribute("bookingPrefix", projectModel.getBookingPrefix());
		model.addAttribute("projectOverview", projectModel.getProjectOverview());
		model.addAttribute("totalPlots", projectModel.getTotalPlots());

		
		return "edit-project";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editStatePost(Model model, ProjectModel projectModel, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);

		/*if (!dbSession.checkUrlAccess(sessionService, roleAccessService, FunctionConstant.LESSONS_ADD)) {
			String url = "/access-denied.do";
			return "redirect:" + url;
		}*/

		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		projectSerivce.editProject(projectModel,userId);

		return pageRedirect("/projects.do");
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] {"js/vendor/jquery.validation.min.js", "js/vendor/addition-medthods-min.js", "js/viewjs/add-project.js" };
	}


	
	
}
