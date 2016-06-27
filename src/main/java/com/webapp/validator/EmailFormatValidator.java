package com.webapp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class EmailFormatValidator {

	private final String emailRegExp = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

	public boolean emailValidate(String email) {

		if (email != null && !email.toString().equals("")) {

			Pattern pattern = Pattern.compile(emailRegExp, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email.trim());
			return matcher.matches();
		}
		return false;

	}
}
