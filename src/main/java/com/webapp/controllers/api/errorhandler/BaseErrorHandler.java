package com.webapp.controllers.api.errorhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.fnf.utils.ErrorMessage;
import com.utils.constant.ErrorConstant;
import com.webapp.dto.ErrorResponse;

public class BaseErrorHandler implements ErrorConstant {

	public static final String SINGLE_OBJECT_RESPONSE = "responseObject";

	protected ErrorResponse ouputErrorMessages(String msgType, String message, int errorCode, String fieldName) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode(errorCode);
		errorMessage.setMessage(message);
		errorMessage.setFieldName(fieldName);
		List<ErrorMessage> errors = new ArrayList<ErrorMessage>();
		errors.add(errorMessage);
		return ouputErrorMessages(msgType, errors);
	}

	protected ErrorResponse ouputErrorMessages(String msgType, List<ErrorMessage> errors) {
		ErrorResponse messageModel = new ErrorResponse();
		messageModel.setType(msgType);
		messageModel.setMessages(errors);
		return messageModel;
	}

	public ModelAndView getOutputResponse(Object obj) {
		return new ModelAndView("", SINGLE_OBJECT_RESPONSE, obj);
	}

}