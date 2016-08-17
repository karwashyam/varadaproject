package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.dto.PaymentSchemeDto;
import com.webapp.services.PaymentSchemeSerivce;

@Component
public class PaymentSchemeValidator implements Validator {

	@Autowired
	PaymentSchemeSerivce paymentSchemeSerivce;
	
	public boolean supports(Class<?> clazz) {
		
		return PaymentSchemeDto.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {

		
		PaymentSchemeDto project = (PaymentSchemeDto) obj;
		
		if (!("").equals(project.getTitle()) && !("").equals(project.getTitle())) {

			boolean isExists = paymentSchemeSerivce.isPaymentSchemeExists(project.getTitle());

			if (isExists) {
				errors.rejectValue("paymentSchemeExists", "valid.paymentSchemeExists");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required.paymentSchemeName");


	}

}