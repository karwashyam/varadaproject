package com.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.models.ExcelDto;
import com.webapp.services.UserSerivce;

@Component
public class UploadValidator implements Validator {

	@Autowired
	private UserSerivce userSerivce;

	public boolean supports(Class<?> clazz) {
		return ExcelDto.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {

		ExcelDto excelDto = (ExcelDto) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "excelName", "required.excel");
		if(excelDto.getExcelName().isEmpty()){
			errors.rejectValue("excelName", "required.excel");
		}

	}

}