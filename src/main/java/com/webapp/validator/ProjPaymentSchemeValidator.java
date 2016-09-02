package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.dto.ProjectPaymentSchemeDto;
import com.webapp.services.ProjPaymentSchemeSerivce;

@Component
public class ProjPaymentSchemeValidator implements Validator {

	@Autowired
	ProjPaymentSchemeSerivce projPaymentSchemeSerivce;
	
	public boolean supports(Class<?> clazz) {
		
		return ProjectPaymentSchemeDto.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {

		
		ProjectPaymentSchemeDto project = (ProjectPaymentSchemeDto) obj;

		if (!("").equals(project.getPaymentSchemeId()) && !("").equals(project.getPaymentSchemeId())) {
			boolean isExists = projPaymentSchemeSerivce.isProjPaymentSchemeExists(project.getPaymentSchemeId(), project.getProjectId());
			System.out.println( "\n\t isExists="+isExists);

			if (isExists) {
				errors.rejectValue("projPaymentSchemeExists", "valid.projPaymentSchemeExists");
			}
		}else{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectId", "required.projectName");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentSchemeId", "required.paymentSchemeName");


		}
		


	}

}