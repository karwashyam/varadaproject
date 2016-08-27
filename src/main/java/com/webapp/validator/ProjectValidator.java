package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.dto.ProjectDto;
import com.webapp.services.ProjectSerivce;

@Component
public class ProjectValidator implements Validator {

	@Autowired
	ProjectSerivce projectSerivce;
	
	public boolean supports(Class<?> clazz) {
		return ProjectDto.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {

		
		ProjectDto project = (ProjectDto) obj;
		
		if (!("").equals(project.getTitle()) && !("").equals(project.getTitle())) {

			boolean isExists = projectSerivce.isProjectNameExists(project.getTitle());

			if (isExists) {
				errors.rejectValue("projectExists", "valid.projectExists");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required.projectName");


	}

}