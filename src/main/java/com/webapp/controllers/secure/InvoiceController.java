package com.webapp.controllers.secure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.webapp.controllers.BusinessController;
import com.webapp.dbsession.DbSession;
import com.webapp.services.OrderService;

@Controller
@RequestMapping("/invoice")
public class InvoiceController extends BusinessController{


	@Autowired
	private OrderService orderService;

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(@RequestParam(required = false) String userId, Model model, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		preprocessRequest(model, req, res);
		if (!DbSession.isValidLogin(getDbSession(), sessionService)) {
			String url = "/login.do";
			return "redirect:" + url;
		}
		
		return "invoice";
	}

	
	@Override
	protected String[] requiredJs() {
		return new String[] { "js/suborder.js" };
	}
}
