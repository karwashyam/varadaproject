package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.dto.LoginApi;
import com.webapp.models1.AppUser;
import com.webapp.services.UserSerivce;

@Component
public class LoginApiValidator implements Validator {

	@Autowired
	private UserSerivce userSerivce;

	public boolean supports(Class<?> clazz) {
		return LoginApi.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {

		LoginApi login = (LoginApi) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required.emailPhone");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deviceUid", "required.deviceUid");

		if((login.getEmail()!=null && !("").equals(login.getEmail())) &&
		   (login.getPassword()!=null && !("").equals(login.getPassword()))) {

			AppUser model = userSerivce.getUserFromCredentialsForApi(login.getEmail(), login.getPassword());

			if (model == null) {
				errors.rejectValue("validLogin", "valid.validLogin");
			}
		}	

		

	}

}