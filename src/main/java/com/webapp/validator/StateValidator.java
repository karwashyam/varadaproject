package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.dto.StateDto;
import com.webapp.services.StateSerivce;

@Component
public class StateValidator implements Validator {

	@Autowired
	StateSerivce stateSerivce;
	
	public boolean supports(Class<?> clazz) {
		return StateDto.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {

		
		StateDto state = (StateDto) obj;
		
		if (!("").equals(state.getStateName()) && !("").equals(state.getStateName())) {

			boolean isExists = stateSerivce.isStateNameExists(state.getStateName());

			if (isExists) {
				errors.rejectValue("stateExists", "valid.stateExists");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stateName", "required.stateName");


	}

}