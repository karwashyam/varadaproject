package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.daos.AppUserDao;
import com.webapp.dto.SignupDto;
import com.webapp.services.UserSerivce;

@Component
public class SignupApiValidator implements Validator {

	@Autowired
	private UserSerivce userSerivce;

	@Autowired
	private EmailFormatValidator emailFormatValidator;
	
	@Autowired
	private AppUserDao appUserDao;
	
	public boolean supports(Class<?> clazz) {
		return SignupDto.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {

		SignupDto signup = (SignupDto) obj;
		
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "required.phone");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "required.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deviceUid", "required.deviceUid");

		if (signup.getEmail() != null && !"".equalsIgnoreCase(signup.getEmail())) {
			boolean isValidFormat = emailFormatValidator.emailValidate(signup.getEmail().trim());

			if (!isValidFormat) {
				errors.rejectValue("email", "valid.email");
			}
			
			boolean alreadyExists = appUserDao.isEmailIdExists(signup.getEmail().trim());
			
			if(alreadyExists){
				errors.rejectValue("email","email.exists");
			}
		}	
		if(signup.getPhone() != null && !"".equalsIgnoreCase(signup.getPhone())){
			boolean alreadyExists = appUserDao.isPhoneExists(signup.getPhone().trim());
			
			if(alreadyExists){
				errors.rejectValue("phone","phone.exists");
			}
		}
		

	}

}