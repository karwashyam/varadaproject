package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.dto.Login;
import com.webapp.models.User;
import com.webapp.services.UserSerivce;

@Component
public class LoginValidator implements Validator {

	@Autowired
	private UserSerivce userSerivce;

	public boolean supports(Class<?> clazz) {
		return Login.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {

		Login login = (Login) obj;

		if (!("").equals(login.getUserName()) && !("").equals(login.getPassword())) {

			User model = userSerivce.getUserFromCredentials(login.getUserName(), login.getPassword());

			if (model == null) {
				errors.rejectValue("validLogin", "valid.validLogin");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "required.userName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.password");


	}

}