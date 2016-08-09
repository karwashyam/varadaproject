package com.webapp.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.models.CityModel;

@Component
public class CityValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return CityModel.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stateId", "required.stateId");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cityName", "required.cityName");


	}

}