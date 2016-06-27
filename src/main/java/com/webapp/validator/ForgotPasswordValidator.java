package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.daos.AppUserDao;
import com.webapp.models1.AppUser;

@Component
public class ForgotPasswordValidator implements Validator {


	@Autowired
	private EmailFormatValidator emailFormatValidator;
	
	@Autowired
	private AppUserDao appUserDao;
	
	public boolean supports(Class<?> clazz) {
		return AppUser.class.isAssignableFrom(clazz);
	}


	public void validate(Object obj, Errors errors) {
		AppUser user = (AppUser) obj;
		
		if (user.getEmail() != null && !"".equalsIgnoreCase(user.getEmail())) {
			boolean isValidFormat = emailFormatValidator.emailValidate(user.getEmail().trim());

			if (!isValidFormat) {
				errors.rejectValue("email", "valid.email");
			}
			
			boolean alreadyExists = appUserDao.isEmailIdExists(user.getEmail().trim());
			
			if(!alreadyExists){
				errors.rejectValue("email","valid.email");
			}
		}else if(user.getPhone() != null && !"".equalsIgnoreCase(user.getPhone())){
			boolean alreadyExists = appUserDao.isPhoneExists(user.getPhone().trim());
			
			if(!alreadyExists){
				errors.rejectValue("phone","valid.phone");
			}
		}else{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required.emailPhone");
		}

	}
}