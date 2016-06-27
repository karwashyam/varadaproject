package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.fnf.utils.EncryptionUtils;
import com.webapp.daos.AppUserDao;
import com.webapp.dto.ChangePasswordApiDto;

@Component
public class ChangePasswordApiValidator implements Validator {

	@Autowired
	AppUserDao appUserDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordApiDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {

		ChangePasswordApiDto changePassword = (ChangePasswordApiDto) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "required.userId");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", "required.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "required.password");

		if (changePassword.getUserId() != null && !"".equalsIgnoreCase(changePassword.getUserId()) && changePassword.getOldPassword() != null  && !"".equalsIgnoreCase(changePassword.getOldPassword()) && changePassword.getNewPassword() != null  && !"".equalsIgnoreCase(changePassword.getNewPassword())) {
			String password = appUserDao.fetchPassword(changePassword.getUserId());

			if (!EncryptionUtils.isValidPassword(changePassword.getOldPassword(), password)) {

				errors.rejectValue("newPassword", "invalid.oldPassword");
			}/* else {

				boolean isValidFormat = validatePassword(errors, changePassword.getNewPassword());

				if (!isValidFormat) {
					errors.rejectValue("newPassword", "validation.password");
				}
			}*/
		}

	}

	//for integer : (?=.*\\d)  for cap case and special char(?=.*[A-Z])(?=.*[-+_!@#$%^&*.,?])

	/*private final String PASSWORD_REG_EXP = "^(?=.*\\d).{6,20}$";

	boolean validatePassword(Errors errors, String password) {

		if (password != null) {

			Pattern pattern = Pattern.compile(PASSWORD_REG_EXP);

			Matcher matcher = pattern.matcher(password.trim());
			return matcher.matches();
		}

		return false;
	}*/
}
