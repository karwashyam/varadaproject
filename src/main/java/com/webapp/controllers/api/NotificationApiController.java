/*package com.webapp.controllers.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.controllers.BusinessApiController;
import com.webapp.dto.NotificationDto;
import com.webapp.services.NotificationSerivce;

@RestController
@RequestMapping("/api")
public class NotificationApiController extends BusinessApiController {

	@Autowired
	private NotificationSerivce notificationSerivce;
	
	@Autowired
	private LoginApiValidator loginApiValidator;

	@InitBinder("loginApi")
	private void initLoginBinder(WebDataBinder binder) {
		binder.setValidator(loginApiValidator);
	}

	@RequestMapping(value = "/notification", method = RequestMethod.POST)
	public ModelAndView addNotification(@RequestBody NotificationDto notificationDto, HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		int status=notificationSerivce.addNotification(notificationDto);
		if(status>0){
			outputMap.put("message", "Notification Added Successfully");
		}else{
			outputMap.put("message", "Failed to add Notification");
		}
		
		return getOutputResponse(outputMap);

	}
	
	@RequestMapping(value = "/notification",method = RequestMethod.GET)
	public ModelAndView getNotification( HttpServletRequest req, HttpServletResponse res) {
		
		HashMap<String, Object> outputMap = notificationSerivce.getNotification();
		return getOutputResponse(outputMap);
	}


}*/