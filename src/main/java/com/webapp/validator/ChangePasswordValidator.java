package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.fnf.utils.EncryptionUtils;
import com.webapp.dto.ChangePasswordApiDto;
import com.webapp.services.UserSerivce;

@Component
public class ChangePasswordValidator implements Validator {

	@Autowired
	private UserSerivce userSerivce;

	public boolean supports(Class<?> clazz) {
		return ChangePasswordApiDto.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {

		ChangePasswordApiDto changePassword = (ChangePasswordApiDto) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "required.userId");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", "required.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "required.password");

		if (changePassword.getUserId() != null && !"".equalsIgnoreCase(changePassword.getOldPassword())&& changePassword.getOldPassword() != null  && !"".equalsIgnoreCase(changePassword.getNewPassword())&& changePassword.getNewPassword() != null) {
			String password = userSerivce.fetchPassword(changePassword.getUserId());

			if (!EncryptionUtils.isValidPassword(changePassword.getOldPassword(), password)) {

				errors.rejectValue("oldPassword", "invalid.oldPassword");
			}else
			if (!changePassword.getNewPassword().equalsIgnoreCase(changePassword.getConfirmNewPassword())) {

				errors.rejectValue("newPassword", "unmatch.password");
			}
		}

	}

}