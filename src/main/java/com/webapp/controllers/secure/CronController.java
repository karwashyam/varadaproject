package com.webapp.controllers.secure;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webapp.services.PaymentService;

@Controller
@RequestMapping("/cron")
@EnableScheduling
public class CronController {

	@Autowired
	private PaymentService paymentService;

	@Scheduled(cron = "0 0 1 * * *")
	@RequestMapping(method = RequestMethod.GET)
	public void schedulerMethod() throws ServletException, IOException {

		paymentService.getFutureEmi();
		
	}
	
}
