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
import com.webapp.models.LeadRegisterDto;
import com.webapp.services.LeadFormService;

@Controller
@RequestMapping("/register")
public class LeadFormController extends BusinessController {

	@Autowired
	private LeadFormService leadFormService;

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(Model model, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {

		return "redirect:http://intricatefashionsindia.com/saree-suit-kurti-zero-investment-franchisee-og/";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postSendMessage(Model model,
			 LeadRegisterDto leadRegisterDto, BindingResult result,
			HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		leadFormService.addLead(leadRegisterDto);
		return "redirect:"+leadRegisterDto.getRedirectUrl();
	}

}