package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.daos.AppUserDao;
import com.webapp.models1.AppUser;

@Component
public class ProfileValidator implements Validator {

	@Autowired
	private AppUserDao appUserDao;
	
	public boolean supports(Class<?> clazz) {
		return AppUser.class.isAssignableFrom(clazz);
	}


	public void validate(Object obj, Errors errors) {
			AppUser appUser = (AppUser)obj;
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "required.userId");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "required.phone");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "required.fullName");
			if(appUser.getEmail()!=null && !"".equalsIgnoreCase(appUser.getEmail())){
				if(appUserDao.isEmailIdExistsForOtherUser(appUser.getEmail(),appUser.getUserId())){
					errors.rejectValue("email", "email.exists");
				}
			}
			
			if(appUser.getPhone()!=null && !"".equalsIgnoreCase(appUser.getPhone())){
				if(appUserDao.isPhoneExistsForOtherUser(appUser.getPhone(),appUser.getUserId())){
					errors.rejectValue("phone", "phone.exists");
				}
			}
	}
}