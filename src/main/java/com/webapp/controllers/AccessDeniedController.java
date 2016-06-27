package com.webapp.controllers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/access-denied")

public class AccessDeniedController extends BusinessController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String accessDenied() throws ServletException, IOException {
		
		return "/access-denied";
	}

}
