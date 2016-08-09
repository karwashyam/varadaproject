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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webapp.controllers.BusinessController;
import com.webapp.dto.StateDto;
import com.webapp.models.State;
import com.webapp.services.StateSerivce;

@Controller
public class StateController extends BusinessController{


	@Value("${tempDirName}")
	private String tempDirName;
	
	@Value("${S3BucketName}")
	private String s3BucketName;

	@Autowired
	StateSerivce stateService;
	
	@RequestMapping(value="/add-state", method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("in state controller ---");
		preprocessRequest(model, req, res);
	/*	if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}*/
		StateDto stateDto=new StateDto();
		
		model.addAttribute("stateFrm", stateDto);
		return "secure/master/state";
	}

	@RequestMapping(value = "/add-state", method = RequestMethod.POST)
	public String addLesson(Model model, StateDto stateDto, BindingResult result, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);


		/*	if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}

		DbSession dbSession = DbSession.getSession(req, res, sessionService, sessionCookieName, false);
		String userId = dbSession.getAttribute(DbSession.USER_ID, sessionService);
*/

		/*if (!dbSession.checkUrlAccess(sessionService, roleAccessService, FunctionConstant.LESSONS_ADD)) {
			String url = "/access-denied.do";
			return "redirect:" + url;
		}*/

		stateService.addState(stateDto);

		return pageRedirect("/states.do");
	}
	
	@RequestMapping(value = "/edit-state/{stateId}", method = RequestMethod.GET)
	public String editState(Model model, @PathVariable("stateId") String stateId, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);

		/*if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url ="/login.do";
			return "redirect:" + url;
		}*/


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

		stateService.editState(stateDto);

		return pageRedirect("/states.do");
	}
	
	@Override
	protected String[] requiredJs() {
		return new String[] {"js/vendor/jquery.validation.min.js", "js/vendor/addition-medthods-min.js", "js/viewjs/add-state.js" };
	}


	
	
}
