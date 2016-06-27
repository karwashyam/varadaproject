package com.webapp.controllers.secure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.models.DailyUpdateDto;
import com.webapp.services.SendDailyUpdateService;

@Controller
@RequestMapping("/daily-update")
public class SendDailyUpdateController extends BusinessController {

	@Autowired
	private SendDailyUpdateService sendDailyUpdateService;


	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		DailyUpdateDto dailyUpdateDto = new DailyUpdateDto();
		model.addAttribute(dailyUpdateDto);
		return "send-daily-update";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postSendMessage(Model model, DailyUpdateDto dailyUpdateDto,
			BindingResult result, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {

		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		model.addAttribute(dailyUpdateDto);
		int status =sendDailyUpdateService.postSendMessage(dailyUpdateDto);
		if(status>0){
				model.addAttribute("msg", "Daily Update sent successfully");
		}else{
			model.addAttribute("msg", "Failed to send updates");
		}
		return "send-daily-update";
	}

}
