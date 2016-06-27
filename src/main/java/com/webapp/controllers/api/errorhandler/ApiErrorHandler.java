package com.webapp.controllers.api.errorhandler;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.fnf.utils.ErrorMessage;
import com.webapp.dto.ValidationError;
import com.webapp.exceptions.BusinessErrorException;
import com.webapp.exceptions.HttpUnauthorizedException;
import com.webapp.exceptions.ResourceNotFoundException;
import com.webapp.exceptions.SendInternalServerException;
import com.webapp.exceptions.SendUnexpectedError;

@ControllerAdvice
public class ApiErrorHandler extends BaseErrorHandler {

	private static Logger logger = Logger.getLogger(ApiErrorHandler.class);
	private MessageSource messageSource;

	@Autowired
	public ApiErrorHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleAllExceptions(Exception ex) {
		logger.error(ex);
		return getOutputResponse(ouputErrorMessages(TECHNICAL_ERROR_TYPE, "Internal Server Error", ERROR_CODE_UNEXPECTED, null));
	}

	@ExceptionHandler(SendUnexpectedError.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView sendUnexpectedError(SendUnexpectedError ex) {
		logger.error(ex);
		return getOutputResponse(ouputErrorMessages(TECHNICAL_ERROR_TYPE, ERROR_MSG_UNEXPECTED, ERROR_CODE_UNEXPECTED, null));
	}

	@ExceptionHandler(SendInternalServerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView sendInternalServerException(SendInternalServerException ex) {
		logger.error(ex);
		return getOutputResponse(ouputErrorMessages(TECHNICAL_ERROR_TYPE, ex.getErrors()));
	}

	@ExceptionHandler(HttpUnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ModelAndView sendUnauthorizedRequestError() {
		
		return getOutputResponse(ouputErrorMessages(BUSSINES_ERROR_TYPE, ERROR_MSG_UNAUTHORIZED_ACCESS, ERROR_CODE_UNAUTHORIZED_ACCESS, null));

	}

	@ExceptionHandler(BusinessErrorException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public ModelAndView sendBusinessError(BusinessErrorException ex) {

		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setFieldName("ERR-BUSS");
		errorMessage.setCode(417);
		errorMessage.setMessage(ex.getFieldErrors().get(0).getMessage());

		return getOutputResponse(errorMessage);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView sendResourceNotFoundError(ResourceNotFoundException ex) {

		return getOutputResponse(ouputErrorMessages(BUSSINES_ERROR_TYPE, ex.getMessage(), ERROR_CODE_UNEXPECTED, null));

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationError processValidationError(MethodArgumentNotValidException ex) {
		logger.debug("Handling form validation error");

		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		return processFieldErrors(fieldErrors);
	}

	private ValidationError processFieldErrors(List<FieldError> fieldErrors) {
		ValidationError dto = new ValidationError();

		for (FieldError fieldError : fieldErrors) {
			String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
//			logger.debug("Adding error message: {} to field: {}", localizedErrorMessage, fieldError.getField());
			dto.addFieldError(fieldError.getField(), localizedErrorMessage);
		}
		return dto;
	}

	private String resolveLocalizedErrorMessage(FieldError fieldError) {

		Locale currentLocale = LocaleContextHolder.getLocale();

		logger.debug(currentLocale.getDisplayName());

		String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

		//If a message was not found, return the most accurate field error code instead.
		//You can remove this check if you prefer to get the default error message.
		if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
			String[] fieldErrorCodes = fieldError.getCodes();
			localizedErrorMessage = fieldErrorCodes[0];
		}

		return localizedErrorMessage;
	}

}