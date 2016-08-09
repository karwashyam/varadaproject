package com.webapp.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;

import com.fnf.utils.ErrorMessage;
import com.webapp.daos.LoginDao;
import com.webapp.exceptions.HttpUnauthorizedException;
import com.webapp.models.User;

public class BusinessApiController extends BaseApiController {

	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private MessageSource messageSource;

	private User user = null;

	public String getHeaderSessionKey() {
		return getRequest().getHeader("x-api-key");
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int preprocessCheck(String function, HttpServletRequest request, HttpServletResponse response) {
		// Do not change the method order

		setRequest(request);
		setResponse(response);

		if (!checkSession()) {
			throw new HttpUnauthorizedException();

		}

		if (function != null && !checkUserAccess()) {
			throw new HttpUnauthorizedException();
		}
		return 0;
	}

	private boolean checkSession() {

		String sessionKey = getHeaderSessionKey();
		if (sessionKey != null) {
			this.setUser(loginDao.fetchUserBySessionKey(sessionKey));
			if (this.getUser() != null) {
				return true;
			}
		}
		return false;
	}

	private boolean checkUserAccess() {
		//TODO check access for user role // no sonar
		/*if (this.getUser() != null) {
		        List<UserRole> roleList = this.getUser().getUserRoles();
		        // Remove commented return statements , check access for user role
		        if (roleList.isEmpty()) {

		        }
		   }*/
		return true;
	}

	public String getSourceMessage(String key) {
		return messageSource.getMessage(key, null, null);
	}
	
 	public ErrorMessage getErrorMessage(String key) {
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setFieldName("TechnicalError");
		errorMessage.setMessage(messageSource.getMessage(key, null, null));
		return errorMessage;
	} 
	
	
	protected ModelAndView sendSuccessMessage(String message) {

		Map<String, Object> messageDto = new HashMap<String, Object>();
		messageDto.put("messageType", SUCCESS_MESSAGE_TYPE);
		messageDto.put("message", message);
		return new ModelAndView("", SINGLE_OBJECT_RESPONSE, messageDto);
	}

	protected ModelAndView getOutputResponse(Object obj) {
		return new ModelAndView("", SINGLE_OBJECT_RESPONSE, obj);
	}

}