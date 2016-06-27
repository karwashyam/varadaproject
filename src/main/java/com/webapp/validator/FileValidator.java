package com.webapp.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.webapp.dto.FileDto;

@Component
public class FileValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return FileDto.class.equals(clazz);
	}

	public void validate(Object obj, Errors errors) {
		FileDto file = (FileDto) obj;
		if (file.getFile().getSize() == 0) {
			errors.rejectValue("file", "valid.file");
		}

	}

}