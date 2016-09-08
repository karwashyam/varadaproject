package com.webapp.controllers.secure.state;

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
import com.webapp.dto.StateDto;
import com.webapp.models.State;
import com.webapp.services.StateSerivce;
import com.webapp.validator.StateValidator;

@Controller
public class StateController extends BusinessController{

	@Autowired
	private StateValidator stateValidator;
	
	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	StateSerivce stateService;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(stateValidator);
	}
	@RequestMapping(value="/add-state", method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("in state controller ---");
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		StateDto stateDto=new StateDto();
		
		model.addAttribute("stateDto", stateDto);
		return "secure/master/state";
	}

	@RequestMapping(value = "/add-state", method = RequestMethod.POST)
	public String addLesson(Model model, @Validated StateDto stateDto, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);


			if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}

		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		model.addAttribute("stateFrm",stateDto);
		System.out.println("\n\t\t =========result.hasErrors()=======>"+result.hasErrors());
		if (result.hasErrors()) {
			model.addAttribute("stateDto",stateDto);
			return "secure/master/state";
		}

		stateService.addState(stateDto,userId);

		return pageRedirect("/states.do");
	}
	
	@RequestMapping(value = "/edit-state/{stateId}", method = RequestMethod.GET)
	public String editState(Model model, @PathVariable("stateId") String stateId, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);

		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}


		State stateModel = stateService.getStateDetailsById(stateId);

		
		
		StateDto stateDto=new StateDto();
		stateDto.setStateName(stateModel.getStateName());
		stateDto.setStateId(stateModel.getStateId());
		model.addAttribute("editstateFrm", stateDto);
		
		model.addAttribute("stateName", stateDto.getStateName());
		model.addAttribute("stateId", stateDto.getStateId());
		
		return "/secure/master/edit-state";
	}
	
	@RequestMapping(value = "/edit-state", method = RequestMethod.POST)
	public String editStatePost(Model model, StateDto stateDto, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);

		/*if (!dbSession.checkUrlAccess(sessionService, roleAccessService, FunctionConstant.LESSONS_ADD)) {
			String url = "/access-denied.do";
			return "redirect:" + url;
		}*/

		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);

		stateService.editState(stateDto,userId);

		return pageRedirect("/states.do");
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] {"js/vendor/jquery.validation.min.js", "js/vendor/addition-medthods-min.js", "js/viewjs/add-state.js" };
	}


	
	
}
